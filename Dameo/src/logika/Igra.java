package logika;

import java.util.LinkedList;

public class Igra {
	public Stanje stanje;
	// Probaj dati ven
	public LinkedList<Poteza> moznePoteze;
	public Igralec napotezi;
	// Probaj dati ven
	private int maks;
	public Lokacija nujnost;
	
	public Igra(){
		stanje = new Stanje();
		moznePoteze = new LinkedList<Poteza>();
		maks = 0;
		napotezi = Igralec.BELI;
		nujnost = null;
		moznePoteze = generirajPoteze();
	}
	
	private static int abs(int x){
		if(x>=0){
			return x;
		}else{return -x;}
	}
	/*
	 * Figuro na mestu lok1 premaknemo na lok2
	 * (Igramo 1 SKOK naenkrat)
	 * (Ce ima igralec na voljo vec skokov, ostane na potezi)
	 */
	public boolean odigraj(Lokacija lok1, Lokacija lok2){
		boolean ali = false;
		for(Poteza poteza: moznePoteze){
			if(poteza.naPrvemMestu(lok1) && poteza.naDrugemMestu(lok2)){
				ali = true;
				break;
			}
		}
		
		if(ali){
			if(maks == 0){
				/*
				 * naredimo enostavno potezo (1 skok, kjer ne jemo).
				 */
				stanje.narediPotezo(lok1.getX(), lok1.getY(), lok2.getX(), lok2.getY());
				if(napotezi == Igralec.BELI){napotezi = Igralec.CRNI;}else{napotezi=Igralec.BELI;}
			}else{
				/*
				 * Ce je kralj jedel, moramo pojesti tistega, ki je eno polje pred lok2.
				 */
				int dolzina;
				if(lok2.getX() - lok1.getX() == 0){
					dolzina = abs(lok2.getY() - lok1.getY());
				}else{dolzina = abs(lok2.getX() - lok1.getX());}
				
				int[] smer = {(lok2.getX() - lok1.getX())/dolzina, (lok2.getY() - lok1.getY())/dolzina};
				stanje.narediPotezo(lok1.getX(), lok1.getY(), lok2.getX() - smer[0], lok2.getY() - smer[1], lok2.getX(), lok2.getY());

				if(maks > 1){
					nujnost = lok2;
				}else{
					nujnost = null;
					if(napotezi == Igralec.BELI){napotezi = Igralec.CRNI;}else{napotezi=Igralec.BELI;}
				}
			}
			/*
			 * Ce kdo pride na zadnje polje, se spremeni v kralja:
			 */
			stanje.mozjeVKralje();

			/*
			 * Spremenili smo stanje, kdo je na potezi in nujnost,
			 * treba je na novo izraèunati možne poteze:
			 */
			if(nujnost == null){
				moznePoteze = generirajPoteze();
				if(moznePoteze.isEmpty()){
					if(napotezi == Igralec.BELI){
						napotezi = Igralec.ZMAGACRNI;
						System.out.println("Zmagal je CRNI!");
					}else{
						napotezi = Igralec.ZMAGABELI;
						System.out.println("Zmagal je BELI!");
					}
				}
			}else{
				maks = 0;
				if(napotezi == Igralec.BELI){
					Poteza pot = new Poteza();
					pot.add(nujnost);
					if(stanje.get(nujnost.getX(),nujnost.getY()) == Polje.BelMoz){
						LinkedList<Poteza> set = generirajPoteze_belmoz(pot);
						moznePoteze.clear();
						for(Poteza poteza: set){
							int k = poteza.size() -1;
							if(k == maks){
								moznePoteze.add(poteza);
							}
							if(k > maks){
								moznePoteze.clear();
								moznePoteze.add(poteza);
								maks = k;
							}
						}
					}else{
						LinkedList<Poteza> set = generirajPoteze_belkralj(pot);
						moznePoteze.clear();
						for(Poteza poteza: set){
							int k = poteza.size() - 1;
							if(k == maks){
								moznePoteze.add(poteza);
							}
							if(k > maks){
								moznePoteze.clear();
								moznePoteze.add(poteza);
								maks = k;
							}
						}
					}
				}else if(napotezi == Igralec.CRNI){
					Poteza pot = new Poteza();
					pot.add(nujnost);
					if(stanje.get(nujnost.getX(),nujnost.getY()) == Polje.CrniMoz){
						LinkedList<Poteza> set = generirajPoteze_crnimoz(pot);
						moznePoteze.clear();
						for(Poteza poteza: set){
							int k = poteza.size() - 1;
							if(k == maks){
								moznePoteze.add(poteza);
							}
							if(k > maks){
								moznePoteze.clear();
								moznePoteze.add(poteza);
								maks = k;
							}
						}
					}else{
						LinkedList<Poteza> set = generirajPoteze_crnikralj(pot);
						moznePoteze.clear();
						for(Poteza poteza: set){
							int k = poteza.size() - 1;
							if(k == maks){
								moznePoteze.add(poteza);
							}
							if(k > maks){
								moznePoteze.clear();
								moznePoteze.add(poteza);
								maks = k;
							}
						}
					}
				}
			}
		}
		return ali;
	}
	/*
	 * Poteze kjer lahko figura je'.
	 */
	
	private LinkedList<Poteza> generirajPoteze(){
		LinkedList<Poteza> f = new LinkedList<Poteza>();
		maks = 1;
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				if(napotezi == Igralec.BELI){
					if(stanje.get(i, j) == Polje.BelMoz){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.add(lok);
						LinkedList<Poteza> gen = generirajPoteze_belmoz(pot);
						for(Poteza poteza: gen){
							int k = poteza.size() - 1;
							if(k == maks){
								f.add(poteza);
							}else if(k > maks){
								f.clear();
								f.add(poteza);
								maks = k;
							}
						}
					}else if(stanje.get(i, j) == Polje.BelKralj){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.add(lok);
						LinkedList<Poteza> gen = generirajPoteze_belkralj(pot);
						for(Poteza poteza: gen){
							int k = poteza.size() - 1;
							if(k == maks){
								f.add(poteza);
							}else if(k > maks){
								f.clear();
								f.add(poteza);
								maks = k;
							}
						}
					}
				}else if(napotezi == Igralec.CRNI){
					if(stanje.get(i, j) == Polje.CrniMoz){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.add(lok);
						LinkedList<Poteza> gen = generirajPoteze_crnimoz(pot);
						for(Poteza poteza: gen){
							int k = poteza.size() - 1;
							if(k == maks){
								f.add(poteza);
							}else if(k > maks){
								f.clear();
								f.add(poteza);
								maks = k;
							}
						}
					}else if(stanje.get(i, j) == Polje.CrniKralj){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.add(lok);
						LinkedList<Poteza> gen = generirajPoteze_crnikralj(pot);
						for(Poteza poteza: gen){
							int k = poteza.size() - 1;
							if(k == maks){
								f.add(poteza);
							}else if(k > maks){
								f.clear();
								f.add(poteza);
								maks = k;
							}
						}
					}	
				}
			}
		}
		if(f.isEmpty()){
			maks = 0;
			return generirajEnostavne();
		}else{
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
				if(napotezi == Igralec.BELI){
					if(stanje.get(i, j) == Polje.BelMoz){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.add(lok);
						LinkedList<Poteza> gen = generirajEnostavne_belmoz(pot);
						for(Poteza poteza: gen){
							f.add(poteza);
						}
					}else if(stanje.get(i, j) == Polje.BelKralj){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.add(lok);
						LinkedList<Poteza> gen = generirajEnostavne_kralj(pot);
						for(Poteza poteza: gen){
							f.add(poteza);
						}
					}
				}else if(napotezi == Igralec.CRNI){
					if(stanje.get(i, j) == Polje.CrniMoz){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.add(lok);
						LinkedList<Poteza> gen = generirajEnostavne_crnimoz(pot);
						for(Poteza poteza: gen){
							f.add(poteza);
						}
					}else if(stanje.get(i, j) == Polje.CrniKralj){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.add(lok);
						LinkedList<Poteza> gen = generirajEnostavne_kralj(pot);
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
							Polje pojeden = stanje.narediPotezo(x, y, x + xy[0], y + xy[1], x + 2*xy[0], y + 2*xy[1]);
							
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
							stanje.razveljaviPotezo(x, y, x + xy[0], y + xy[1], x + 2*xy[0], y + 2*xy[1], pojeden);
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
							Polje pojeden = stanje.narediPotezo(x, y, x + xy[0], y + xy[1], x + 2*xy[0], y + 2*xy[1]);
							
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
							stanje.razveljaviPotezo(x, y, x + xy[0], y + xy[1], x + 2*xy[0], y + 2*xy[1], pojeden);
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
							Polje pojeden = stanje.narediPotezo(x, y, x + (k-1)*xy[0], y + (k-1)*xy[1], x + k*xy[0], y + k*xy[1]);

							Poteza nova = new Poteza();
							nova = pot.clone();
							Lokacija novalok = new Lokacija(x + k * xy[0], y + k * xy[1]);
							nova.add(novalok);
							
							
							LinkedList<Poteza> novset = generirajPoteze_belkralj(nova);
							for(Poteza poteza: novset){
								set.add(poteza);
							}
							//razveljavimo:
							stanje.razveljaviPotezo(x, y, x + (k-1)*xy[0], y + (k-1)*xy[1], x + k*xy[0], y + k*xy[1], pojeden);
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
							Polje pojeden = stanje.narediPotezo(x, y, x + (k-1)*xy[0], y + (k-1)*xy[1], x + k*xy[0], y + k*xy[1]);
							
							Poteza nova = new Poteza();
							nova = pot.clone();
							Lokacija novalok = new Lokacija(x + k*xy[0], y + k*xy[1]);
							nova.add(novalok);
							
							
							LinkedList<Poteza> novset = generirajPoteze_crnikralj(nova);
							for(Poteza poteza: novset){
								set.add(poteza);
							}
							//razveljavimo:
							stanje.razveljaviPotezo(x, y, x + (k-1)*xy[0], y + (k-1)*xy[1], x + k*xy[0], y + k*xy[1], pojeden);
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
	
	private LinkedList<Poteza> generirajEnostavne_belmoz(Poteza pot){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0}};
		int x = pot.getX(pot.size()-1);
		int y = pot.getY(pot.size()-1);
		
		for(int[] xy: smeri){
			int k = 1;
			boolean stikalo = true;
			while(stikalo){
				// Ce pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if(stanje.get(x + k*xy[0],y + k*xy[1]) == Polje.Prazno){
						Poteza nova = new Poteza();
						Lokacija lok1 = new Lokacija(x,y);
						nova.add(lok1);
						Lokacija novalok = new Lokacija(x + k*xy[0], y + k*xy[1]);
						nova.add(novalok);
						set.add(nova);
						stikalo = false;
					}else if(stanje.get(x + k*xy[0],y + k*xy[1]) == Polje.BelMoz){}else{stikalo = false;}
				}else{stikalo = false;}
				k++;
			}	
		}
		return set;
	}
	
	private LinkedList<Poteza> generirajEnostavne_crnimoz(Poteza pot){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,1},{0,1},{1, 1},{1, 0}};
		int x = pot.getX(pot.size()-1);
		int y = pot.getY(pot.size()-1);
		
		for(int[] xy: smeri){
			int k = 1;
			boolean stikalo = true;
			while(stikalo){
				// Ce pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if(stanje.get(x + k*xy[0],y + k*xy[1]) == Polje.Prazno){
						Poteza nova = new Poteza();
						Lokacija lok1 = new Lokacija(x,y);
						nova.add(lok1);
						Lokacija novalok = new Lokacija(x + k*xy[0], y + k*xy[1]);
						nova.add(novalok);
						set.add(nova);
						stikalo = false;
					}else if(stanje.get(x + k*xy[0],y + k*xy[1]) == Polje.CrniMoz){}else{stikalo = false;}
				}else{stikalo = false;}
				k++;
			}
		}
		return set;
	}
	
	private LinkedList<Poteza> generirajEnostavne_kralj(Poteza pot){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0},{-1,1},{0,1},{1, 1}};
		int x = pot.getX(pot.size()-1);
		int y = pot.getY(pot.size()-1);
		
		for(int[] xy: smeri){
			int k = 1;
			boolean stikalo = true;
			while(stikalo){
				// Ce pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if(stanje.get(x + k*xy[0],y + k*xy[1]) == Polje.Prazno){
						Poteza nova = new Poteza();
						Lokacija lok1 = new Lokacija(x,y);
						nova.add(lok1);
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
