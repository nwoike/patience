package com.patience.klondike.infrastructure.persistence;

import static com.google.common.collect.Maps.newHashMap;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patience.klondike.domain.model.game.Game;
import com.patience.klondike.domain.model.game.GameId;
import com.patience.klondike.domain.model.game.GameRepository;
import com.patience.klondike.infrastructure.persistence.model.GameDO;

/**
 * Simple example of persisting an aggregate as a serialized graph.
 */
public class JdbcGameRepository implements GameRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	// TODO: Externalize SQL somewhere on the classpath
	private static final String searchSql = "SELECT COUNT(*) FROM Klondike WHERE game_id = :gameId";
	
	private static final String selectSql = "SELECT game_id, data, version FROM Klondike WHERE game_id = :gameId";
	
	private static final String insertSql = "INSERT INTO KLONDIKE VALUES (:gameId, :data, 1)";
	
	private static final String updateSql = "UPDATE KLONDIKE SET data = :data, version= :newVersion"
	                                      + " WHERE game_id = :gameId AND version = :existingVersion ";
	
	@Override
	public GameId nextIdentity() {		
		return new GameId(UUID.randomUUID().toString());
	}

	@Override
	public void save(Game game) {		
		Map<String, String> parameters = buildParameterMap(game);
		
		int count = jdbcTemplate.queryForObject(searchSql, parameters, Integer.class);
					
		if (count == 0) {			
			jdbcTemplate.update(insertSql, parameters);		
			 
		} else {			
			jdbcTemplate.update(updateSql, parameters);		
		}
	}

	private Map<String, String> buildParameterMap(Game game) {
		Map<String, String> parameters = newHashMap();
		parameters.put("gameId", game.gameId().id());		
		parameters.put("data", convertToJson(game));
		parameters.put("existingVersion", String.valueOf(game.version()));
		parameters.put("newVersion", String.valueOf(game.version() + 1));
		
		return parameters;
	}

	private String convertToJson(Game game) {
		GameDO gameDO = new GameDO(game);
		
		String jsonData;
		
		try {
			jsonData = objectMapper.writeValueAsString(gameDO);		
			
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	
		return jsonData;
	}

	@Override
	public Game gameOfId(GameId gameId) {
		Map<String, String> parameters = newHashMap();
		parameters.put("gameId", gameId.id());	
		
		 List<Game> results = jdbcTemplate.query(selectSql, parameters, new RowMapper<Game>() {
			@Override
			public Game mapRow(ResultSet resultSet, int idx) throws SQLException {
				GameDO gameDO;
				
				try {
					gameDO = objectMapper.readValue(resultSet.getString("data"), GameDO.class);
					
				} catch (IOException e) {
					throw new RuntimeException(e);
				}		
				
				Game game = gameDO.toGame();
				game.setVersion(resultSet.getInt("version"));
				
				return game;
			}			
		});
		 
		if (results.isEmpty()) {
			return null;
		}
		
		return results.get(0);
	}	
}