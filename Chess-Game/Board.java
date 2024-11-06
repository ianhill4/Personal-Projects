public class Board {
	private int size;
	private Piece[][] pieces;
	
	public Board(int s) {
		size = s;
		pieces = new Piece[size][size];
	}
	
	public Piece pieceAt(Coord p) {
		if(validCoord(p)) {
			return pieces[p.x][p.y];
		}
		
		return null;
	}
	
	public Piece pieceAt(int x, int y) {
		if(validCoord(new Coord(x,y))) {
			return pieces[x][y];
		}
		
		return null;
	}
	
	public int getSize() {
		return size;
	}
	
	public void addPiece(Piece piece, Coord pos) {
		pieces[pos.x][pos.y] = piece;
		piece.setBoard(this);
	}
	
	public void addPiece(Piece piece) {
		addPiece(piece, piece.getPos());
	}
	
	public void removePiece(Coord pos) {
		pieces[pos.x][pos.y] = null;
	}
	
	public boolean movePiece(Coord from, Coord to) {
		if(from.x < pieces.length && from.y < pieces.length && to.x < pieces.length && to.y < pieces.length) {
			if(pieces[from.x][from.y] != null && !from.equals(to) && validCoord(from) && validCoord(to)) {
				if(pieces[from.x][from.y].move(to)) {
					pieces[to.x][to.y] = pieceAt(from);
					removePiece(from);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean validCoord(Coord c) {
		if(c.x < size && c.x >= 0 && c.y < size && c.y >= 0) {
			return true;
		}
		
		return false;
	}
	
	public void print() {
		for(int y = size-1; y>=0; y--) {
			for(int x = 0; x<size; x++) {
				System.out.print(pieces[x][y]);
			}
			//after every row, print a new line
			System.out.println();
		}
	}
}
