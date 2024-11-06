import java.awt.Color;
import java.awt.Graphics;

public class Pawn implements Piece{
	protected Coord pos;
	private final int value = 1;
	private boolean hasMoved = false;
	private boolean white;
	private int direction;
	protected Board board;
	
	public Pawn(Coord p, boolean color) {
		pos = p;
		white = color;
		direction = (color)?(1):(-1);
	}
	
	public boolean move(Coord d) {
		if(validMove(d)) {
			hasMoved = true;
			pos = d;
			
			return true;
		}
		
		return false;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getType() {
		return "Pawn";
	}
	
	public void setBoard(Board b) {
		board = b;
	}
	
	public boolean isWhite() {
		return white;
	}
	
	public boolean validMove(Coord d) {
		if((white)?(d.y-pos.y <= 2 && d.y-pos.y > 0):(d.y-pos.y >= -2 && d.y-pos.y < 0)) {
			switch(Math.abs(d.y-pos.y)) {
				case 1:
					if((Math.abs(d.x - pos.x) == 1 && board.pieceAt(d) != null && board.pieceAt(d).isWhite() != white) || (d.x - pos.x == 0 && board.pieceAt(d) == null)) {
						return true;
					}
					break;
				case 2:
					if(checkPath() && !hasMoved && d.x - pos.x == 0 && board.pieceAt(d) == null) {
						return true;
						}
					break;
			}
		}
		
		return false;
	}
	
	public boolean checkPath() {
		if(board.pieceAt(pos.x,pos.y+direction) == null && board.pieceAt(pos.x,pos.y+(2*direction)) == null) {
			return true;
		}
		
		return false;
	}
	
	public Coord getPos() {
		return pos;
	}
	
	public String toString() {
		return String.format("Pawn at "+pos, pos);
	}

	@Override
	public void draw(Graphics g) {
		
	}
}
