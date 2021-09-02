package view;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.ReadCustomFile;
import controller.ShipException;
import controller.StoreHighScores;

public class StartingPanel {

	/*
	 * this class creates a window where users can select different game settings
	 * 
	 */

	private static String userScoringPreference; // to store user specified scoring preference
	private static String shipPlacementChoice; // to store user specified ship placement preference
	private static int userSelectedSize; // to store the user specified board size

	private final JPanel welcome = new JPanel(new BorderLayout(3, 3)); // JPanel to display welcome message
	private final JPanel gameSetting = new JPanel(new BorderLayout(3, 3)); // JPanel to display game setting buttons
	private final JPanel gameStart = new JPanel(new BorderLayout(3, 3)); // display starting button for the game
	private final JPanel aboutGame = new JPanel(new BorderLayout(3, 3)); // JPanel to display other info about the game
	ReadCustomFile newFile;
	private static String filename = ""; // to store the value of the file name entered by the user
	private boolean startFileOpen = false; // indicate when the file operation has been started and completed

	public StartingPanel() {

		// constructor of the class
		userScoringPreference = "player2 has no bonus score"; // default setting for bonus availability to player two
		shipPlacementChoice = "random"; // default way to place ships
		userSelectedSize = 8; // default size of the board

		makeUserChoiceButtons();
		makeStartGameButton();
		makeRuleScoreAndExitButtons();
	}

	public final void makeUserChoiceButtons() {

		gameSetting.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel welcomeAndPreferenceSetter = new JPanel(new BorderLayout());

		JToolBar preferenceSetter = new JToolBar();
		preferenceSetter.setFloatable(false);

		JLabel message = new JLabel("Wecome to battleship");
		JLabel message2 = new JLabel("Please selection option and get started");
		JLabel messageBoardSize = new JLabel("Choose the board size");

		JToolBar welcomeMessage = new JToolBar();
		welcomeMessage.setFloatable(false);

		welcome.add(welcomeMessage);

		welcomeMessage.setLayout(new BoxLayout(welcomeMessage, BoxLayout.LINE_AXIS));

		welcomeMessage.add(Box.createHorizontalGlue());

		welcomeMessage.add(message);
		welcomeMessage.add(Box.createHorizontalGlue());

		welcomeAndPreferenceSetter.add(welcomeMessage, BorderLayout.NORTH);
		welcomeAndPreferenceSetter.add(message2, BorderLayout.CENTER);
		welcomeAndPreferenceSetter.add(messageBoardSize, BorderLayout.EAST);

		welcomeAndPreferenceSetter.add(preferenceSetter, BorderLayout.SOUTH);
		gameSetting.add(welcomeAndPreferenceSetter);

		preferenceSetter.setLayout(new BoxLayout(preferenceSetter, BoxLayout.LINE_AXIS));

		JButton shipPlacement = new JButton("Choose Ship Placement");
		preferenceSetter.add(shipPlacement);
		preferenceSetter.add(Box.createHorizontalGlue());

		JButton scoringSystem = new JButton("Scoring System");

		preferenceSetter.add(scoringSystem);
		preferenceSetter.add(Box.createHorizontalGlue());

		scoringSystem.addActionListener(new ActionListener() {

			// requests the user to provide preference for scoring system, either player 2
			// has bonus, or not

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] options = { "player2 has bonus score", "player2 has no bonus score" }; // options to choose, if
																								// player 2 has bonus
				Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB); // empty image to remove the Java
																					// icon

				int x = 0; // the value that will be returned by the user, after selecting the choice
				x = JOptionPane.showOptionDialog(null, "Please select option", "Please select option",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image), options,
						null);

				if (x > -1) {
					userScoringPreference = options[x];
					System.out.println(getUserScoringPreference());
				}

			}
		});

		shipPlacement.addActionListener(new ActionListener() {

			// requests the user to provide preference for ship placement, either random, or
			// through a file from user

			@Override
			public void actionPerformed(ActionEvent e) {
				String[] shipPlacementOption = { "random", "user defined" }; // 2 different options to define ship
																				// positions

				Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB); // empty image to remove the Java
																					// icon

				int x; // the value that will be returned by the user, after selecting the choice
				x = JOptionPane.showOptionDialog(null, "Please select option", "Please select option",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image),
						shipPlacementOption, null);

				if (x > -1) {
					shipPlacementChoice = shipPlacementOption[x];
					System.out.println(shipPlacementOption[x]);
				}

				if (x == 1) {

					boolean fileExist = false; // if true, the file specified by user exists in the directory

					try {
						showOpenDialog();
						if (filename.equals("UserCancelled")) {
							return;
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}

					newFile = new ReadCustomFile();

					try {
						fileExist = newFile.readUserData(getFilename());
					} catch (ShipException e1) {
						showInputMessage(e1.getMessage());
					}

					while (newFile.checkAllShipsAvailable() == false || newFile.checkIfShipValid() == false
							|| newFile.checkIfTooManyShips() == false || newFile.checkIfShipOutOfBoard() == false) {

						if (newFile.checkAllShipsAvailable() == false) {

							fileExist = showInputMessage("Ships are missing or incorrect ship name. Upload new file");

						} else if (newFile.checkIfShipValid() == false) {
							fileExist = showInputMessage("The ships are not placed on a straight or continuous line");

						} else if (newFile.checkIfTooManyShips() == false) {
							fileExist = showInputMessage("There are too many ships");
						} else if (newFile.checkIfShipOutOfBoard() == false) {
							fileExist = showInputMessage("Ships are out of board dimension");
						}
						if (filename.equals("UserCancelled")) {
							break;
						}

					}
				}

			}

			private boolean showInputMessage(String message) {
				// a method to show the respective message to user, when there are problems with
				// the file

				boolean fileExist = false;
				JOptionPane.showMessageDialog(null, message);

				try {
					showOpenDialog();
					if (getFilename().equals("UserCancelled")) {
						return true;
					}
					fileExist = newFile.readUserData(getFilename());
				} catch (ShipException e) {
					e.printStackTrace();
					showInputMessage(e.getMessage());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return fileExist;
			}

		});

		JSpinner boardSize = new JSpinner(new SpinnerNumberModel(8, 8, 20, 1)); // the spinner for user to choose board
																				// size
		preferenceSetter.add(boardSize);
		boardSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				userSelectedSize = (int) boardSize.getValue();
			}
		});

	}

	public final void makeStartGameButton() {

		// create the button that enables starting of the game

		gameStart.setBorder(new EmptyBorder(10, 10, 10, 10));

		JToolBar toBeginGame = new JToolBar();

		toBeginGame.setFloatable(false);

		toBeginGame.setLayout(new BoxLayout(toBeginGame, BoxLayout.LINE_AXIS));

		gameStart.add(toBeginGame);

		JButton newGame = new JButton("Start Game");

		toBeginGame.add(Box.createHorizontalGlue());

		toBeginGame.add(newGame);
		toBeginGame.add(Box.createHorizontalGlue());

		newGame.addActionListener(new ActionListener() {

			// Initialize the game board for the game to start

			@Override
			public void actionPerformed(ActionEvent e) {

				Runnable r = new Runnable() {

					@Override
					public void run() {

						SetGameDisplay cb = new SetGameDisplay();

						JFrame f = new JFrame("Battlefield");

						f.add(cb.getGui(f));

						f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						f.setLocationByPlatform(true);

						f.pack();
						f.setMinimumSize(f.getSize());
						f.setVisible(true);

					}
				};
				SwingUtilities.invokeLater(r);
				System.out.println("now the game is about to start");
			}
		});

	}

	private String showOpenDialog() throws Exception {
		filename = "";
		startFileOpen = true;

		String folder = System.getProperty("user.dir");
		JFileChooser fc = new JFileChooser(folder);
		int result = fc.showOpenDialog(null);
		// if the user selects a file
		if (result == JFileChooser.APPROVE_OPTION) {
			// set the label to the path of the selected file
			filename = fc.getSelectedFile().getAbsolutePath();
			System.out.println(filename);

		}
		// if the user cancelled the operation, the ships will be randomly generated
		else {
			filename = "UserCancelled";
			System.out.println("the user cancelled the operation, the ships are now randomly generated");
			shipPlacementChoice = "random";
		}
		startFileOpen = false;

		return filename;
	}

	public final void makeRuleScoreAndExitButtons() {

		// the method to create info about the game rules, and the button to exist the game

		aboutGame.setBorder(new EmptyBorder(10, 10, 10, 10));
		aboutGame.setLayout(new BorderLayout());
		JToolBar aboutGameButtons = new JToolBar();

		aboutGameButtons.setLayout(new BoxLayout(aboutGameButtons, BoxLayout.LINE_AXIS));

		aboutGameButtons.setFloatable(false);
		aboutGame.add(aboutGameButtons);

		JButton rule = new JButton("Rule");
		aboutGameButtons.add(rule);
		aboutGameButtons.add(Box.createHorizontalGlue());

		rule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB); // empty image to remove the Java
																					// icon

				String message = "The game is played by two players \n" + "both will iteratively click on the button \n"
						+ "trying to hit the hidden ships. \n" + "when the part of a ship has been hit \n"
						+ "the color will change, \n" + "when it is a missed hit', \n" + "the color will turn blue. \n"
						+ "each hit will earn players scores \n" + "and the one with the highest score will win, \n"
						+ "when all ships sink. \n";

				JOptionPane.showMessageDialog(null, message, "How to play the game", JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(image));

			}
		});

		JButton highScore = new JButton("High Score");
		aboutGameButtons.add(highScore, BorderLayout.CENTER);
		aboutGameButtons.add(Box.createHorizontalGlue());

		highScore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB); // empty image to remove icon of
																					// Java

				StoreHighScores.loadGameScoresFromFile("scores.txt");

				JOptionPane.showMessageDialog(null,
						"the best players are: " + "\n" + StoreHighScores.getHighestPlayer(), null, 0,
						new ImageIcon(image));
			}
		});

		JButton endGame = new JButton("Exit");

		endGame.addActionListener(new ActionListener() {

			// enable the user to quit the game by clicking this button

			@Override
			public void actionPerformed(ActionEvent e) {

				Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

				int confirm = 0;
				confirm = JOptionPane.showConfirmDialog(null, "Are you sure to quit?", null, JOptionPane.YES_NO_OPTION,
						confirm, new ImageIcon(image));
				if (confirm == JOptionPane.YES_OPTION) {

					System.exit(0);

				} else {
				}

			}
		});

		aboutGameButtons.add(endGame, BorderLayout.EAST);

	}

	public final JComponent getGui() {
		// get the component related to game setting
		return gameSetting;
	}

	public final JComponent getStart() {
		// get the component for starting game
		return gameStart;
	}

	public final JComponent aboutGame() {
		// get the component related to game info
		return aboutGame;
	}

	public final JComponent getWelcome() {
		// get the component related to welcome message
		return welcome;
	}

	public static int getUserSelectedSize() {
		// get the user specified board dimension
		return userSelectedSize;
	}

	public static String getShipPlacementChoice() {
		// get the user specified preference for ship placement
		return shipPlacementChoice;
	}

	public static String getUserScoringPreference() {
		// get the user specified preference for scoring system
		return userScoringPreference;
	}

	public static String getFilename() {
		return filename;
	}

}
