package Pieces;

import java.util.ArrayList;

public class Bishop extends Piece {

	public Bishop(int color, int x, int y) {
		super(color, x, y);
	}

	@Override
	public ArrayList<int[]> move(int[] end) {
		int distance = this.getDistance(end);
		int direction = this.getDirection(end);
		
		//check to make sure that the number of spaces fits the rules for this piece.
		if(distance <= 0) return null;
		
		//check to make sure that the direction is valid
		if(direction % 2 == 0)
			return null;

		return this.constructPath(end);
	}

}
