package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.BattleShip;
import model.Carrier;
import model.Coordinates;
import model.Destroyer;
import model.Ship;
import model.Submarine;
import view.StartingPanel;


public class ReadCustomFile {

	/*
	 * this is the class that reads the user defined file and create the ships accordingly
	 * it will identify if the ships are valid, if ships are missing, and also if ship names are correct
	 */

	private List<Ship> allShips = new ArrayList<Ship>(); // a list of all ships 

	public boolean checkAllShipsAvailable() {

		// this method checks if there are ships missing, and if yes, will feedback the file is not okey

		int[] shipCounter = new int[4];  // the counter to count if each ship type exists once
		for(Ship ship: allShips) {
			shipCounter[ship.getType()]++;
		}

		boolean ok = true; // true if no ships are missing
		for(int i = 0; i < 4; i++) {
			if(shipCounter[i] < 1) {
				ok = false;
			}

		}
		return ok;
	}

	public boolean checkIfShipOutOfBoard() {

		// if true, then the ships are not placed outside of the board dimension
		boolean ok = true;
		int dim = StartingPanel.getUserSelectedSize();
		for(Ship ship: allShips) {
			if (ship.getCoordinatesList().get(ship.getShiplength()-1).getX() > dim -1 || ship.getCoordinatesList().get(ship.getShiplength()-1).getY() > dim -1) {
				ok = false;
			}
		}
		return ok;
	}

	public boolean checkIfShipValid() {
		// if true, then the ships are on a straight continuous line
		boolean ok = true; 
		for(Ship ship: allShips) {
			if(ship.checkShipValid(ship.getShiplength()) == false) {
				ok = false;
			}
		}
		return ok;
	}

	public boolean checkIfTooManyShips() {
		
		// this method checks if the shape of ship is either horizontal and vertical & without space between ship parts
		boolean ok = true; // true if the ships are on a straight continuous line
		int shipCounter = 0;
		for(Ship ship: allShips) {
			shipCounter += 1;
			if(shipCounter > 4) {
				ok = false;
			}
		}
		return ok;
	}


	public boolean readUserData(String fileName) throws ShipException {

		// this reads the user data which should have a predefined format

		File file = new File(fileName); // read the corresponding file identified by its file name
		boolean succeed = false; // if true, then successfully read in the ship data
		try {
			Scanner scanner = new Scanner(file); // a scanner to read line by line
			while (scanner.hasNextLine()) {

				Ship ship = null; // the kind of the ship that has been read
				String data = scanner.nextLine();
				String splitted[] =data.split(";",2); 

				if (splitted[0].equals("Carrier")) { // data.substring(0, data.indexOf(";"))).equals("Carrier")
					ship = new Carrier(readShipData(data));

				} else if (splitted[0].equals("Battleship")) {
					ship = new BattleShip(readShipData(data));

				} else if (splitted[0].equals("Submarine")) {
					ship = new Submarine(readShipData(data));

				} else if (splitted[0].equals("Destroyer")) {
					ship = new Destroyer(readShipData(data));

				} 
				if(ship != null) {
					getAllShips().add(ship);
				}
			}
			succeed = true;
			scanner.close();

		} catch (FileNotFoundException e) {
			succeed = false;
			System.out.println("The file does not exist.");
			ShipException se = new ShipException("The file does not exist.");
			throw se;
		} catch (NumberFormatException e) {
			succeed = false;
			System.out.println(e.getMessage());
			System.out.println("The format of data is incorrect.");
			ShipException se = new ShipException("The format of data is incorrect.");
			throw se;
		} 
		return succeed;

	}

	public List<Coordinates> readShipData(String data) {

		// this methods read the data in each line, and return the position of the ship in a list of coordinates

		List<Coordinates> cList = new ArrayList<Coordinates>();

		int indexOfFirstSemiColon = data.indexOf(";") + 1;
		String dataPart = data.substring(indexOfFirstSemiColon);
		String[] splitData = dataPart.split(";");

		for (int i = 0; i < splitData.length; i++) {
			if (splitData[i].contains("*")) {
				String[] sSplit = splitData[i].split("\\*");
				int x = Integer.parseInt(sSplit[0].trim()) - 1;
				int y = Integer.parseInt(sSplit[1].trim()) - 1;
				cList.add(new Coordinates(x, y));
			}
		}
		return cList;
	}

	public List<Ship> getAllShips() {
		// a method to get all ships
		return allShips;
	}


}