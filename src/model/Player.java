package model;

public class Player {

	/*
	 * this is the class that define the player, which has different attributes
	 */

	private String name; // the name of the player, need to be entered at start of the game
	public int score; // the score for each player
	private boolean currentTurn; // an instance variable to store whether it is this player's turn

	public Player(String playerName, boolean ifFirst) {

		// constructor for the player, with the player's name and whether the player is the first one to play

		name = playerName;  // name of the player
		setCurrentTurn(ifFirst); // if true, then the player is the first one to player

	}

	public boolean isCurrentTurn() {

		// get the status of the current turn for the player
		return currentTurn;
	}

	public String getName() {

		// get the name of the player
		return name;
	}

	public void setCurrentTurn(boolean currentTurn) {
		// set the current turn, so that each player plays one after another
		this.currentTurn = currentTurn;
	}

}
