import java.awt.Graphics;

public class Bishop implements Piece{
	protected Coord pos;
	private final int value = 1;
	private boolean hasMoved = false;
	private boolean white;
	protected Board board;
	
	public Bishop(Coord p, boolean color) {
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
		return "Bishop";
	}
	
	public void setBoard(Board b) {
		board = b;
	}
	
	public boolean isWhite() {
		return white;
	}
	
	public boolean validMove(Coord d) {
		if(Math.abs(d.x - pos.x) == Math.abs(d.y - pos.y)) {
			if(checkPath(d) && (board.pieceAt(d) == null || (board.pieceAt(d).isWhite() != white))) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkPath(Coord d) {
		Coord check = pos.copy();
		int dX = d.x-check.x;
		int dY = d.y-check.y;
				
		for(int i = 0; i < Math.abs(dY)-1; i++) {
			check.add(dX/Math.abs(dX), dY/Math.abs(dY));
			if(board.pieceAt(check) != null) {
				return false;
			}
		}
		
		return true;
	}
	
	public Coord getPos() {
		return pos;
	}
	
	public String toString() {
		return String.format("Bishop at "+pos, pos);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
