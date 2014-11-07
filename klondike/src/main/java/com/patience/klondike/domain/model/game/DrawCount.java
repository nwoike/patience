package com.patience.klondike.domain.model.game;

public enum DrawCount {

	One(1),
	Three(3);
	
	private int value;
	
	private DrawCount(int value) {
		this.value = value;		
	}
	
	public int asInt() {
		return value;
	}
}
