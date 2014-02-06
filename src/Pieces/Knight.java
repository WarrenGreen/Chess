package Pieces;

import java.util.ArrayList;

public class Knight extends Piece {

	public Knight(int color, int x, int y) {
		super(color, x, y);
	}

	@Override
	public ArrayList<int[]> move(int[] end) {
		if(this.getDistance(end) <= 0) return null;
		
		return new ArrayList<int[]>();

	}

}
