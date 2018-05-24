package logika;

import java.util.LinkedList;

public class Igra {
	public Stanje stanje;
	public IgralecIgre napotezi;
	public Lokacija nujnost;
	
	public Igra(){
		stanje = new Stanje();
		napotezi = IgralecIgre.BELI;
		nujnost = null;
	}
	
	/*
	 * Igro bom vedno kopiral, ko bo nujnost = null.
	 */
	public Igra(Igra igra) {
		stanje = new Stanje(igra.stanje);
		this.napotezi = igra.napotezi;
		this.nujnost = null;
	}
	
	/*
	 * v igrah v undoIgre so samo igre, ki imajo nujnost = null,
	 * torej true vrne, èe igra(dameo).nujnost == null 
	 */
	public boolean equals(Igra igra){
		if(!igra.stanje.equals(this.stanje)){
			return false;
		}
		if(igra.napotezi != this.napotezi){
			return false;
		}
		if(igra.nujnost != null){
			return false;
		}
		return true;
	}
	
	/*
	 * Figuro na mestu lok1 premaknemo na lok2
	 * (Igramo 1 SKOK naenkrat)
	 * (Ce ima igralec na voljo vec skokov, ostane na potezi)
	 */
	public boolean odigraj(Lokacija lok1, Lokacija lok2){
		boolean ali = false;
		boolean enostavnost = false;
		int maks = 0;
		for(Poteza poteza: generirajPoteze()){
			if(poteza.naPrvemMestu(lok1) && poteza.naDrugemMestu(lok2)){
				ali = true;
				enostavnost = poteza.enostavnost;
				maks = poteza.size();
				break;
			}
		}
		
		if(ali){
			if(enostavnost){
				/*
				 * naredimo enostavno potezo (1 skok, kjer ne jemo).
				 */
				stanje.narediEnostavno(lok1.getX(), lok1.getY(), lok2.getX(), lok2.getY());
				if(napotezi == IgralecIgre.BELI){napotezi = IgralecIgre.CRNI;}else{napotezi=IgralecIgre.BELI;}
			}else{
				/*
				 * Ce je kralj jedel, moramo pojesti tistega, ki je eno polje pred lok2.
				 */

				stanje.narediSkok(lok1.getX(), lok1.getY(), lok2.getX(), lok2.getY());

				if(maks > 2){
					nujnost = lok2;
				}else{
					nujnost = null;
					if(napotezi == IgralecIgre.BELI){napotezi = IgralecIgre.CRNI;}else{napotezi=IgralecIgre.BELI;}
				}
			}
			/*
			 * Ce kdo pride na zadnje polje, se spremeni v kralja:
			 */
			stanje.mozjeVKralje();
			
			/*
			 * Spremenili smo stanje, kdo je na potezi in nujnost,
			 * treba je pogledati ce se lahko igra
			 */
			
			if(generirajPoteze().isEmpty()){
				if(napotezi == IgralecIgre.BELI){
					napotezi = IgralecIgre.ZMAGACRNI;
				}else{
					napotezi = IgralecIgre.ZMAGABELI;
				}
			}
		}
		return ali;
	}
	
	/*
	 * Pomozna metoda za misleca (uporabljam na kopiji igre):
	 * 
	 * Poteza je že veljavna,
	 * nujnost bo ves èas null
	 * na koncu zamenjamo IgralecIgre naPotezi.
	 */
	public void odigrajPotezo(Poteza pot) {
		if(pot.enostavnost) {
			stanje.narediEnostavno(pot.getX(0), pot.getY(0), pot.getX(1), pot.getY(1));
		}else {
			for(int i = 1; i<pot.size(); i++) {
				stanje.narediSkok(pot.getX(i-1), pot.getY(i-1), pot.getX(i), pot.getY(i));
			}
		}
		if(napotezi == IgralecIgre.BELI){napotezi = IgralecIgre.CRNI;}else{napotezi=IgralecIgre.BELI;}
		
		if(generirajPoteze().isEmpty()){
			if(napotezi == IgralecIgre.BELI){
				napotezi = IgralecIgre.ZMAGACRNI;
			}else{
				napotezi = IgralecIgre.ZMAGABELI;
			}
		}
	}
	/*
	 * Poteze kjer lahko figura je'.
	 */
	public LinkedList<Poteza> generirajPoteze(){
		LinkedList<Poteza> f = new LinkedList<Poteza>();
		int maks = 2;
		if (nujnost == null) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (napotezi == IgralecIgre.BELI) {
						if (stanje.get(i, j) == Polje.BelMoz) {
							Lokacija lok = new Lokacija(i, j);
							Poteza pot = new Poteza();
							pot.add(lok);
							LinkedList<Poteza> gen = generirajPoteze_belmoz(pot);
							for (Poteza poteza : gen) {
								int k = poteza.size();
								if (k == maks) {
									f.add(poteza);
								} else if (k > maks) {
									f.clear();
									f.add(poteza);
									maks = k;
								}
							}
						} else if (stanje.get(i, j) == Polje.BelKralj) {
							Lokacija lok = new Lokacija(i, j);
							Poteza pot = new Poteza();
							pot.add(lok);
							LinkedList<Poteza> gen = generirajPoteze_belkralj(pot);
							for (Poteza poteza : gen) {
								int k = poteza.size();
								if (k == maks) {
									f.add(poteza);
								} else if (k > maks) {
									f.clear();
									f.add(poteza);
									maks = k;
								}
							}
						}
					} else if (napotezi == IgralecIgre.CRNI) {
						if (stanje.get(i, j) == Polje.CrniMoz) {
							Lokacija lok = new Lokacija(i, j);
							Poteza pot = new Poteza();
							pot.add(lok);
							LinkedList<Poteza> gen = generirajPoteze_crnimoz(pot);
							for (Poteza poteza : gen) {
								int k = poteza.size();
								if (k == maks) {
									f.add(poteza);
								} else if (k > maks) {
									f.clear();
									f.add(poteza);
									maks = k;
								}
							}
						} else if (stanje.get(i, j) == Polje.CrniKralj) {
							Lokacija lok = new Lokacija(i, j);
							Poteza pot = new Poteza();
							pot.add(lok);
							LinkedList<Poteza> gen = generirajPoteze_crnikralj(pot);
							for (Poteza poteza : gen) {
								int k = poteza.size();
								if (k == maks) {
									f.add(poteza);
								} else if (k > maks) {
									f.clear();
									f.add(poteza);
									maks = k;
								}
							}
						}
					}
				}
			}
			if (f.isEmpty()) {
				return generirajEnostavne();
			} else {
				return f;
			} 
		}else {
			if(stanje.get(nujnost.getX(), nujnost.getY())==Polje.BelMoz) {
				Poteza pot = new Poteza();
				pot.add(nujnost);
				LinkedList<Poteza> gen = generirajPoteze_belmoz(pot);
				for(Poteza poteza: gen) {
					int k = poteza.size();
					if (k == maks) {
						f.add(poteza);
					} else if (k > maks) {
						f.clear();
						f.add(poteza);
						maks = k;
					}
				}
			}
			if(stanje.get(nujnost.getX(), nujnost.getY())==Polje.CrniMoz) {
				Poteza pot = new Poteza();
				pot.add(nujnost);
				LinkedList<Poteza> gen = generirajPoteze_crnimoz(pot);
				for(Poteza poteza: gen) {
					int k = poteza.size();
					if (k == maks) {
						f.add(poteza);
					} else if (k > maks) {
						f.clear();
						f.add(poteza);
						maks = k;
					}
				}
			}
			if(stanje.get(nujnost.getX(), nujnost.getY())==Polje.BelKralj) {
				Poteza pot = new Poteza();
				pot.add(nujnost);
				LinkedList<Poteza> gen = generirajPoteze_belkralj(pot);
				for(Poteza poteza: gen) {
					int k = poteza.size();
					if (k == maks) {
						f.add(poteza);
					} else if (k > maks) {
						f.clear();
						f.add(poteza);
						maks = k;
					}
				}
			}
			if(stanje.get(nujnost.getX(), nujnost.getY())==Polje.CrniKralj) {
				Poteza pot = new Poteza();
				pot.add(nujnost);
				LinkedList<Poteza> gen = generirajPoteze_crnikralj(pot);
				for(Poteza poteza: gen) {
					int k = poteza.size();
					if (k == maks) {
						f.add(poteza);
					} else if (k > maks) {
						f.clear();
						f.add(poteza);
						maks = k;
					}
				}
			}
			return f;
			
		}
		
	}
	/*
	 * Poteze kjer fugura ne je'.
	 */
	private LinkedList<Poteza> generirajEnostavne(){
		LinkedList<Poteza> f = new LinkedList<Poteza>();
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				if(napotezi == IgralecIgre.BELI){
					if(stanje.get(i, j) == Polje.BelMoz){
						Lokacija lok = new Lokacija(i, j);
						LinkedList<Poteza> gen = generirajEnostavne_belmoz(lok);
						for(Poteza poteza: gen){
							f.add(poteza);
						}
					}else if(stanje.get(i, j) == Polje.BelKralj){
						Lokacija lok = new Lokacija(i, j);
						LinkedList<Poteza> gen = generirajEnostavne_kralj(lok);
						for(Poteza poteza: gen){
							f.add(poteza);
						}
					}
				}else if(napotezi == IgralecIgre.CRNI){
					if(stanje.get(i, j) == Polje.CrniMoz){
						Lokacija lok = new Lokacija(i, j);
						LinkedList<Poteza> gen = generirajEnostavne_crnimoz(lok);
						for(Poteza poteza: gen){
							f.add(poteza);
						}
					}else if(stanje.get(i, j) == Polje.CrniKralj){
						Lokacija lok = new Lokacija(i, j);
						LinkedList<Poteza> gen = generirajEnostavne_kralj(lok);
						for(Poteza poteza: gen){
							f.add(poteza);
						}
					}
				}
			}
		}
		return f;
	}
	/*
	 * Rekurzivno izracunamo poti po katerih lahko bel moz je'.
	 */
	private LinkedList<Poteza> generirajPoteze_belmoz(Poteza pot){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0}};
		int x = pot.getX(pot.size()-1);
		int y = pot.getY(pot.size()-1);
		boolean lahkoje = false;
		for(int[] xy: smeri){
			
			// Ce pade ven:
			if(0 <= y + 2*xy[1] && y + 2*xy[1] <= 7 && 0 <= x + 2*xy[0] && x + 2*xy[0] <= 7){
				if(stanje.get(x + xy[0],y + xy[1]) == Polje.CrniMoz || stanje.get(x + xy[0],y + xy[1]) == Polje.CrniKralj){
					if(stanje.get(x + 2*xy[0],y + 2*xy[1]) == Polje.Prazno){
						lahkoje = true;
						// Ce beli moz pride na koncno polje se tam poteza zakljuci, spremeni se v kralja:
						if(y + 2*xy[1] == 0){
							Poteza nova = new Poteza();
							nova = pot.clone();
							Lokacija novalok = new Lokacija(x + 2*xy[0], y + 2*xy[1]);
							nova.add(novalok);
							set.add(nova);
						}else{
							//Naredimo potezo na matriki in si zapomnemo koga smo pojedli:
							Polje pojeden = stanje.narediSkok(x, y, x + 2*xy[0], y + 2*xy[1]);
							
							Poteza nova = new Poteza();
							nova = pot.clone();
							Lokacija novalok = new Lokacija(x + 2*xy[0], y + 2*xy[1]);
							nova.add(novalok);
							
							//Rekurzivni klic:
							LinkedList<Poteza> novset = generirajPoteze_belmoz(nova);
							for(Poteza poteza: novset){
								set.add(poteza);
							}
							
							//razveljavimo:
							stanje.razveljaviSkok(x, y, x + 2*xy[0], y + 2*xy[1], pojeden);
						}
					}
				}
			}
		}
		if(lahkoje){
			return set;
		}else{
			set.add(pot);
			return set;
		}
	}
	/*
	 * Rekurzivno izracunamo poti po katerih lahko crni moz je'.
	 */
	private LinkedList<Poteza> generirajPoteze_crnimoz(Poteza pot){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,1},{0,1},{1, 1},{1, 0}};
		int x = pot.getX(pot.size()-1);
		int y = pot.getY(pot.size()-1);
		boolean lahkoje = false;
		for(int[] xy: smeri){
			// Ce pade ven:
			if(0 <= y + 2*xy[1] && y + 2*xy[1] <= 7 && 0 <= x + 2*xy[0] && x + 2*xy[0] <= 7){
				if(stanje.get(x + xy[0],y + xy[1]) == Polje.BelMoz || stanje.get(x + xy[0],y + xy[1]) == Polje.BelKralj){
					if(stanje.get(x + 2*xy[0],y + 2*xy[1]) == Polje.Prazno){
						lahkoje = true;
						// Ce crni moz pride na koncno polje se tam poteza zakljuci, spremeni se v kralja:
						if(y + 2*xy[1] == 7){
							Poteza nova = new Poteza();
							nova = pot.clone();
							Lokacija novalok = new Lokacija(x + 2*xy[0], y + 2*xy[1]);
							nova.add(novalok);
							set.add(nova);
						}else{
							//Naredimo potezo na matriki in si zapomnemo koga smo pojedli:
							Polje pojeden = stanje.narediSkok(x, y, x + 2*xy[0], y + 2*xy[1]);
							
							Poteza nova = new Poteza();
							nova = pot.clone();
							Lokacija novalok = new Lokacija(x + 2*xy[0], y + 2*xy[1]);
							nova.add(novalok);
							
							//Rekurzivni klic
							LinkedList<Poteza> novset = generirajPoteze_crnimoz(nova);
							for(Poteza poteza: novset){
								set.add(poteza);
							}
							//razveljavimo:
							stanje.razveljaviSkok(x, y, x + 2*xy[0], y + 2*xy[1], pojeden);
						}
					}
				}
			}
		}
		if(lahkoje){
			return set;
		}else{
			set.add(pot);
			return set;
		}
	}
	/*
	 * Rekurzivno izracunamo poti po katerih lahko bel kralj je'.
	 */
	private LinkedList<Poteza> generirajPoteze_belkralj(Poteza pot){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0},{1,1},{0,1},{-1,1}};
		int x = pot.getX(pot.size()-1);
		int y = pot.getY(pot.size()-1);
		boolean lahkoje = false;
		for(int[] xy: smeri){
			boolean stikalo = true;
			int k = 2;
			while(stikalo){
				// Ce pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if((stanje.get(x + (k-1)*xy[0],y + (k-1)*xy[1]) == Polje.CrniMoz || stanje.get(x + (k-1)*xy[0],y + (k-1)*xy[1]) == Polje.CrniKralj)
					&& (stanje.get(x + k*xy[0],y + k*xy[1]) == Polje.Prazno)) {
							lahkoje = true;
							
							//Naredimo potezo na matriki in si zapomnemo koga smo pojedli:
							Polje pojeden = stanje.narediSkok(x, y, x + k*xy[0], y + k*xy[1]);

							Poteza nova = new Poteza();
							nova = pot.clone();
							Lokacija novalok = new Lokacija(x + k * xy[0], y + k * xy[1]);
							nova.add(novalok);
							
							
							LinkedList<Poteza> novset = generirajPoteze_belkralj(nova);
							for(Poteza poteza: novset){
								set.add(poteza);
							}
							//razveljavimo:
							stanje.razveljaviSkok(x, y, x + k*xy[0], y + k*xy[1], pojeden);
							stikalo = false;
					}else if(!(stanje.get(x + (k-1)*xy[0],y + (k-1)*xy[1]) == Polje.Prazno)) {stikalo = false;}
					k++;
				}else{stikalo = false;}
			}
		}
		if(lahkoje){
			return set;
		}else{
			set.add(pot);
			return set;
		}
	}
	/*
	 * Rekurzivno izracunamo poti po katerih lahko crni kralj je'.
	 */
	private LinkedList<Poteza> generirajPoteze_crnikralj(Poteza pot){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0},{1,1},{0,1},{-1,1}};
		int x = pot.getX(pot.size()-1);
		int y = pot.getY(pot.size()-1);
		boolean lahkoje = false;
		for(int[] xy: smeri){
			boolean stikalo = true;
			int k = 2;
			while(stikalo){
				
				// Ce pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if((stanje.get(x + (k-1)*xy[0],y + (k-1)*xy[1]) == Polje.BelMoz || stanje.get(x + (k-1)*xy[0],y + (k-1)*xy[1]) == Polje.BelKralj)
					&& (stanje.get(x + k*xy[0],y + k*xy[1]) == Polje.Prazno)){
							lahkoje = true;
							
							//Naredimo potezo na matriki in si zapomnemo koga smo pojedli:
							Polje pojeden = stanje.narediSkok(x, y, x + k*xy[0], y + k*xy[1]);
							
							Poteza nova = new Poteza();
							nova = pot.clone();
							Lokacija novalok = new Lokacija(x + k*xy[0], y + k*xy[1]);
							nova.add(novalok);
							
							
							LinkedList<Poteza> novset = generirajPoteze_crnikralj(nova);
							for(Poteza poteza: novset){
								set.add(poteza);
							}
							//razveljavimo:
							stanje.razveljaviSkok(x, y, x + k*xy[0], y + k*xy[1], pojeden);
							stikalo = false;
					}else if(!(stanje.get(x + (k-1)*xy[0],y + (k-1)*xy[1]) == Polje.Prazno)) {stikalo = false;}
					k++;
				}else{stikalo = false;}
			}
		}
		if(lahkoje){
			return set;
		}else{
			set.add(pot);
			return set;
		}
	}
	
	private LinkedList<Poteza> generirajEnostavne_belmoz(Lokacija lok){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0}};
		int x = lok.getX();
		int y = lok.getY();
		
		for(int[] xy: smeri){
			int k = 1;
			boolean stikalo = true;
			while(stikalo){
				// Ce pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if(stanje.get(x + k*xy[0],y + k*xy[1]) == Polje.Prazno){
						Poteza nova = new Poteza(true);
						nova.add(lok);
						Lokacija novalok = new Lokacija(x + k*xy[0], y + k*xy[1]);
						nova.add(novalok);
						set.add(nova);
						stikalo = false;
					}else if(stanje.get(x + k*xy[0],y + k*xy[1]) != Polje.BelMoz){stikalo = false;}
				}else{stikalo = false;}
				k++;
			}	
		}
		return set;
	}
	
	private LinkedList<Poteza> generirajEnostavne_crnimoz(Lokacija lok){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,1},{0,1},{1, 1},{1, 0}};
		int x = lok.getX();
		int y = lok.getY();
		
		for(int[] xy: smeri){
			int k = 1;
			boolean stikalo = true;
			while(stikalo){
				// Ce pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if(stanje.get(x + k*xy[0],y + k*xy[1]) == Polje.Prazno){
						Poteza nova = new Poteza(true);
						nova.add(lok);
						Lokacija novalok = new Lokacija(x + k*xy[0], y + k*xy[1]);
						nova.add(novalok);
						set.add(nova);
						stikalo = false;
					}else if(stanje.get(x + k*xy[0],y + k*xy[1]) != Polje.CrniMoz){stikalo = false;}
				}else{stikalo = false;}
				k++;
			}
		}
		return set;
	}
	
	private LinkedList<Poteza> generirajEnostavne_kralj(Lokacija lok){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0},{-1,1},{0,1},{1, 1}};
		int x = lok.getX();
		int y = lok.getY();
		
		for(int[] xy: smeri){
			int k = 1;
			boolean stikalo = true;
			while(stikalo){
				// Ce pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if(stanje.get(x + k*xy[0],y + k*xy[1]) == Polje.Prazno){
						Poteza nova = new Poteza(true);
						nova.add(lok);
						Lokacija novalok = new Lokacija(x + k*xy[0], y + k*xy[1]);
						nova.add(novalok);
						set.add(nova);
					}else{stikalo = false;}
				}else{stikalo = false;}
				k++;
			}
		}
		return set;
	}
}
