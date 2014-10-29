package com.patience.klondike.infrastructure.persistence;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.patience.klondike.domain.model.game.Game;
import com.patience.klondike.domain.model.game.GameId;
import com.patience.klondike.domain.model.game.GameRepository;
import com.patience.klondike.infrastructure.persistence.model.game.GameDO;

/**
 * Simple example of persisting an aggregate as a serialized graph.
 */
public class JdbcGameRepository implements GameRepository {

	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private ObjectSerializer objectSerializer;
	
	@Value("${sql.game.exists}")
	private String searchSql;
	
	@Value("${sql.game.retrieve}")
	private String selectSql;
	
	@Value("${sql.game.insert}")
	private String insertSql;
	
	@Value("${sql.game.update}")
	private String updateSql;
	
	public JdbcGameRepository(NamedParameterJdbcTemplate jdbcTemplate, ObjectSerializer objectSerializer) {
		this.jdbcTemplate = checkNotNull(jdbcTemplate, "JdbcTemplate must be provided.");
		this.objectSerializer = checkNotNull(objectSerializer, "ObjectSerializer must be provided.");	
	}
	
	@Override
	public GameId nextIdentity() {		
		return new GameId(UUID.randomUUID());		
	}

	@Override
	public void save(Game game) {		
		Map<String, String> parameters = buildParameterMap(game);		
		int count = jdbcTemplate.queryForObject(searchSql, parameters, Integer.class);		
		String sql = count == 0 ? insertSql	: updateSql;		
		jdbcTemplate.update(sql, parameters);	
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
		return objectSerializer.serialize(gameDO);		
	}

	@Override
	public Game gameOfId(GameId gameId) {
		Map<String, String> parameters = newHashMap();
		parameters.put("gameId", gameId.id());	
		
		 List<Game> results = jdbcTemplate.query(selectSql, parameters, new RowMapper<Game>() {
			@Override
			public Game mapRow(ResultSet resultSet, int idx) throws SQLException {
				String data = resultSet.getString("data");
				GameDO gameDO = objectSerializer.deserialize(data, GameDO.class);
				
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