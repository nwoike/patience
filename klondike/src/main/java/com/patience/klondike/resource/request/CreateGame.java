package com.patience.klondike.resource.request;

public class CreateGame {

	private String drawCount = "One";
	
	private String passCount = "Unlimited";
	
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
}
