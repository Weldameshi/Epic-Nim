package model;

public class Player {
	private String name;
	private int playerId;
	private boolean activePlayer;
	
	public Player(String name, int playerId, boolean activePlayer) {
		setName(name);
		setPlayerId(playerId);
		setActivePlayer(activePlayer);
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

	public boolean isActivePlayer() {
		return activePlayer;
	}
	
	public void setActivePlayer(boolean activePlayer) {
		this.activePlayer = activePlayer;
	}
}
