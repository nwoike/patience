package com.patience.klondike.application.representation;

import com.patience.klondike.domain.model.game.Settings;

public class SettingsRepresentation {

	private String drawCount;
	
	private String passCount;
	
	public SettingsRepresentation(Settings settings) {
		this.drawCount = settings.drawCount().name();
		this.passCount = settings.passCount().name();
	}
	
	public String getDrawCount() {
		return drawCount;
	}
	
	public String getPassCount() {
		return passCount;
	}
}
