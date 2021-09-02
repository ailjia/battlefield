package model;

import java.awt.Color;
import java.util.List;

public class Destroyer extends Ship {

	// this class inherits from the ship and specifies a particular type of ship

	private final int pointPerHit = 4; // the unique score per hit, for this kind of ship
	private final static int shipLength = 2; // the length of the ship

	private final static Color color = Color.YELLOW; // the color of the ship


	public Destroyer(List<Coordinates> cList) {
		// this constructs the ship with a list of coordinates
		super(cList);
		name = "destroyer ship";
	}


	public Destroyer() {
		// sets up a battle ship randomly with the right length
		super(setup(shipLength));
		name = "destroyer ship";
	}

	public int getScoreMultipiler() {
		// when the ship is sinking, the score will double with respect to the original score per hit
		return super.getScoreMultipiler() * this.pointPerHit;
	}

	public int getShiplength() {
		// get the length of the ship
		return shipLength;
	}

	public int getPointperhit() {
		// get the unique point per hit for this kind of ship
		return pointPerHit;
	}


	@Override
	public int getType() {
		// get the type of the ship, each ship type represents by a unique integer

		return 2;
	}

	@Override
	public Color getColor() {
		// get the color of this kind of ship
		return color;
	}

}
