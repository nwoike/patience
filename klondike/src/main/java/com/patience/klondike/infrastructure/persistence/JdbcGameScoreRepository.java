package com.patience.klondike.infrastructure.persistence;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.patience.klondike.domain.model.game.GameId;
import com.patience.klondike.domain.model.game.score.GameScore;
import com.patience.klondike.domain.model.game.score.GameScoreRepository;
import com.patience.klondike.infrastructure.persistence.model.game.score.GameScoreDO;

@Component
public class JdbcGameScoreRepository implements GameScoreRepository {

	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private ObjectSerializer objectSerializer;
	
	@Value("${sql.gamescore.exists}")
	private String searchSql;
	
	@Value("${sql.gamescore.retrieve}")
	private String selectSql;
	
	@Value("${sql.gamescore.insert}")
	private String insertSql;
	
	@Value("${sql.gamescore.update}")
	private String updateSql;
	
	public JdbcGameScoreRepository(NamedParameterJdbcTemplate jdbcTemplate, ObjectSerializer objectSerializer) {
		this.jdbcTemplate = checkNotNull(jdbcTemplate, "JdbcTemplate must be provided.");
		this.objectSerializer = checkNotNull(objectSerializer, "ObjectSerializer must be provided.");	
	}
	
	@Override
	public void save(GameScore game) {		
		Map<String, String> parameters = buildParameterMap(game);		
		int count = jdbcTemplate.queryForObject(searchSql, parameters, Integer.class);		
		String sql = count == 0 ? insertSql	: updateSql;		
		jdbcTemplate.update(sql, parameters);	
	}

	private Map<String, String> buildParameterMap(GameScore gameScore) {
		Map<String, String> parameters = newHashMap();
		parameters.put("gameId", gameScore.gameId().id());		
		parameters.put("data", convertToJson(gameScore));
		parameters.put("existingVersion", String.valueOf(gameScore.version()));
		parameters.put("newVersion", String.valueOf(gameScore.version() + 1));
		
		return parameters;
	}

	private String convertToJson(GameScore game) {
		GameScoreDO gameScoreDO = new GameScoreDO(game);		
		return objectSerializer.serialize(gameScoreDO);		
	}

	@Override
	public GameScore gameScoreOfId(GameId gameId) {
		Map<String, String> parameters = newHashMap();
		parameters.put("gameId", gameId.id());	
		
		 List<GameScore> results = jdbcTemplate.query(selectSql, parameters, new RowMapper<GameScore>() {
			@Override
			public GameScore mapRow(ResultSet resultSet, int idx) throws SQLException {
				String data = resultSet.getString("data");
				GameScoreDO gameScoreDO = objectSerializer.deserialize(data, GameScoreDO.class);
				
				GameScore gameScore = gameScoreDO.toGameScore();
				gameScore.setVersion(resultSet.getInt("version"));
				
				return gameScore;
			}			
		});
		 
		if (results.isEmpty()) {
			return null;
		}
		
		return results.get(0);
	}	

}
