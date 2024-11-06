import java.awt.Graphics;

public interface Piece {
	public int getValue();
	
	public Coord getPos();
	
	public boolean move(Coord d);
	
	public boolean validMove(Coord d);
	
	public String toString();
	
	public void setBoard(Board b);
	
	public boolean isWhite();
	
	public String getType();
	
	public void draw(Graphics g);
}
