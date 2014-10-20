package com.patience.klondike.resource.request;

public class PromoteCard {

	private int foundationId;
	
	private String rank;
	
	private String suit;
	
	public String getRank() {
		return rank;
	}
	
	public void setRank(String rank) {
		this.rank = rank;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public void setSuit(String suit) {
		this.suit = suit;
	}
	
	public int getFoundationId() {
		return foundationId;
	}
	
	public void setFoundationId(int foundationId) {
		this.foundationId = foundationId;
	}
}
