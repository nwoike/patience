package com.patience.klondike.application;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.transaction.annotation.Transactional;

import com.patience.klondike.domain.model.game.scorer.GameScorerRepository;

@Transactional
public class GameScorerApplicationService {

	private GameScorerRepository repository;
	
	public GameScorerApplicationService(GameScorerRepository repository) {
		this.repository = checkNotNull(repository, "GameScorerRepository is required.");
	}
	
	public void cardMoved() {
		
	}
}
