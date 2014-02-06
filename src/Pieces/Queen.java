package Pieces;

import java.util.ArrayList;

public class Queen extends Piece {

	public Queen(int color, int x, int y) {
		super(color, x, y);
	}

	@Override
	public ArrayList<int[]> move(int[] end) {
		int distance = this.getDistance(end);
				
		//check to make sure that the number of spaces fits the rules for this piece.
		if(distance <= 0) return null;
				
		return constructPath(end);
	}

}
