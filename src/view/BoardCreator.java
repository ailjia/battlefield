package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.Game;
import model.Ship;


public class BoardCreator {

	// this class creates the board, which is the main display of the game, that consists of individual buttons

	private Game newGame; // the game
	private String currentPlayer; // this variable shows whose turn is it
	private int boardSize; // the board size in terms of its length and height, specified as square

	public BoardCreator() {

		// constructor of the class

		newGame = new Game();

		boardSize = StartingPanel.getUserSelectedSize();
	}

	public JPanel createBoard() {

		// the method to create a board which consists of individual buttons to be clicked, and the logic for updating game status

		JButton[][] battleBoardSquares = new JButton[boardSize][boardSize]; // a square consists of multiple JButtons

		JPanel battleBoard = new JPanel(new GridLayout(0, boardSize + 1)); // the JPanel to store the info about the game

		//Insets buttonMargin = new Insets(0, 0, 0, 0);

		for (int ii = 0; ii < battleBoardSquares.length; ii++) {
			for (int jj = 0; jj < battleBoardSquares[ii].length; jj++) {

				JButton button = new JButton(); // a new button for each ii, jj position

				setDefaultButtons(button);

				int x; // the x value of the hit position
				int y; // the y value of the hit position
				x = ii; 
				y = jj; 

				button.addActionListener(new ActionListener() {
					@Override

					public void actionPerformed(ActionEvent e) {
						// the action listener to respond to the click of the user, and update the game related info

						updateGameStatus(x, y);
					}

					private void updateGameStatus(int x, int y) {

						// this method updates the game status, by switching turns, update scores and checks if game ends

						if (newGame.gameEnd() == false) {

							currentPlayer = newGame.displayTurn();

							SetGameDisplay.getWhoseTurn().setText("current: " + currentPlayer);

							if (newGame.getGameBoard().validClickCheck(x, y) == true) {


								newGame.updateScore(x, y);

								System.out.println("Score of player one is: " + newGame.getPlayerOne().score);
								System.out.println("Score of player two is: " + newGame.getPlayerTwo().score);

								updateButtonColor();

								if (newGame.gameEnd() == true) {
									System.out.println("The game has ended");
									SetGameDisplay.getWhoseTurn()
									.setText("Game ended, " + newGame.whoWins() + " has won!");
								}

							} else {

								System.out.println("invalid click");

							}
							updateScoreDisplay();

						} else {
							System.out.println("The game has ended");
							SetGameDisplay.getWhoseTurn().setText("Game ended, " + newGame.whoWins() + " has won!");
						}
					}

					private void updateScoreDisplay() {
						// update the score of the player, on the display in the game
						SetGameDisplay.getPlayerOneScore().setText("Score: " + newGame.getPlayerOne().score);
						SetGameDisplay.getPlayerTwoScore().setText("Score: " + newGame.getPlayerTwo().score);

					}

					private void updateButtonColor() {

						// update the color of the button, after each hit

						Ship lastShipHit = newGame.getGameBoard().lastShipHit;
						if (lastShipHit == null) {
							button.setBackground(Color.BLUE);
						} else {
							button.setBackground(lastShipHit.getColor());

						}
					}
				}
						);

				battleBoardSquares[jj][ii] = button;
			}
		}

		battleBoard.add(new JLabel(""));

		horizontalPositionIdentifiers(battleBoard);

		verticalPositionIdentifiers(battleBoardSquares, battleBoard);

		return battleBoard;

	}

	private void setDefaultButtons(JButton button) {

		// method to set up the visual of the default buttons
		button.setBackground(Color.gray);
		button.setOpaque(true);
		button.setBorderPainted(true);
		button.setBorder(null);
	}

	private void verticalPositionIdentifiers(JButton[][] battleBoardSquares, JPanel battleBoard) {

		// creates the number from 1 up until the board size, placed on top of the board
		for (int ii = 0; ii < boardSize; ii++) {
			for (int jj = 0; jj < boardSize; jj++) {
				switch (jj) {
				case 0:
					battleBoard.add(new JLabel("" + (ii + 1), SwingConstants.CENTER));
				default:
					battleBoard.add(battleBoardSquares[jj][ii]);
				}
			}
		}
	}

	private void horizontalPositionIdentifiers(JPanel battleBoard) {

		// creates the number from 1 up until the board size, placed on left side of the board

		for (int ii = 0; ii < boardSize; ii++) {
			battleBoard.add(new JLabel("" + (ii + 1), SwingConstants.CENTER));
		}
	}

}
