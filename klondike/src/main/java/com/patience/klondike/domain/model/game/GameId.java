package com.patience.klondike.domain.model.game;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class GameId {

	private final UUID id;
	
	public GameId(UUID id) {
		checkNotNull(id, "Id must be provided.");
		this.id = id;
	}
	
	public GameId(String uuid) {
		this(UUID.fromString(uuid));
	}

	@JsonProperty
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

	public long toSeed() {		
		return id.getLeastSignificantBits();
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