package com.patience.klondike.domain.model;

import static com.google.common.base.Preconditions.checkNotNull;

public final class GameId {

	private final String id;
	
	public GameId(String id) {
		checkNotNull(id, "Id must be provided.");
		this.id = id;
	}
	
	public String id() {
		return id.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (obj != null && this.getClass() == obj.getClass()) {
			GameId other = (GameId) obj;
			return id().equals(other.id());
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return 31 * id.hashCode();
	}
	
	@Override
	public String toString() {
		return id.toString();
	}
}