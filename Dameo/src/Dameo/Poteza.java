package Dameo;

import java.util.ArrayList;

public class Poteza {
	public ArrayList<Lokacija> sestavljena;
	
	public Poteza(){
		sestavljena = new ArrayList<Lokacija>();
	}
	
	public int size(){
		return sestavljena.size();
	}
	
}
