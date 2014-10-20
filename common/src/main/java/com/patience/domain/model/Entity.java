package com.patience.domain.model;

public abstract class Entity {

	private int version;
	
	protected Entity() {		
	}
	
	public int version() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
}