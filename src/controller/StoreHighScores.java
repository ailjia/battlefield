package controller;

import java.io.BufferedReader;

// there are some warning messages down below

// need to store the highest 3 scores, instead of 1
// there are some warning messages below

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class StoreHighScores {

	/*
	 * this class store the score after the game ends to a file
	 * it reads the file and fine out the top 3 players
	 */

	private static List<String> gameScores = new ArrayList<String>(); // the list to store highest scores
	private static String highestPlayer; // the name of the top players

	public List<String> getGameScores() {
		// get the list of top scores
		return gameScores; 
	}

	public StoreHighScores() {
		// constructor for the class
		gameScores = new ArrayList<>();
	}

	public static void loadGameScoresFromFile(String fileName) {
		// the method to load the game scores from the stored file
		File file = new File(fileName);
		FileReader fr;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String currentLine;
			while ((currentLine = br.readLine()) != null) {
				gameScores.add(currentLine);
			}

			highestPlayer = loadMaxScores(); // the best players and the scores
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String loadMaxScores() {
		// the method to identify the top 3 players based on the data stored in the file

		int max1 = 0;
		int max2 = 0;
		int max3 = 0;

		String highestPlayer1 = "NULL"; // initialize the value of player as null
		String highestPlayer2 = "NULL"; // initialize the value of player as null
		String highestPlayer3 = "NULL"; // initialize the value of player as null
		for (String str : gameScores) {
			String[] split = str.split(":");
			int score = Integer.parseInt(split[1]);
			if (score > max1) {
				max3 = max2;
				highestPlayer3 = highestPlayer2;
				max2 = max1;
				highestPlayer2 = highestPlayer1;

				max1 = score;
				highestPlayer1 = str;
			} else if (score > max2) {
				max3 = max2;
				highestPlayer3 = highestPlayer2;

				max2 = score;
				highestPlayer2 = str;
			} else if (score > max3) {

				max3 = score;
				highestPlayer3 = str;
			}
		}
		String highestPlayers = "Top1 " + "\n" + highestPlayer1 + "\n" + "Top2 " + "\n" + highestPlayer2 + "\n"
				+ "Top3 " + "\n" + highestPlayer3;

		return highestPlayers;
	}

	public static void appendGameScoreToFile(String winnerName, int score) {
		// this will append the score at the end of the text file
		File file = new File("scores.txt"); // the file where the scores are saved
		try {
			FileWriter fw = new FileWriter(file, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(winnerName + ":" + score);
			pw.flush(); // make changes permanent

		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	public static String getHighestPlayer() {
		// get the top players and the scores
		return highestPlayer;
	}
}