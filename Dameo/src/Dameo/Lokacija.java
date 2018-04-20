package Dameo;

public class Lokacija {
	public int x;
	public int y;
	
	public Lokacija(int i, int j){
		x = i;
		y = j;
	}
	
	public void set(int i, int j){
		x = i;
		y = j;
	}
	
	public boolean equals(Lokacija lok){
		return (this.x == lok.x && this.y == lok.y);
	}
}
