package com.patience.klondike.solver;

public abstract class Move implements Comparable<Move> {

	abstract void play(MoveHandler handler);
	
	protected int priority() {		
		return 0;
	}
	
	@Override
	public final int compareTo(Move other) {
		return other.priority() - priority();
	}
}
