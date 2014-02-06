package chess;

import java.util.ArrayList;
import java.util.HashMap;

import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;

public class Board {
	HashMap<int[], Piece> board;
	ArrayList<Piece> whiteCaptured;
	ArrayList<Piece> blackCaptured;

	public Board() {
		board = new HashMap<int[], Piece>();
		whiteCaptured = new ArrayList<Piece>();
		blackCaptured = new ArrayList<Piece>();

		populateBoard();
	}

	public boolean move(int[] start, int[] end) {
		//if the move causes check or breaks rules then fail it 
		if (willCheck(start, end) || !canMove(start, end, board))
			return false;

		Piece toMove = board.get(start);
		capturePiece(toMove, end);

		// update board with new move
		toMove.setCoordinates(end);
		board.put(start, null);
		board.put(end, toMove);

		return true;

	}

	public boolean canMove(int[] start, int[] end, HashMap<int[], Piece> board) {
		Piece toMove = board.get(start);

		ArrayList<int[]> path = toMove.move(end);

		// Determine if the move was valid for that piece
		if (path == null)
			return false;

		// Determine that the path taken is empty
		for (int[] current : path) {
			if (board.get(current) != null)
				return false;
		}
		
		return true;

	}

	private void capturePiece(Piece toMove, int[] space) {
		// Determine if there is a captured piece
		Piece cap = board.get(space);

		if (cap != null) {
			// determine if captured piece is same color
			if (cap.getColor() == toMove.getColor())
				return;

			// add captured piece to respective list
			if (cap.getColor() == 0)
				whiteCaptured.add(cap);
			else
				blackCaptured.add(cap);
		}

	}
	
	public boolean willCheck(int[] start, int[] end){
		// Create hypothetical board as if the move happpened
		HashMap<int[], Piece> hypBoard = board;
		Piece toMove = hypBoard.get(start);
		toMove.setCoordinates(end);
		hypBoard.put(start, null);
		hypBoard.put(end, toMove);
		
		//Test if the move will cause check
		if(inCheck(toMove.getColor(), hypBoard).size() == 0) return false;
		else{ // if the move causes check reset the piece settings
			toMove.setCoordinates(start);
			return false;
		}
		
	}

	public ArrayList<Piece> inCheck(int team, HashMap<int[], Piece> board){
		Piece king = getKing(team);
		ArrayList<Piece> causingCheck = new ArrayList<Piece>();
		
		//check if any piece will cause check
		for(Piece curr: board.values()){
			if(causeCheck(curr, king, board)) causingCheck.add(curr);
		}
		
		return causingCheck;
		
	}

	public boolean causeCheck(Piece cPiece, Piece king, HashMap<int[], Piece> board) {

		// Check if piece can kill the king
		if (king != null
				&& cPiece != king
				&& canMove(cPiece.getCoordinates(), king.getCoordinates(), board))
			return true;

		return false;
	}
	
	public boolean checkMate(int team, ArrayList<Piece> causes){
		King king = getKing(team);
		
		//Check if king can move out of checkmate
		for(int[] pot: king.getMoves())
			if(canMove(king.getCoordinates(), pot, board)) return false;
		
		/*
		 * If there is only one path leading to check, then it can be blocked else immediate checkmate
		 * 
		 * for everyone of your pieces, check if it can move to any spot in the path of your check
		 */
		if(causes.size() > 1) return true;
		
		ArrayList<ArrayList<int[]>> paths = new ArrayList<ArrayList<int[]>>();
		for(Piece curr: causes)
			paths.add(curr.constructPath(king.getCoordinates()));
		
		for(Piece curr: getPieces(team)){
			for(ArrayList<int[]> causer: paths){
				for(int[] space: causer){
					if(canMove(curr.getCoordinates(), king.getCoordinates(), board)) return false;
				}
			}
		}
		
		
		return true;
	}

	public King getKing(int color) {

		// Find opposing king piece
		for (Piece curr : board.values()) {
			if (curr.getClass() == King.class && curr.getColor() == color) 
				return (King) curr;

		}
		
		return null;
	}
	
	public ArrayList<Piece> getPieces(int color){
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		
		for(Piece curr: board.values()){
			if(curr.getColor() == color)
				pieces.add(curr);
		}
		
		return pieces;
	}

	private void populateBoard() {

		// Populate empty board space
		for (int i = 1; i <= 8; i++) {
			for (int j = 3; j <= 6; j++) {
				board.put(new int[] { i, j }, null);
			}
		}

		// Generate pawns
		for (int i = 1; i <= 8; i++) {
			board.put(new int[] { i, 2 }, new Pawn(0, i, 2));
			board.put(new int[] { i, 2 }, new Pawn(1, i, 7));
		}

		// Rooks
		board.put(new int[] { 1, 1 }, new Rook(0, 1, 1));
		board.put(new int[] { 1, 8 }, new Rook(1, 1, 8));
		board.put(new int[] { 8, 1 }, new Rook(0, 8, 1));
		board.put(new int[] { 8, 8 }, new Rook(1, 8, 8));

		// Knights
		board.put(new int[] { 2, 1 }, new Knight(0, 2, 1));
		board.put(new int[] { 2, 8 }, new Knight(1, 2, 8));
		board.put(new int[] { 7, 1 }, new Knight(0, 7, 1));
		board.put(new int[] { 7, 8 }, new Knight(1, 7, 8));

		// Bishops
		board.put(new int[] { 3, 1 }, new Bishop(0, 3, 1));
		board.put(new int[] { 3, 8 }, new Bishop(1, 3, 8));
		board.put(new int[] { 6, 1 }, new Bishop(0, 6, 1));
		board.put(new int[] { 6, 8 }, new Bishop(1, 6, 8));

		// Queens
		board.put(new int[] { 4, 1 }, new Queen(0, 4, 1));
		board.put(new int[] { 4, 8 }, new Queen(1, 4, 8));

		// Kings
		board.put(new int[] { 5, 1 }, new King(0, 5, 1));
		board.put(new int[] { 5, 8 }, new King(1, 5, 8));
	}

}
