package controller;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import view.StartingPanel;

public class Main {

	public static void main(String[] args) {

		Runnable r = new Runnable() {

			@Override
			public void run() {
				StartingPanel st = new StartingPanel(); // Initiates the starting panel

				JFrame frameGameSelection = new JFrame("Battleship game: selection screen"); // JFrame for the first screen

				frameGameSelection.setLayout(new BorderLayout());

				frameGameSelection.add(st.getGui(), BorderLayout.NORTH);
				frameGameSelection.add(st.getStart(), BorderLayout.CENTER);
				frameGameSelection.add(st.aboutGame(), BorderLayout.SOUTH);

				frameGameSelection.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frameGameSelection.setLocationByPlatform(true);

				frameGameSelection.pack();
				frameGameSelection.setResizable(false);

				frameGameSelection.setVisible(true);

			}
		};
		SwingUtilities.invokeLater(r);

	}
}