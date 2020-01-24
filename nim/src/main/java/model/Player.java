package model;

public class Player {
	private String name;
	private int playerId;
	
	public Player(String name, int playerId) {
		setName(name);
		setPlayerId(playerId);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPlayerId() {
		return playerId;
	}
	
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

}
