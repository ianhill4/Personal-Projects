// import required graphic libraries
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.util.Scanner;

public class Chess extends JFrame{
	//create our canvas and keyboard listening objects
	private Canvas canvas;

  //define the dimensions of the window
	public static final int SCREENWIDTH = 740+28;
	public static final int SCREENHEIGHT = 640;

  //create the mouse input listener
	private ClickListener cL;

  //create a new board object with 8 rows and columns
	private static Board b = new Board(8);

  //constructor for the Chess object
	public Chess() {
    //name the window Chess
		super("Chess");

    //make the window visable, focusable, and quit the program upon closing the window
		setVisible(true);
		setFocusable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //set the size of the window
		setSize(SCREENWIDTH,SCREENHEIGHT);

    //create the canvas with the same dimensions
		canvas = new Canvas(b,SCREENWIDTH,SCREENHEIGHT);

    //instantiate the mouse input object
		cL = new ClickListener(canvas,b);

    //add the mouse input object to the canvas
		canvas.addMouseListener(cL);

    //add the canvas to the Chess object
		add(canvas);

    //create the pieces on the board

    //pawns
		for(int i = 0; i<b.getSize(); i++) {
			b.addPiece(new Pawn(new Coord(i,1),true));
			b.addPiece(new Pawn(new Coord(i,6),false));
		}

    //rooks
		b.addPiece(new Rook(new Coord(0,0),true));
		b.addPiece(new Rook(new Coord(7,0),true));
		b.addPiece(new Rook(new Coord(0,7),false));
		b.addPiece(new Rook(new Coord(7,7),false));		

    //bishops
		b.addPiece(new Bishop(new Coord(2,0),true));
		b.addPiece(new Bishop(new Coord(5,0),true));
		b.addPiece(new Bishop(new Coord(2,7),false));
		b.addPiece(new Bishop(new Coord(5,7),false));

    //knights
		b.addPiece(new Knight(new Coord(1,0),true));
		b.addPiece(new Knight(new Coord(6,0),true));
		b.addPiece(new Knight(new Coord(1,7),false));
		b.addPiece(new Knight(new Coord(6,7),false));

    //queens and kings
		b.addPiece(new Queen(new Coord(4,0),true));
		b.addPiece(new Queen(new Coord(4,7),false));
		b.addPiece(new King(new Coord(3,0),true));
		b.addPiece(new King(new Coord(3,7),false));
	}
	
	public static void main(String[] args) {
    //create a new chess game
		Chess c = new Chess();
	}
}

//class to handle the mouse input
class ClickListener implements MouseListener{
	private Canvas canvas;
	public Coord point1, point2;
	public Board board;

  //variables to keep track of whose turn it is and the number of mouse presses
	int timesClicked = 0;
	int turn = 0;

  //constructor for the class
	public ClickListener(Canvas c, Board b) {
		this.canvas = c;
		this.board = b;
	}

  //handle the mouse being pressed
	public void mousePressed(MouseEvent e) {
    //if the user haven't clicked the mouse yet,
		if(timesClicked == 0) {
      //record the coordinates of the mouse press
			point1 = coordToSquare(e.getX(),e.getY());

      //if there exists a piece at the square the user has pressed
			if(board.pieceAt(point1) != null) {
        //if it is whites turn
				if(turn % 2 == 0) {
          //if the piece where the user has clicked, and it is white, highlight it and increase the number of times clicked
					if(board.pieceAt(point1).isWhite()) {
						timesClicked++;
						canvas.highlightSquare(point1);
						canvas.repaint();
					}
        //if it is blacks turn
				} else {
          //if the piece where the user has clicked, and it is black, highlight it and increase the number of times clicked
					if(!board.pieceAt(point1).isWhite()) {
						timesClicked++;
						canvas.highlightSquare(point1);
						canvas.repaint();
					}
				}
      //if the user has not clicked on a square with a piece, deselect the currently selected square
			} else {
				canvas.highlightSquare(new Coord(-1,-1));
				canvas.repaint();
			}
		} else {
      //record the coordinates for the second mouse click
			point2 = coordToSquare(e.getX(),e.getY());

      //if the user has not clicked on the same square
			if(!point2.equals(point1)) {
        //attempt to move the piece to that square
				if(board.movePiece(point1, point2)) {
          //if the user is able to move the piece, change the turn to the next player
					turn++;
				}
        //otherwise, if the move was invalid, deselect the square and reset the number of times clicked, allowing the player to make another move
				canvas.highlightSquare(new Coord(-1,-1));
				canvas.repaint();
				timesClicked = 0;
			}
		}
	}

  //since MouseListener is an interface, we need to implement these methods, but we have no use for them so they are left empty
  public void mouseClicked(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	public Coord coordToSquare(int pointX, int pointY) {
    //calculate the corresponding grid coordinate given a mouse location
		int squareX = ((pointX - ((Chess.SCREENHEIGHT)/(40)))*((board.getSize()*10)))/(9*Chess.SCREENHEIGHT);
		int squareY = 7-((pointY - ((Chess.SCREENHEIGHT)/(40)))*((board.getSize()*10)))/(9*Chess.SCREENHEIGHT);

    //return the grid coordinate corresponding to the mouse location
		return new Coord(squareX,squareY);
	}
}
