import java.awt.Graphics;

public class King implements Piece{
	protected Coord pos;
	private final int value = 1;
	private boolean hasMoved = false;
	private boolean white;
	protected Board board;
	
	public King(Coord p, boolean color) {
		pos = p;
		white = color;
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
		return "King";
	}
	
	public void setBoard(Board b) {
		board = b;
	}
	
	public boolean isWhite() {
		return white;
	}
	
	public boolean validMove(Coord d) {
		//need to make exclusive or XOR
		if((((Math.abs(d.x - pos.x) == 0) && (Math.abs(d.y - pos.y) == 1)) || (Math.abs(d.x - pos.x) == 1) && (d.y - pos.y == 0)) || (Math.abs(d.x - pos.x) == 1) && ((Math.abs(d.y - pos.y) == 1))) {
			if((board.pieceAt(d) == null || (board.pieceAt(d).isWhite() != white))) {
				return true;
			}
		}
		
		return false;
	}
	
	public Coord getPos() {
		return pos;
	}
	
	public String toString() {
		return String.format("King at "+pos, pos);
	}

	@Override
	public void draw(Graphics g) {
		
	}
}
