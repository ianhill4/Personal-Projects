import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class Canvas extends JPanel{
	
	//create variable to store current drawing color and the board
	private Color color;
	private Board b;
	private int sWidth, sHeight, size;
	
	//constructor to initialize these variables
	public Canvas(Board board, int sW, int sH) {
		setBackground(Color.BLACK);
		color = Color.BLACK;
		b = board;
		sWidth = sW;
		sHeight = sH;
		size = b.getSize();

	}
	
	//overridden painting method that creates the board and paints to it
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		paintBoard(g);
		
		paintPieces(g);
	}
	
	Coord selectedSquare = new Coord(-1,-1);
	
	public void paintBoard(Graphics g) {
		for(int y = 0; y<size+1; y++) {
			for(int x = 0; x<size+1; x++) {
				g.setColor(((y+x) % 2 == 0)?(Color.LIGHT_GRAY):(Color.GRAY));
				if(x>=size || y>=size) {
					g.setColor(Color.BLACK);
				}
				g.fillRect(((9*sHeight*x)/(size*10)) + ((sHeight)/(40)),((9*sHeight*y)/(size*10)) + ((sHeight)/(40)),sHeight/size,sHeight/size);
				if(selectedSquare.x == x && selectedSquare.y == (size-y-1)) {
					g.setColor(Color.RED);
					g.drawRect(4+((9*sHeight*x)/(size*10)) + ((sHeight)/(40)),4+((9*sHeight*y)/(size*10)) + ((sHeight)/(40)),(sHeight/size)-16,(sHeight/size)-16);
				}
			}
		}
	}
	
	public void highlightSquare(Coord c) {
		selectedSquare = c;
	}
	
	private Coord l;
	private Piece p;
	
	int[] xPoints, yPoints;
	
	public void paintPieces(Graphics g) {
		for(int y = size; y>=0; y--) {
			for(int x = 0; x<size; x++) {
				p = b.pieceAt(x,y);
				if(p != null) {
					l = getPosition(x,y);
					g.setColor((p.isWhite())?(Color.white):(Color.black));
					switch(p.getType()) {
						case "Pawn":
							g.fillOval(l.x + (9*sHeight)/(size*40), l.y + (9*sHeight)/(size*40), (9*sHeight)/(size*20), (9*sHeight)/(size*20));
							g.setColor((!p.isWhite())?(Color.white):(Color.black));
							g.drawString("pawn", l.x + (9*sHeight)/(size*40), l.y + (9*sHeight)/(size*20)+4);
							break;
						case "Rook":
							g.fillRect(l.x + (9*sHeight)/(size*40), l.y + (9*sHeight)/(size*40), (9*sHeight)/(size*20), (9*sHeight)/(size*20));
							g.setColor((!p.isWhite())?(Color.white):(Color.black));
							g.drawString("rook", l.x + (9*sHeight)/(size*40), l.y + (9*sHeight)/(size*20)+4);
							break;
						case "Bishop":
							g.fillRoundRect(l.x + (9*sHeight)/(size*40), l.y + (9*sHeight)/(size*40), (9*sHeight)/(size*20), (9*sHeight)/(size*20),(9*sHeight)/(size*80), (9*sHeight)/(size*80));
							g.setColor((!p.isWhite())?(Color.white):(Color.black));
							g.drawString("bishop", l.x + (9*sHeight)/(size*40), l.y + (9*sHeight)/(size*20)+4);
							break;
						case "Knight":
							xPoints = new int[] {(l.x + (9*sHeight)/(size*40)) - (9*sHeight)/(size*160),l.x + (9*sHeight)/(size*40),(l.x + (9*sHeight)/(size*40)) + (9*sHeight)/(size*160)};
							yPoints = new int[] {(l.y + (9*sHeight)/(size*40)) - (9*sHeight)/(size*160),(l.y + (9*sHeight)/(size*40)) + (9*sHeight)/(size*160),(l.y + (9*sHeight)/(size*40))-(9*sHeight)/(size*160)};
							
							g.fillPolygon(xPoints,yPoints,3);
							g.setColor((!p.isWhite())?(Color.white):(Color.black));
							g.drawString("knight", l.x + (9*sHeight)/(size*40), l.y + (9*sHeight)/(size*20)+4);
							break;
						case "Queen":
							g.setColor((!p.isWhite())?(Color.white):(Color.black));
							g.drawString("queen", l.x + (9*sHeight)/(size*40), l.y + (9*sHeight)/(size*20)+4);
							break;
						case "King":
							g.setColor((!p.isWhite())?(Color.white):(Color.black));
							g.drawString("King", l.x + (9*sHeight)/(size*40), l.y + (9*sHeight)/(size*20)+4);;
							break;
					}
				}
			}
		}
	}
	
	public Coord getPosition(int x, int y) {
		return new Coord(((9*sHeight*x)/(size*10)) + ((sHeight)/(40)),(((9*sHeight*(size-y-1))/(size*10)) + ((sHeight)/(40))));
	}
}
