package model;

import java.util.Random;

import view.StartingPanel;

public class Coordinates {

	/*
	 * this class defines the position of the ships, and is used to store location of the ships, and positions that's been hit
	 */

	private int x; // the x(row) position of a coordinate
	private int y; // the y(column) position of a coordinate

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getX();
		result = prime * result + getY();
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		// this methods allows identification two equal coordinates
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (getX() != other.getX())
			return false;
		if (getY() != other.getY())
			return false;
		return true;
	}

	public Coordinates(int xAxis, int yAxis) {

		// constructor for the coordinates
		x = xAxis; // the x(row) value of the coordinates
		y = yAxis; // the y(column) value of the coordinates

	}

	public static Coordinates getRandomCoordinates(Coordinates boardsize) {

		// method to get a random coordinates, subject the size identified by the user

		Random rand = new Random(); // an instance variable to generate random numbers
		int userSpecified = StartingPanel.getUserSelectedSize(); // board size specified by the user
		boardsize = new Coordinates(userSpecified, userSpecified); // a coordinate to specify the board size
		int x = rand.nextInt(boardsize.x); // the randomly generated position of x
		int y = rand.nextInt(boardsize.y); // the randomly generated position of y
		return new Coordinates(x, y);
	}


	public Coordinates delta(int dx, int dy) {

		// a method to generate a new coordinate, that is next to the original coordinate
		return new Coordinates(x+dx, y+dy);
	}

	public int getX() {

		// get the x position of the coordinate
		return x;
	}

	public int getY() {

		// get the y position of the coordinate
		return y;
	}
}
