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
	
	public boolean naDrugemMestu(Lokacija lok) {
		return sestavljena.get(1).equals(lok);
	}
	
	public boolean naPrvemMestu(Lokacija lok) {
		return sestavljena.get(0).equals(lok);
	}
	
	@SuppressWarnings("unchecked")
	public Poteza clone() {
		Poteza pot = new Poteza();
		pot.sestavljena = (ArrayList<Lokacija>) this.sestavljena.clone();
		return pot;
	}
}
