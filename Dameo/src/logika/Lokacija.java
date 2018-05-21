package logika;

public class Lokacija {
	private final int x;
	private final int y;
	
	public Lokacija(int i, int j){
		x = i;
		y = j;
	}
	
	public boolean equals(Lokacija lok){
		return (this.x == lok.x && this.y == lok.y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Lokacija [x=" + x + ", y=" + y + "]";
	}

	
	
}
