public class Coord {
	protected int x;
	protected int y;
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Coord add(Coord c) {
		this.x += c.x;
		this.y += c.y;
		
		return this;
	}
	
	public Coord add(int x, int y) {
		this.x += x;
		this.y += y;
		
		return this;
	}
	
	public Coord sub(Coord c) {
		this.x -= c.x;
		this.y -= c.y;
		
		return this;
	}
	
	public int max() {
		return (y>x)?(y):(x);
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Coord c) {
		if(this.x == c.x && this.y == c.y) {
			return true;
		}
		
		return false;
	}
	
	public Coord copy() {
		return new Coord(this.x,this.y);
	}
	
	public String toString() {
		return String.format("<%d, %d>", x,y);
	}
}
