package model;

import java.awt.Color;
import java.util.List;

public class BattleShip extends Ship {

	// this class inherits from the ship and specifies a particular type of ship

	private final int pointPerHit = 2; // unique point after hitting the battleship
	private final static int shipLength = 4; // length of the ship
	private final static Color color = Color.WHITE; // color of the ship

	public BattleShip(List<Coordinates> cList) {
		// this constructs the ship with a list of coordinates
		super(cList);
		name = "battle ship";
	}

	public BattleShip() {
		// sets up a battle ship randomly with the right length
		super(setup(shipLength));
		name = "battle ship";

	}

	@Override
	public int getScoreMultipiler() {
		// when the ship is sinking, the score will double with respect to the original
		// score per hit
		return super.getScoreMultipiler() * this.pointPerHit;
	}

	public int getShiplength() {
		// get the length of the ship
		return shipLength;
	}

	public int getPointperhit() {

		// get the point per hit
		return pointPerHit;
	}

	@Override
	public int getType() {
		// get the type of the ship, each ship type represents by a unique integer
		return 0;
	}

	@Override
	public Color getColor() {

		// get the color of the ship
		return color;
	}

}
