package Pieces;

import java.util.ArrayList;

public abstract class Piece {
	
	private int[] coordinates;
	private int color;
	
	/**
	 * 
	 * @param color 0 for white and 1 for black
	 * @param x
	 * @param y
	 */
	public Piece(int color, int x, int y){
		this.coordinates = new int[]{x,y};
		this.color = color;
	}
	
	/**
	 * Generic move method required for each piece.
	 * 
	 * @param direction defined by the matrix below
	 * 1 | 2 | 3
	 * 4 | x | 6
	 * 7 | 8 | 9
	 * 
	 * @param spaces how many spaces are to be traveled
	 * @return returns a boolean determining if it is a valid move for that piece
	 */
	public abstract ArrayList<int[]> move(int[] end);
	
	public ArrayList<int[]> constructPath(int[] end){
		int[] current = this.getCoordinates();
		ArrayList<int[]> path = new ArrayList<int[]>();
		
		current = getSpace(getDirection(end), current);
		while(end[0] != current[0] || end[1] != current[1]){
			path.add(current);
			current = getSpace(getDirection(end), current);
		}
		
		return path;

	}
	
	/**
	 * Returns direction headed to get to new space
	 * 
	 * @param end
	 * @return
	 */
	public int getDirection(int[] end){

		if(coordinates[0] < end[0]){ //Moving to the right
			if(coordinates[1] < end[1]) // Moving up
				return 3;
			else if(coordinates[1] > end[1]) // Moving down
				return 9;
			else //Same y-axis
				return 6;
		}else if(coordinates[0] > end[0]){ // Moving to the Left
			if(coordinates[1] < end[1]) //Moving up
				return 1;
			else if(coordinates[1] > end[1]) //Moving down
				return 7;
			else  // Same y-axis
				return 4;
			
		}else{ //Same x-axis
			if(coordinates[1] < end[1]) //Moving up
				return 2;
			else if(coordinates[1] > end[1]) //Moving down
				return 8;
		}
		
		return 0;

	}
	
	/**
	 * Returns next spaces based on current location and direction
	 * 
	 * @param direction
	 * @param current
	 * @return
	 */
	public int[] getSpace(int direction, int[] current){
		int[] space = new int[2];
		
		switch(direction){
		case 1:
			space[0] = current[0] - 1;
			space[1] = current[1] + 1;
			break;
		case 2:
			space[0] = current[0];
			space[1] = current[1] + 1;
			break;
		case 3:
			space[0] = current[0] + 1;
			space[1] = current[1] + 1;
			break;
		case 4:
			space[0] = current[0] - 1;
			space[1] = current[1];
			break;
		case 6:
			space[0] = current[0] + 1;
			space[1] = current[1];
			break;
		case 7:
			space[0] = current[0] - 1;
			space[1] = current[1] - 1;
			break;
		case 8:
			space[0] = current[0];
			space[1] = current[1] - 1;
			break;
		case 9:
			space[0] = current[0] + 1;
			space[1] = current[1] - 1;
			break;
		}
		
		return space;
	}
	
	/**
	 * Euclidean distance between spaces
	 * 
	 * @param end
	 * @return
	 */
	public int getDistance(int[] end){
		return Math.abs(this.getCoordinates()[0] - end[0]) + Math.abs(this.getCoordinates()[1] - end[1]);

	}

	public int[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(int x, int y) {
		this.coordinates[0] = x;
		this.coordinates[1] = y;
	}
	
	public void setCoordinates(int[] coordinates) {
		this.coordinates = coordinates;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	

}
