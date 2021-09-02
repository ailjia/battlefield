package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import view.StartingPanel;

public abstract class Ship {

	/* this class defines the ship, which is a parent for different ship types
	 * the ship can generate itself a random ship and can check if there are overlaps
	 * it can check if a ship has been hit, and then calculate the score 
	 * */


	public String name = null;
	private List<Coordinates> coordinatesList = new ArrayList<Coordinates>(); // list of coordinates to store a ship's locations
	private int hitTimes = 0; // how many times a ship has been hit, starting from 0 for each ship


	public Ship(List<Coordinates> cList) {  

		// this is the constructor for ship

		coordinatesList = cList; 

	}




	public boolean shipOverlap(Ship ship) {
		// check if ship overlaps

		boolean overlaps = false; // false means the ships do not overlap
		for(Coordinates c: coordinatesList) {
			if(ship.coordinatesList.contains(c)) {
				overlaps = true;
			}
		}

		return overlaps;	
	}

	public boolean shipOverlap(Collection <Ship> ship) {

		// check if ship overlaps

		boolean overlaps = false; // false means the ships do not overlap
		for(Ship c: ship) {
			if(shipOverlap(c)) {
				overlaps = true;
			}
		}

		return overlaps;	
	} 

	public static List<Coordinates> setup(int length) {

		// set up ship with a random location, random direction, and defined length

		List<Coordinates> coordinatesList = new ArrayList<Coordinates>();
		int dx = 0; // use this integer later to increment the coordinate
		int dy = 0; // use this integer later to increment the coordinate

		Random rand = new Random(); // to generate random numbers
		int dir = rand.nextInt(2); // get a random number between 0 and 1

		if(dir == 0) {
			dx = 1; // Direction.VERTICAL.num
		} else if(dir == 1) {
			dy = 1;
		} else {
			System.out.println("illegal direction");
		}

		Coordinates currentCoordinates; // the initiate coordinate randomly generated

		int userSpecified = StartingPanel.getUserSelectedSize(); // board size specified by the user
		Coordinates boardSize = new Coordinates(userSpecified,userSpecified); // a coordinate that contains info about board size

		do {
			coordinatesList.clear();
			currentCoordinates = Coordinates.getRandomCoordinates(boardSize);


			for(int i = 0; i<length; i++) {

				currentCoordinates = currentCoordinates.delta(dx, dy);
				coordinatesList.add(currentCoordinates);  // some problem with the codes here

			}
		} while(coordinatesList.get(length-1).getX() >userSpecified-1 || coordinatesList.get(length-1).getY() > userSpecified-1);

		return coordinatesList;
	}



	abstract public int getShiplength(); // get length of each ship type

	public boolean checkHit(int xPosition, int yPosition) {

		// check if a ship has been hit
		if (getCoordinatesList().contains(new Coordinates(xPosition, yPosition))) {
			hitTimes += 1;
			return true;
		} else {
			return false;
		}

	}

	public int getScoreMultipiler() {
		// if a hit makes a ship sink, the score is multiplied by 2
		if (hitTimes < getShiplength()) {
			return 1;
		} else {
			return 2;
		}
	}


	public boolean checkShipValid(int shipLength) {

		// check if a ship uploaded by the user is valid in terms of its shape and length
		boolean shipValid = false;
		int minX = Integer.MAX_VALUE; // a minimum integer used later for comparison purpose
		int minY = Integer.MAX_VALUE; // a maximum integer used later for comparison purpose
		for (Coordinates coord : getCoordinatesList()) {
			if (coord.getX() < minX) {
				minX = coord.getX();
			}
			if (coord.getY() < minY) {
				minY = coord.getY();
			}
		}

		if (getCoordinatesList().size() != shipLength) {
			shipValid = false;
		}

		else {
			List<Coordinates> coordinatesOne = new ArrayList<Coordinates>(); // a simulated coordinate to check if a ship is placed along a line
			for (int i = minX; i < minX + getShiplength(); i++) {

				coordinatesOne.add(new Coordinates(i, minY));
				if (getCoordinatesList().equals(coordinatesOne)) {
					shipValid = true;
				}
			}
			List<Coordinates> coordinatesTwo = new ArrayList<Coordinates>(); // a simulated coordinate to check if a ship is placed along a line
			for (int i = minY; i < minY + getShiplength(); i++) {

				coordinatesTwo.add(new Coordinates(minX, i));
				if (getCoordinatesList().equals(coordinatesTwo)) {
					shipValid = true;
				}
			}
		}
		return shipValid;

	}


	public int getHitTimes() {

		// get how many times a ship has been hit
		return hitTimes;
	}

	public List<Coordinates> getCoordinatesList() {

		// get a ship's position as a coordinate list
		return coordinatesList;
	}

	public abstract int getType(); // get type of a ship, each type represented by an integer

	public abstract Color getColor(); // get color of a ship


}

