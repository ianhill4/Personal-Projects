import java.awt.Graphics;

public class Queen implements Piece{
	protected Coord pos;
	private final int value = 1;
	private boolean hasMoved = false;
	private boolean white;
	protected Board board;
	
	public Queen(Coord p, boolean color) {
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
		return "Queen";
	}
	
	public void setBoard(Board b) {
		board = b;
	}
	
	public boolean isWhite() {
		return white;
	}
	
	public boolean validMove(Coord d) {
		//need to make exclusive or XOR
		if((((d.x - pos.x == 0) && (d.y - pos.y != 0)) || ((d.x - pos.x != 0) && (d.y - pos.y == 0))) || (Math.abs(d.x - pos.x) == Math.abs(d.y - pos.y))) {
			if(checkPath(d) && (board.pieceAt(d) == null || (board.pieceAt(d).isWhite() != white))) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkPath(Coord to) {
		Coord check = pos.copy();
		int dX = to.x-pos.x;
		int dY = to.y-pos.y;
				
		if(dX == 0) {
			for(int i = 0; i < Math.abs(dY)-1; i++) {
				check.add(0, dY/Math.abs(dY));
				if(board.pieceAt(check) != null) {
					return false;
				}
			}
		} else if(dY == 0) {
			for(int i = 0; i < Math.abs(dX)-1; i++) {
				check.add(dX/Math.abs(dX),0);
				if(board.pieceAt(check) != null) {
					return false;
				}
			}
		} else {
			for(int i = 0; i < Math.abs(dY)-1; i++) {
				check.add(dX/Math.abs(dX), dY/Math.abs(dY));
				if(board.pieceAt(check) != null) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Coord getPos() {
		return pos;
	}
	
	public String toString() {
		return String.format("Queen at "+pos, pos);
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
