package com.patience.klondike.infrastructure.persistence.model.game;

import com.patience.klondike.domain.model.game.DrawCount;
import com.patience.klondike.domain.model.game.PassCount;
import com.patience.klondike.domain.model.game.Settings;

public class SettingsDO {

	private String drawCount;
	
	private String passCount;
	
	public SettingsDO() {		
	}
	
	public SettingsDO(Settings settings) {
		this.drawCount = settings.drawCount().name();
		this.passCount = settings.passCount().name();
	}
	
	public String getDrawCount() {
		return drawCount;
	}
	
	public String getPassCount() {
		return passCount;
	}
	
	public void setDrawCount(String drawCount) {
		this.drawCount = drawCount;
	}
	
	public void setPassCount(String passCount) {
		this.passCount = passCount;
	}

	public Settings toSettings() {
		return new Settings(DrawCount.valueOf(drawCount), PassCount.valueOf(passCount));
	}
}
