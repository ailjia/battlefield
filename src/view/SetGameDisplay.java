package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.*;

public class SetGameDisplay {

	/*
	 * this class sets up the display when players are playing, score, name of players, whose turn
	 * it gives the possibility to quite the game
	 */

	private BoardCreator myBattleBoard; // the board that's created from BoardCreator
	JFrame frame = null;

	private final JPanel GameDisplay = new JPanel(new BorderLayout(1, 1));

	private static JLabel playerOneScore; // displayer for playerOne's score
	private static JLabel playerTwoScore; // displayer for playerTwo's score
	private static JLabel whoseTurn; // displayer for whose turn it is
	private static String name1 = ""; // Initialize the player name
	private static String name2 = ""; // Initialize the player name

	public SetGameDisplay() {

		// constructor for the class
		myBattleBoard = new BoardCreator();
		initializeGui();

	}

	public final void initializeGui() {

		// this method sets up the display for different components during the game

		GameDisplay.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel display = new JPanel(new BorderLayout(1, 1));
		JToolBar nameDisplay = new JToolBar();
		JToolBar scoreDisplay = new JToolBar();
		display.add(nameDisplay, BorderLayout.NORTH);
		display.add(scoreDisplay, BorderLayout.SOUTH);

		nameDisplay.setFloatable(false);
		nameDisplay.setLayout(new BoxLayout(nameDisplay, BoxLayout.LINE_AXIS));
		GameDisplay.add(display, BorderLayout.PAGE_START); // instead of nameDisplay

		setPlayerOneScore(new JLabel("player1 Score"));
		setPlayerTwoScore(new JLabel("player2 Score"));

		JLabel emptySpace = new JLabel("");
		whoseTurn = new JLabel("Player___'s turn");

		scoreDisplay.setLayout(new BoxLayout(scoreDisplay, BoxLayout.LINE_AXIS));

		scoreDisplay.add(emptySpace);
		scoreDisplay.add(Box.createHorizontalGlue());

		scoreDisplay.add(getPlayerOneScore());
		scoreDisplay.add(Box.createHorizontalGlue());
		scoreDisplay.add(getWhoseTurn());
		scoreDisplay.add(Box.createHorizontalGlue());
		scoreDisplay.add(getPlayerTwoScore());
		scoreDisplay.add(Box.createHorizontalGlue());
		scoreDisplay.add(emptySpace);

		JButton newGame = new JButton("Enter player names"); // the button for players to enter names
		nameDisplay.add(newGame);
		nameDisplay.add(Box.createHorizontalGlue());

		JLabel playerName = new JLabel("player One"); // JLabel to displayer first player's name
		JLabel playerName2 = new JLabel("player Two"); // JLabel to displayer second player's name
		JLabel displayTurn = new JLabel("Turn"); // JLabel to displayer whose turn

		nameDisplay.add(playerName);
		nameDisplay.add(Box.createHorizontalGlue());

		nameDisplay.add(displayTurn);
		nameDisplay.add(Box.createHorizontalGlue());

		nameDisplay.add(playerName2);
		nameDisplay.add(Box.createHorizontalGlue());

		newGame.addActionListener(new ActionListener() {

			// request the names from the users 
			@Override
			public void actionPerformed(ActionEvent e) {

				do {
					name1 = JOptionPane.showInputDialog("What is your name?");
					name2 = JOptionPane.showInputDialog("What is your name?");

					playerName.setText("player: " + getName1());

					playerName2.setText("player: " + getName2());

				} while (getName1() == null || getName2() == null || getName1().equals("") || getName2().equals("")); 

			}
		});

		JButton endGame = new JButton("Quit Game"); // the button to end the game
		nameDisplay.add(endGame);
		endGame.addActionListener(new ActionListener() {

			// show message when user wants to quit game
			@Override
			public void actionPerformed(ActionEvent e) {

				Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

				int confirm = 0;
				confirm = JOptionPane.showConfirmDialog(null, "Are you sure to quit?", null,
						JOptionPane.YES_NO_OPTION, confirm, new ImageIcon(image));
				if (confirm == JOptionPane.YES_OPTION) {
					frame.dispose();

				} else {
				}

			}
		});

		nameDisplay.addSeparator();

		JPanel board1 = myBattleBoard.createBoard();
		GameDisplay.add(board1);

	}

	public final JComponent getGui(JFrame f) {

		this.frame = f;
		return GameDisplay;
	}


	public static JLabel getWhoseTurn() {
		// get whose turn it is
		return whoseTurn;
	}

	public static JLabel getPlayerOneScore() {
		// get the score for first player
		return playerOneScore;
	}

	public static void setPlayerOneScore(JLabel playerOneScore) {
		// set the display for the score of first player
		SetGameDisplay.playerOneScore = playerOneScore;
	}

	public static JLabel getPlayerTwoScore() {

		// get the score for the second player
		return playerTwoScore;
	}

	public static void setPlayerTwoScore(JLabel playerTwoScore) {
		// set the display for the score of second player
		SetGameDisplay.playerTwoScore = playerTwoScore;
	}

	public static String getName1() {
		// get the name of the first player
		return name1;
	}

	public static String getName2() {
		// get the name of the second player
		return name2;
	}
}
