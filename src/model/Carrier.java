package model;

import java.awt.Color;
import java.util.List;


public class Carrier extends Ship {

	// this class inherits from the ship and specifies a particular type of ship

	private final int pointPerHit = 1; // the unique point per hit for this ship
	private final static int shipLength = 5; // the length of the ship
	private final static Color color = Color.BLACK; // the color of the ship


	public Carrier(List<Coordinates> cList) {
		// this constructs the ship with a list of coordinates

		super(cList);
		name = "carrier ship";
	}



	public Carrier() {
		// this randomly sets up the carrier ship with its defined length
		super(setup(shipLength));
		name = "carrier ship";
	}

	public int getScoreMultipiler() {
		// when the ship is about to sink, the score will double with respect to the original score per hit
		return super.getScoreMultipiler() * this.pointPerHit;
	}


	public int getShiplength() {
		// get the length of the ship
		return shipLength;
	}

	public int getPointperhit() {
		// get the point per hit for this type of ship
		return pointPerHit;
	}


	@Override
	public int getType() {
		// get type of ship, each ship type represented by a unique integer
		return 1;
	}

	@Override
	public Color getColor() {
		// get the color of the ship
		return color;
	}


}
