package Dameo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Igra {
	public Stanje stanje;
	public HashMap<Lokacija, HashSet<Poteza>> moznePoteze;
	public int napotezi;
	public int maks;
	public Lokacija nujnost;
	
	public Igra(){
		stanje = new Stanje();
		//crni zgoraj pozitivni, beli spodaj negativni
		//1/-1 možje, 2/-2 kralji.
		moznePoteze = new HashMap<Lokacija, HashSet<Poteza>>();
		napotezi = 1;
		// 1 - je beli (negativen), 2 - je crni (pozitiven)
		nujnost = new Lokacija(1,1);
	}
	
	{
		moznePoteze = generirajPoteze();
		nujnost = null;
	}
	
	//Figuro na polju lok1 premaknemo na lok2:
	public static int abs(int x){
		if(x>=0){
			return x;
		}else{return -x;}
	}
	
	public void Odigraj(Lokacija lok1, Lokacija lok2){
		//Poteze igramo 1 polje naenkrat, torej moramo izbrati da se figura premakne na 2. mesto v možni potezi.
		boolean ali = false;
		for(HashSet<Poteza> set: moznePoteze.values()){
			for(Poteza poteza: set){
				if(poteza.sestavljena.get(0).equals(lok1) && poteza.sestavljena.get(1).equals(lok2)){
					ali = true;
				}
			}
		}
		
		if(ali){
			System.out.println("Odigrali bomo");
			if(maks == 0){
				stanje.matrika[lok2.y][lok2.x] = stanje.matrika[lok1.y][lok1.x];
				stanje.matrika[lok1.y][lok1.x] = 0;
				if(napotezi == 1){napotezi = 2;}else{napotezi=1;}
			}else{
				//da vemo koga pojemo:
				int dolzina;
				if(lok2.x - lok1.x == 0){
					dolzina = abs(lok2.y - lok1.y);
				}else{dolzina = abs(lok2.x - lok1.x);}
				
				int[] smer = {(lok2.x - lok1.x)/dolzina, (lok2.y - lok1.y)/dolzina};
				stanje.matrika[lok2.y][lok2.x] = stanje.matrika[lok1.y][lok1.x];
				stanje.matrika[lok1.y][lok1.x] = 0;
				//Èe je kralj jedel, moramo pojesti tistega, ki je eno polje pred lokacijo 2.
				stanje.matrika[lok2.y - smer[1]][lok2.x - smer[0]] = 0;
				if(maks > 1){
					nujnost.set(lok2.x, lok2.y);
				}else{
					if(!(nujnost == null)){
						nujnost = null;
					}
					if(napotezi == 1){napotezi = 2;}else{napotezi=1;}
				}
			}
			//Èe kdo pride na zadnje polje, se spremeni v kralja:
			for(int i = 0; i<8; i++){
				if(stanje.matrika[7][i] == 1){
					stanje.matrika[7][i] = 2;
				}
				if(stanje.matrika[0][i] == -1){
					stanje.matrika[0][i] = -2;
				}
			}
			//Spremenili smo stanje, kdo je na potezi in nujnost
			//Treba je na novo izraèunati možne poteze:
			if(nujnost == null){
				moznePoteze = generirajPoteze();
				if(moznePoteze.isEmpty()){
					if(napotezi == 1){
						System.out.println("Zmagal je CRNI!");
					}else{
						System.out.println("Zmagal je BELI!");
					}
				}
			// Èe imamo nujnost
			}else{
				maks = 1;
				if(napotezi == 1){
					Poteza pot = new Poteza();
					pot.sestavljena.add(nujnost);
					if(stanje.matrika[nujnost.y][nujnost.x] == -1){
						Set<Poteza> set = generirajPoteze_belmoz(pot);
						moznePoteze.clear();
						for(Poteza poteza: set){
							int k = poteza.size() - 1;
							if(k == maks){
								if(moznePoteze.containsKey(nujnost)){
									moznePoteze.get(nujnost).add(poteza);
								}else{
									HashSet<Poteza> nov = new HashSet<Poteza>();
									nov.add(poteza);
									moznePoteze.put(nujnost, nov);
								}
							}else if(k > maks){
								moznePoteze.clear();
								HashSet<Poteza> nov = new HashSet<Poteza>();
								nov.add(poteza);
								moznePoteze.put(nujnost, nov);
								maks = k;
							}
						}
					}else{
						Set<Poteza> set = generirajPoteze_belkralj(pot);
						moznePoteze.clear();
						for(Poteza poteza: set){
							int k = poteza.size() - 1;
							if(k == maks){
								if(moznePoteze.containsKey(nujnost)){
									moznePoteze.get(nujnost).add(poteza);
								}else{
									HashSet<Poteza> nov = new HashSet<Poteza>();
									nov.add(poteza);
									moznePoteze.put(nujnost, nov);
								}
							}else if(k > maks){
								moznePoteze.clear();
								HashSet<Poteza> nov = new HashSet<Poteza>();
								nov.add(poteza);
								moznePoteze.put(nujnost, nov);
								maks = k;
							}
						}
					}
				}else if(napotezi == 2){
					Poteza pot = new Poteza();
					pot.sestavljena.add(nujnost);
					if(stanje.matrika[nujnost.y][nujnost.x] == 1){
						Set<Poteza> set = generirajPoteze_crnimoz(pot);
						moznePoteze.clear();
						for(Poteza poteza: set){
							int k = poteza.size() - 1;
							if(k == maks){
								if(moznePoteze.containsKey(nujnost)){
									moznePoteze.get(nujnost).add(poteza);
								}else{
									HashSet<Poteza> nov = new HashSet<Poteza>();
									nov.add(poteza);
									moznePoteze.put(nujnost, nov);
								}
							}else if(k > maks){
								moznePoteze.clear();
								HashSet<Poteza> nov = new HashSet<Poteza>();
								nov.add(poteza);
								moznePoteze.put(nujnost, nov);
								maks = k;
							}
						}
					}else{
						Set<Poteza> set = generirajPoteze_crnikralj(pot);
						moznePoteze.clear();
						for(Poteza poteza: set){
							int k = poteza.size() - 1;
							if(k == maks){
								if(moznePoteze.containsKey(nujnost)){
									moznePoteze.get(nujnost).add(poteza);
								}else{
									HashSet<Poteza> nov = new HashSet<Poteza>();
									nov.add(poteza);
									moznePoteze.put(nujnost, nov);
								}
							}else if(k > maks){
								moznePoteze.clear();
								HashSet<Poteza> nov = new HashSet<Poteza>();
								nov.add(poteza);
								moznePoteze.put(nujnost, nov);
								maks = k;
							}
						}
					}
				}
			}
		}else{System.out.println("Ne moreš odigrati!");}
	}
	
	public HashMap<Lokacija, HashSet<Poteza>> generirajPoteze(){
		
		//Najprej èe sploh lahko koga poje:
		
		HashMap<Lokacija, HashSet<Poteza>> f = new HashMap<Lokacija, HashSet<Poteza>>();
		maks = 1;
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				if(napotezi == 1){
					if(stanje.matrika[j][i] == -1){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
						Set<Poteza> gen = generirajPoteze_belmoz(pot);
						for(Poteza poteza: gen){
							int k = poteza.size() - 1;
							if(k == maks){
								if(f.containsKey(lok)){
									f.get(lok).add(poteza);
								}else{
									HashSet<Poteza> set = new HashSet<Poteza>();
									set.add(poteza);
									f.put(lok, set);
								}
							}else if(k > maks){
								f.clear();
								HashSet<Poteza> set = new HashSet<Poteza>();
								set.add(poteza);
								f.put(lok, set);
								maks = k;
							}
						}
					}else if(stanje.matrika[j][i] == -2){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
						Set<Poteza> gen = generirajPoteze_belkralj(pot);
						for(Poteza poteza: gen){
							int k = poteza.size() - 1;
							if(k == maks){
								if(f.containsKey(lok)){
									f.get(lok).add(poteza);
								}else{
									HashSet<Poteza> set = new HashSet<Poteza>();
									set.add(poteza);
									f.put(lok, set);
								}
							}else if(k > maks){
								f.clear();
								HashSet<Poteza> set = new HashSet<Poteza>();
								set.add(poteza);
								f.put(lok, set);
								maks = k;
							}
						}
					}
				}else if(napotezi == 2){
					if(stanje.matrika[j][i] == 1){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
						Set<Poteza> gen = generirajPoteze_crnimoz(pot);
						for(Poteza poteza: gen){
							int k = poteza.size() - 1;
							if(k == maks){
								if(f.containsKey(lok)){
									f.get(lok).add(poteza);
								}else{
									HashSet<Poteza> set = new HashSet<Poteza>();
									set.add(poteza);
									f.put(lok, set);
								}
							}else if(k > maks){
								f.clear();
								HashSet<Poteza> set = new HashSet<Poteza>();
								set.add(poteza);
								f.put(lok, set);
								maks = k;
							}
						}
					}else if(stanje.matrika[j][i] == 2){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
						Set<Poteza> gen = generirajPoteze_crnikralj(pot);
						for(Poteza poteza: gen){
							int k = poteza.size() - 1;
							if(k == maks){
								if(f.containsKey(lok)){
									f.get(lok).add(poteza);
								}else{
									HashSet<Poteza> set = new HashSet<Poteza>();
									set.add(poteza);
									f.put(lok, set);
								}
							}else if(k > maks){
								f.clear();
								HashSet<Poteza> set = new HashSet<Poteza>();
								set.add(poteza);
								f.put(lok, set);
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
	
	public HashMap<Lokacija, HashSet<Poteza>> generirajEnostavne(){
		HashMap<Lokacija, HashSet<Poteza>> f = new HashMap<Lokacija, HashSet<Poteza>>();
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				if(napotezi == 1){
					if(stanje.matrika[j][i] == -1){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
						Set<Poteza> gen = generirajEnostavne_belmoz(pot);
						for(Poteza poteza: gen){
							if(f.containsKey(lok)){
								f.get(lok).add(poteza);
							}else{
								HashSet<Poteza> set = new HashSet<Poteza>();
								set.add(poteza);
								f.put(lok, set);
							}
						}
					}else if(stanje.matrika[j][i] == -2){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
						Set<Poteza> gen = generirajEnostavne_kralj(pot);
						for(Poteza poteza: gen){
							if(f.containsKey(lok)){
								f.get(lok).add(poteza);
							}else{
								HashSet<Poteza> set = new HashSet<Poteza>();
								set.add(poteza);
								f.put(lok, set);
							}
						}
					}
				}else if(napotezi == 2){
					if(stanje.matrika[j][i] == 1){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
						Set<Poteza> gen = generirajEnostavne_crnimoz(pot);
						for(Poteza poteza: gen){
							if(f.containsKey(lok)){
								f.get(lok).add(poteza);
							}else{
								HashSet<Poteza> set = new HashSet<Poteza>();
								set.add(poteza);
								f.put(lok, set);
							}
						}
					}else if(stanje.matrika[j][i] == 2){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
						Set<Poteza> gen = generirajEnostavne_kralj(pot);
						for(Poteza poteza: gen){
							if(f.containsKey(lok)){
								f.get(lok).add(poteza);
							}else{
								HashSet<Poteza> set = new HashSet<Poteza>();
								set.add(poteza);
								f.put(lok, set);
							}
						}
					}
				}
			}
		}
		return f;
	}
	
	public Set<Poteza> generirajPoteze_belmoz(Poteza pot){
		Set<Poteza> set = new HashSet<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0}};
		int x = pot.sestavljena.get(pot.sestavljena.size()-1).x;
		int y = pot.sestavljena.get(pot.sestavljena.size()-1).y;
		boolean lahkoje = false;
			// zaèetna poteza = <lok>
		for(int[] xy: smeri){
			
			// Èe pade ven:
			if(0 <= y + 2*xy[1] && y + 2*xy[1] <= 7 && 0 <= x + 2*xy[0] && x + 2*xy[0] <= 7){
				if(stanje.matrika[y + xy[1]][x + xy[0]] > 0){
					if(stanje.matrika[y + 2*xy[1]][x + 2*xy[0]] == 0){
						lahkoje = true;
						// Èe beli moz pride na konèno polje se tam poteza zakljuèi, spremeni se v kralja:
						if(y + 2*xy[1] == 0){
							Poteza nova = pot;
							Lokacija novalok = new Lokacija(x + 2*xy[0], y + 2*xy[1]);
							nova.sestavljena.add(novalok);
							set.add(nova);
						}else{
							int pojeden = stanje.matrika[y + xy[1]][x + xy[0]];
							stanje.matrika[y][x] = 0;
							stanje.matrika[y + xy[1]][x + xy[0]] = 0;
							stanje.matrika[y + 2*xy[1]][x + 2*xy[0]] = -1;
							Poteza nova = pot;
							Lokacija novalok = new Lokacija(x + 2*xy[0], y + 2*xy[1]);
							nova.sestavljena.add(novalok);
							Set<Poteza> novset = generirajPoteze_belmoz(nova);
							for(Poteza poteza: novset){
								set.add(poteza);
							}
							//razveljavimo:
							stanje.matrika[y + xy[1]][x + xy[0]] = pojeden;
							stanje.matrika[y + 2*xy[1]][x + 2*xy[0]] = 0;
							stanje.matrika[y][x] = -1;
							// podaljšaj potezo za en skok
							// g = rekurzivni klic, ki dopolni potezo do konca
							// posodobi f glede na g
							// èe smo našli daljšo potezo ...
							// razveljavi skok v potezi
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
	
	public Set<Poteza> generirajPoteze_crnimoz(Poteza pot){
		Set<Poteza> set = new HashSet<Poteza>();
		int[][] smeri = {{-1, 0},{-1,1},{0,1},{1, 1},{1, 0}};
		int x = pot.sestavljena.get(pot.sestavljena.size()-1).x;
		int y = pot.sestavljena.get(pot.sestavljena.size()-1).y;
		boolean lahkoje = false;
			// zaèetna poteza = <lok>
		for(int[] xy: smeri){
			// Èe pade ven:
			if(0 <= y + 2*xy[1] && y + 2*xy[1] <= 7 && 0 <= x + 2*xy[0] && x + 2*xy[0] <= 7){
				if(stanje.matrika[y + xy[1]][x + xy[0]] < 0){
					if(stanje.matrika[y + 2*xy[1]][x + 2*xy[0]] == 0){
						lahkoje = true;
						// Èe crni moz pride na konèno polje se tam poteza zakljuèi, spremeni se v kralja:
						if(y + 2*xy[1] == 7){
							Poteza nova = pot;
							Lokacija novalok = new Lokacija(x + 2*xy[0], y + 2*xy[1]);
							nova.sestavljena.add(novalok);
							set.add(nova);
						}else{
							int pojeden = stanje.matrika[y + xy[1]][x + xy[0]];
							stanje.matrika[y][x] = 0;
							stanje.matrika[y + xy[1]][x + xy[0]] = 0;
							stanje.matrika[y + 2*xy[1]][x + 2*xy[0]] = 1;
							Poteza nova = pot;
							Lokacija novalok = new Lokacija(x + 2*xy[0], y + 2*xy[1]);
							nova.sestavljena.add(novalok);
							Set<Poteza> novset = generirajPoteze_crnimoz(nova);
							for(Poteza poteza: novset){
								set.add(poteza);
							}
							//razveljavimo:
							stanje.matrika[y + xy[1]][x + xy[0]] = pojeden;
							stanje.matrika[y + 2*xy[1]][x + 2*xy[0]] = 0;
							stanje.matrika[y][x] = 1;
							// podaljšaj potezo za en skok
							// g = rekurzivni klic, ki dopolni potezo do konca
							// posodobi f glede na g
							// èe smo našli daljšo potezo ...
							// razveljavi skok v potezi
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
	
	public Set<Poteza> generirajPoteze_belkralj(Poteza pot){
		Set<Poteza> set = new HashSet<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0},{1,1},{0,1},{-1,1}};
		int x = pot.sestavljena.get(pot.sestavljena.size()-1).x;
		int y = pot.sestavljena.get(pot.sestavljena.size()-1).y;
		boolean lahkoje = false;
			// zaèetna poteza = <lok>
		for(int[] xy: smeri){
			boolean stikalo = true;
			int k = 2;
			while(stikalo){
				
				// Èe pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if(stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] > 0){
						if(stanje.matrika[y + k*xy[1]][x + k*xy[0]] == 0){
							lahkoje = true;
							int pojeden = stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]];
							stanje.matrika[y][x] = 0;
							stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] = 0;
							stanje.matrika[y + k*xy[1]][x + k*xy[0]] = -2;
							Poteza nova = pot;
							Lokacija novalok = new Lokacija(x + k*xy[0], y + k*xy[1]);
							nova.sestavljena.add(novalok);
							Set<Poteza> novset = generirajPoteze_belkralj(nova);
							for(Poteza poteza: novset){
								set.add(poteza);
							}
							//razveljavimo:
							stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] = pojeden;
							stanje.matrika[y + k*xy[1]][x + k*xy[0]] = 0;
							stanje.matrika[y][x] = -2;
							// podaljšaj potezo za en skok
							// g = rekurzivni klic, ki dopolni potezo do konca
							// posodobi f glede na g
							// èe smo našli daljšo potezo ...
							// razveljavi skok v potezi
							stikalo = false;
						}
					}
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
	
	public Set<Poteza> generirajPoteze_crnikralj(Poteza pot){
		Set<Poteza> set = new HashSet<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0},{1,1},{0,1},{-1,1}};
		int x = pot.sestavljena.get(pot.sestavljena.size()-1).x;
		int y = pot.sestavljena.get(pot.sestavljena.size()-1).y;
		boolean lahkoje = false;
			// zaèetna poteza = <lok>
		for(int[] xy: smeri){
			boolean stikalo = true;
			int k = 2;
			while(stikalo){
				
				// Èe pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if(stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] < 0){
						if(stanje.matrika[y + k*xy[1]][x + k*xy[0]] == 0){
							lahkoje = true;
							int pojeden = stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]];
							stanje.matrika[y][x] = 0;
							stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] = 0;
							stanje.matrika[y + k*xy[1]][x + k*xy[0]] = 2;
							Poteza nova = pot;
							Lokacija novalok = new Lokacija(x + k*xy[0], y + k*xy[1]);
							nova.sestavljena.add(novalok);
							Set<Poteza> novset = generirajPoteze_crnikralj(nova);
							for(Poteza poteza: novset){
								set.add(poteza);
							}
							//razveljavimo:
							stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] = pojeden;
							stanje.matrika[y + k*xy[1]][x + k*xy[0]] = 0;
							stanje.matrika[y][x] = 2;
							// podaljšaj potezo za en skok
							// g = rekurzivni klic, ki dopolni potezo do konca
							// posodobi f glede na g
							// èe smo našli daljšo potezo ...
							// razveljavi skok v potezi
							stikalo = false;
						}
					}
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
	
	public Set<Poteza> generirajEnostavne_belmoz(Poteza pot){
		Set<Poteza> set = new HashSet<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0}};
		int x = pot.sestavljena.get(pot.sestavljena.size()-1).x;
		int y = pot.sestavljena.get(pot.sestavljena.size()-1).y;
		
		for(int[] xy: smeri){
			int k = 1;
			boolean stikalo = true;
			while(stikalo){
				// Èe pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if(stanje.matrika[y + k*xy[1]][x + k*xy[0]] == 0){
						Poteza nova = new Poteza();
						Lokacija lok1 = new Lokacija(x,y);
						nova.sestavljena.add(lok1);
						Lokacija novalok = new Lokacija(x + k*xy[0], y + k*xy[1]);
						nova.sestavljena.add(novalok);
						set.add(nova);
						stikalo = false;
					}else if(stanje.matrika[y + k*xy[1]][x + k*xy[0]] == -1){}else{stikalo = false;}
				}else{stikalo = false;}
				k++;
			}	
		}
		return set;
	}
	
	public Set<Poteza> generirajEnostavne_crnimoz(Poteza pot){
		Set<Poteza> set = new HashSet<Poteza>();
		int[][] smeri = {{-1, 0},{-1,1},{0,1},{1, 1},{1, 0}};
		int x = pot.sestavljena.get(pot.sestavljena.size()-1).x;
		int y = pot.sestavljena.get(pot.sestavljena.size()-1).y;
		
		for(int[] xy: smeri){
			int k = 1;
			boolean stikalo = true;
			while(stikalo){
				// Èe pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if(stanje.matrika[y + k*xy[1]][x + k*xy[0]] == 0){
						Poteza nova = new Poteza();
						Lokacija lok1 = new Lokacija(x,y);
						nova.sestavljena.add(lok1);
						Lokacija novalok = new Lokacija(x + k*xy[0], y + k*xy[1]);
						nova.sestavljena.add(novalok);
						set.add(nova);
						stikalo = false;
					}else if(stanje.matrika[y + k*xy[1]][x + k*xy[0]] == 1){}else{stikalo = false;}
				}else{stikalo = false;}
				k++;
			}
		}
		return set;
	}
	
	public Set<Poteza> generirajEnostavne_kralj(Poteza pot){
		Set<Poteza> set = new HashSet<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0},{-1,1},{0,1},{1, 1}};
		int x = pot.sestavljena.get(pot.sestavljena.size()-1).x;
		int y = pot.sestavljena.get(pot.sestavljena.size()-1).y;
		
		for(int[] xy: smeri){
			int k = 1;
			boolean stikalo = true;
			while(stikalo){
				// Èe pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if(stanje.matrika[y + k*xy[1]][x + k*xy[0]] == 0){
						Poteza nova = new Poteza();
						Lokacija lok1 = new Lokacija(x,y);
						nova.sestavljena.add(lok1);
						Lokacija novalok = new Lokacija(x + k*xy[0], y + k*xy[1]);
						nova.sestavljena.add(novalok);
						set.add(nova);
					}else{stikalo = false;}
				}else{stikalo = false;}
				k++;
			}
		}
		return set;
	}
}
