package player;

import player.list.*;

/**
 *  An implementation of a Network game chess.  Keeps track of its location, color and whether it has been visited during a search for a Network winning pattern.
 *  Can provide a list of connections with other Network chess pieces.
 *  The color field indicates the chess piece's color, black (0) or white (1). The fields x and y are the x and y coordinates of this chess piece on 
 *  the gameboard. The board field refers to the gameboard that uses this chess piece. The field meet is a list containing the connections to other chips.
 *  The BLACK and WHITE fields are constants that indicate the integer representation of the black and white color on the gameboard.
 */
public class Chip{
	protected int color;
	protected int x, y;
	protected boolean marked = false;
	protected Gameboard board;
	protected SList meet;
	public final static int BLACK = 0;
	public final static int WHITE = 1;

	// Creates a Network chess piece with the given color, location and the Network gameboard that uses it.
	// Color is either 0 (black) or 0 (white). And the location, its x and y coordinates are assumed to be valid on a gameboard.
	public Chip(int color, int x, int y, Gameboard b){
		this.color = color;
		this.x = x;
		this.y = y;
		board = b;
	}
	
	// Creates a cloned version of a given Network chess piece on the given gameboard.
	// Helps copying chess pieces for a hash table implemented for the given gameboard.
	public Chip(Chip c, Gameboard b){
		color  = c.color;
		x = c.x;
		y = c.y;
		board = b;
	}

	// Provides a list of chess pieces that are in connection with this chess piece.
	public SList connections(){
		meet = new SList();
		for(int j = -1; j <= 1; j++){
			for(int i = -1; i <= 1; i++){
				if(i!=0 || j!=0){
					findConnections(i, j);
				}
			}
		}
		return meet;
	}

	// Helps constructing all the chess pieces that are connected to this chess piece by
	// searching in the direction specified by increX and increY.
	// increX implies horizontal direction (1 means right, -1 means left and 0 means no horizontal direction).
	// increY implies vertical direction (1 means up, -1 means down and 0 means no vertical direction).
	private void findConnections(int increX, int increY){
		int x = this.x + increX;
		int y = this.y + increY;
		while(board.isValidForBoard(x, y, this.color) && !board.hasChip(x, y)){
			x = x+increX;
			y = y+increY;
		}
		if(board.isValidForBoard(x, y, this.color)){
			Chip temp = board.getChip(x, y);
			if(temp.color == this.color){
				meet.insertFront(temp);
			}
		}
	}

	// Labels the chess piece visited in a search for a potential Network winning pattern.
	public void mark(){
		marked = true;
	}

	// Labels the chess piece not visited in a search for a potential Network winning pattern.
	public void unmark(){
		marked = false;
	}

	// Revelas whether the chess piece is currently considered to be visited or not.
	public boolean isMarked(){
		return marked;
	}

	// Provides the count of adjascent chess pieces within one square away from this chess piece.
	public int neighbours(){
		int count=0;
		int color = board.turn;
		for(int j = y-1; j < y+2; j++){
			for(int i = x-1; i < x+2; i++){
				if(board.isValidForBoard(i, j, color) && board.hasChip(i, j) && (board.getChip(i, j)).color == color){
					count++;
				}
			}
		}
		return count;
	}
	
	// Overrides Object's equals method to meet the standard of comparing two chess pieces only by their locations and color
	// if the given chess piece is a valid Chip object.
	public boolean equals(Object o){
		if(o instanceof Chip){
			Chip c = (Chip)o;
			return c.x == x && c.y == y && c.color == color;
		}else return false;
	}

	// Overrides Object's toString method for clarity when presenting the chess piece as a string.
	// A string of the chess piece's color, white (''W'') or black (''B''), and x and y coordinates is returned.
	public String toString(){
		if(color == WHITE){
			return "W"+x+""+y;
		}
		return "B"+x+""+y;
	}
}


