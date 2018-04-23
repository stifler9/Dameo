package Dameo;

import java.util.ArrayList;

public class Poteza {
	private ArrayList<Lokacija> sestavljena;
	
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
	
	public Lokacija get(int i){
		return sestavljena.get(i);
	}
	
	public int getX(int i){
		return sestavljena.get(i).x;
	}
	
	public int getY(int i){
		return sestavljena.get(i).y;
	}
	
	public void add(Lokacija lok){
		sestavljena.add(lok);
	}
	
	@SuppressWarnings("unchecked")
	public Poteza clone() {
		Poteza pot = new Poteza();
		pot.sestavljena = (ArrayList<Lokacija>) this.sestavljena.clone();
		return pot;
	}
}
