package Pieces;

import java.util.ArrayList;

public class King extends Piece {

	public King(int color, int x, int y) {
		super(color, x, y);
		
	}

	@Override
	public ArrayList<int[]> move(int[] end) {
		
		int distance = this.getDistance(end);
		
		//check to make sure that the number of spaces fits the rules for this piece.
		if(distance > 1 || distance <= 0) return null;
		
		return constructPath(end);

	}
	
	public ArrayList<int[]> getMoves(){
		ArrayList<int[]> moves = new ArrayList<int[]>();
		int x = this.getCoordinates()[0];
		int y = this.getCoordinates()[1];
		
		for(int i=-1;i<=1;i++){
			for(int j=-1;j<=1;j++){
				// Don't add the spot the king is at
				if(i != 0 || j != 0 )
					moves.add(new int[]{x+i,y+j});
			}
		}
		
		return moves;
	}


}
