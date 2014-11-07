package com.patience.klondike.domain.model.game;

public enum PassCount {

	One(1),
	Three(3),
	Unlimited(Integer.MAX_VALUE);

	private int value;

	private PassCount(int value) {
		this.value = value;		
	}
	
	public int asInt() {		
		return value;
	}	
	
	public boolean limitReached(int passCount) {
		return passCount >= value;
	}
}