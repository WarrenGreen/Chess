package Pieces;

import java.util.ArrayList;

public class Pawn extends Piece {
	private boolean moved;

	public Pawn(int color, int x, int y) {
		super(color, x, y);
		moved = false;
	}

	@Override
	public ArrayList<int[]> move(int[] end) {
		int direction = this.getDirection(end);
		int distance = this.getDistance(end);
		
		// Allow the pawn to move two spaces if it is their first move
		if((!moved && distance > 1) || distance > 2 || distance <=0 ) return null;
		
		// Allow the pawn to only move forward
		if(direction >= 4) return null;
		
		return this.constructPath(end);
	}

}
