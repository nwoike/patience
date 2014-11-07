package com.patience.klondike.domain.model.game;

import static com.google.common.base.Preconditions.checkNotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Settings {

	private DrawCount drawCount;
	
	private PassCount passCount;

	public Settings(DrawCount drawCount, PassCount passCount) {
		this.drawCount = checkNotNull(drawCount, "DrawCount must be provided.");
		this.passCount = checkNotNull(passCount, "PassCount must be provided.");	
	}
	
	@JsonProperty
	public DrawCount drawCount() {
		return drawCount;
	}
	
	@JsonProperty
	public PassCount passCount() {
		return passCount;
	}
}
