package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import view.SetGameDisplay;


public class Board {

	/* this class sets up the ships correctly
	 * and also identifies which ship among all ships have been hit
	 */

	private List<Ship> allShips = new ArrayList<Ship>(); // a list of all ships


	private List<Coordinates> hitPositions = new ArrayList<Coordinates>(); // a list of positions being hit

	public Ship lastShipHit; // this shows the ship that's being hit, if null, then it is ocean

	public boolean validClickCheck(int x, int y) {
		boolean validOrNot; // if a click if valid, then this is true

		// this checks if a click is valid by checking if this position was clicked

		if (SetGameDisplay.getName1() == "" || SetGameDisplay.getName2() == "") {
			validOrNot = false;
			System.out.println("please give valid names for both players");
			JOptionPane.showMessageDialog(null, "please give valid names for both players");
		} else {
			if (hitPositions.contains(new Coordinates(x, y))) {
				validOrNot = false;
				System.out.println("invalid click");
			} else {
				validOrNot = true;
			}
		}
		return validOrNot;

	}

	public int calculateScoreFromHit(int xPosition, int yPosition) {

		// a method to return the score of hitting a ship, taken into account whether unique points for each ship, and if ship is sinking

		int earnedScore = 0; // the score after a hit

		lastShipHit = null; // the kind of ship that's been hit

		if (validClickCheck(xPosition, yPosition) == true) {
			hitPositions.add(new Coordinates(xPosition, yPosition));

			for (Ship ship : getAllShips()) {
				if (ship.checkHit(xPosition, yPosition) == true) {

					earnedScore = ship.getScoreMultipiler();
					lastShipHit = ship;
					System.out.println("The ship being hit: " + ship.name);
				} 
			}
		}

		return earnedScore;
	}

	public boolean addShip(Ship s) {

		// if a ship does not overlap, then add the ship
		if(s.shipOverlap(getAllShips())) {
			return false;
		} else {
			allShips.add(s);
			return true;
		}
	}


	public void addAllShipsRandomly() {

		boolean result; // whether the ship overlaps, true means the ships do not overlap

		do {
			allShips.clear();
			Carrier carrier = new Carrier(); // constructs a carrier ship
			BattleShip battleShip = new BattleShip(); // constructs a battle ship
			Destroyer destroyer = new Destroyer(); // constructs a destroyer ship
			Submarine submarine = new Submarine();	 // constructs a submarine ship

			result = addShip(carrier) && addShip(battleShip) && addShip(destroyer) && addShip(submarine);

		}
		while(! result);
	}


	public Ship getLastShipHit() {
		// get the current position being hit
		return lastShipHit;
	}

	public void setAllShips(List<Ship> allShips) {

		// the setter for allShips
		this.allShips = allShips;
	}


	public List<Ship> getAllShips() {
		// the getter for allShips, return the copy of the list that is not editable
		return Collections.unmodifiableList(allShips);
	}



}
