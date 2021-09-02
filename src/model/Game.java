package model;

import controller.ReadCustomFile;
import controller.ShipException;
import controller.StoreHighScores;
import view.SetGameDisplay;
import view.StartingPanel;

public class Game {

	/*
	 * this class sets up the board and the players
	 * it is also taking charge of the score for each player and the ending of the game
	 * */


	private Board gameBoard; // the board
	private Player playerOne; // first player
	private Player playerTwo; // second player
	private int totalShipHit = 0; // the total times that all ships have been hit

	public Game() {

		// constructor to create the game
		gameBoard = new Board(); // a new board for the game
		setUpPlayers();
		setUpShips();
	}

	public void setUpPlayers() {

		// methods to set up the players

		playerOne = new Player("player One", true); // the player that plays the first
		playerTwo = new Player("player Two", false); // the player that plays the second
	};

	public void setUpShips() {

		// sets up the ships in 2 different ways, either randomly, or through a user uploaded file
		if (StartingPanel.getShipPlacementChoice() == "random") {
			gameBoard.addAllShipsRandomly();
		} else if (StartingPanel.getShipPlacementChoice() == "user defined") {
			ReadCustomFile newFile = new ReadCustomFile();
			try {
				newFile.readUserData(StartingPanel.getFilename()); // read the file name specified by the user
			} catch (ShipException e) {
				e.printStackTrace();
			} 
			gameBoard.setAllShips(newFile.getAllShips());
		}
	}

	public boolean gameEnd() {
		// this makes the game end, when all ships sink
		boolean End = false; // if this is true, then the game ends
		int sumShipHit = 0; // the total number of hit for all ships, when all ships sink, the total hit = 14 (sum of all ships' length)


		for (Ship ship : gameBoard.getAllShips()) {
			sumShipHit += ship.getHitTimes();

		}


		totalShipHit = sumShipHit; if (totalShipHit > 13) { End = true; }


		return End;
	}

	public String displayTurn() {

		// shows which player has the current turn to play

		String currentPlayer = null; // the initial player is null
		if (getPlayerOne().isCurrentTurn() == true && getPlayerTwo().isCurrentTurn() == false) {
			currentPlayer = SetGameDisplay.getName1(); // getPlayerOne().getName();
		} else {
			currentPlayer = SetGameDisplay.getName2(); // getPlayerTwo().getName();
		}
		return currentPlayer;
	}


	public String whoWins() {

		// this method returns name of the winner, and store the highest score

		String winner; // the winner's name

		String playerOneName = SetGameDisplay.getName1();
		String playerTwoName = SetGameDisplay.getName2();
		if (getPlayerOne().score > getPlayerTwo().score) {
			winner = playerOneName;

			StoreHighScores.appendGameScoreToFile(playerOneName, getPlayerOne().score);
		} else if (getPlayerOne().score < getPlayerTwo().score) {
			winner = playerTwoName;
			StoreHighScores.appendGameScoreToFile(playerTwoName, getPlayerTwo().score);

		} else {
			winner = "both";
			StoreHighScores.appendGameScoreToFile(playerOneName, getPlayerOne().score);
			StoreHighScores.appendGameScoreToFile(playerTwoName, getPlayerTwo().score);

		}
		return winner;
	}

	public int adjustScoreWithScoringSystem(int xPosition, int yPosition) {

		// might give bonus score to player2, based on the setting of the game
		// if the game setting is that player 2 has bonus, then player 2 gets 1 extra point for each hit

		int addedScore = 0; // bonus
		int score = getGameBoard().calculateScoreFromHit(xPosition, yPosition);; // original score

		if (score >0 && getPlayerTwo().isCurrentTurn() == true){
			if (StartingPanel.getUserScoringPreference().equals("player2 has bonus score")) {
				addedScore = 1;
			} 	
		}

		return score + addedScore;
	}

	public void updateScore(int x, int y) {

		// this method updates the player's score, based on who hits the ship and the chosen scoring system

		int playerScore = adjustScoreWithScoringSystem(x, y); // the score that's shown in the game display

		if (getPlayerOne().isCurrentTurn() == true && getPlayerTwo().isCurrentTurn() == false) {

			getPlayerOne().score += playerScore;
			System.out.print("player One just earned " + playerScore + ". ");

			getPlayerOne().setCurrentTurn(false);
			getPlayerTwo().setCurrentTurn(true);
		} else {

			getPlayerTwo().score += playerScore;
			System.out.print("player Two just earned " + playerScore + ". ");
			getPlayerOne().setCurrentTurn(true);
			getPlayerTwo().setCurrentTurn(false);

		}

	}

	public Board getGameBoard() {

		// get the board
		return gameBoard;
	}

	public Player getPlayerOne() {

		// get the player
		return playerOne;
	}

	public Player getPlayerTwo() {

		// get the player
		return playerTwo;
	}

}
