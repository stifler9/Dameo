package Dameo;

import java.util.LinkedList;

public class Igra {
	public Stanje stanje;
	public LinkedList<Poteza> moznePoteze;
	public Igralec napotezi;
	public int maks;
	public Lokacija nujnost;
	
	public Igra(){
		stanje = new Stanje();
		moznePoteze = new LinkedList<Poteza>();
		napotezi = Igralec.I1;
		// 1 - je beli 
		// 2 - je crni 
		nujnost = null;
		moznePoteze = generirajPoteze();
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
		for(Poteza poteza: moznePoteze){
			if(poteza.naPrvemMestu(lok1) && poteza.naDrugemMestu(lok2)){
				ali = true;
			}
		}
		
		if(ali){
			System.out.println("Odigrali bomo");
			if(maks == 0){
				stanje.matrika[lok2.y][lok2.x] = stanje.matrika[lok1.y][lok1.x];
				stanje.matrika[lok1.y][lok1.x] = Polje.Prazno;
				if(napotezi == Igralec.I1){napotezi = Igralec.I2;}else{napotezi=Igralec.I1;}
			}else{
				//da vemo koga pojemo:
				int dolzina;
				if(lok2.x - lok1.x == 0){
					dolzina = abs(lok2.y - lok1.y);
				}else{dolzina = abs(lok2.x - lok1.x);}
				
				int[] smer = {(lok2.x - lok1.x)/dolzina, (lok2.y - lok1.y)/dolzina};
				stanje.matrika[lok2.y][lok2.x] = stanje.matrika[lok1.y][lok1.x];
				stanje.matrika[lok1.y][lok1.x] = Polje.Prazno;
				
				//Èe je kralj jedel, moramo pojesti tistega, ki je eno polje pred lokacijo 2.
				
				stanje.matrika[lok2.y - smer[1]][lok2.x - smer[0]] = Polje.Prazno;
				if(maks > 1){
					nujnost = lok2;
				}else{
					nujnost = null;
					if(napotezi == Igralec.I1){napotezi = Igralec.I2;}else{napotezi=Igralec.I1;}
				}
			}
			//Èe kdo pride na zadnje polje, se spremeni v kralja:
			for(int i = 0; i<8; i++){
				if(stanje.matrika[7][i] == Polje.CrniMoz){
					stanje.matrika[7][i] = Polje.CrniKralj;
				}
				if(stanje.matrika[0][i] == Polje.BelMoz){
					stanje.matrika[0][i] = Polje.BelKralj;
				}
			}
			//Spremenili smo stanje, kdo je na potezi in nujnost
			//Treba je na novo izraèunati možne poteze:
			if(nujnost == null){
				moznePoteze = generirajPoteze();
				if(moznePoteze.isEmpty()){
					if(napotezi == Igralec.I1){
						napotezi = Igralec.ZMAGA2;
						System.out.println("Zmagal je CRNI!");
					}else{
						napotezi = Igralec.ZMAGA1;
						System.out.println("Zmagal je BELI!");
					}
				}
			// Èe imamo nujnost
			}else{
				maks = 0;
				if(napotezi == Igralec.I1){
					Poteza pot = new Poteza();
					Lokacija loka = new Lokacija(nujnost.x, nujnost.y);
					pot.sestavljena.add(loka);
					if(stanje.matrika[nujnost.y][nujnost.x] == Polje.BelMoz){
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
				}else if(napotezi == Igralec.I2){
					Poteza pot = new Poteza();
					Lokacija loka = new Lokacija(nujnost.x, nujnost.y);
					pot.sestavljena.add(loka);
					if(stanje.matrika[nujnost.y][nujnost.x] == Polje.CrniMoz){
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
			if(moznePoteze.isEmpty()) {System.out.println("Nekaj je narobe!");}
			}
		}else{System.out.println("Ne moreš odigrati!");}
	}
	
	public Polje[][] praznoPolje(){
		Polje[][] polje = new Polje[8][8];
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++){
				polje[j][i] = Polje.Prazno;
			}
		}
		return polje;
	}
	
	public LinkedList<Poteza> generirajPoteze(){
		
		//Najprej èe sploh lahko koga poje:
		
		LinkedList<Poteza> f = new LinkedList<Poteza>();
		maks = 1;
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				if(napotezi == Igralec.I1){
					if(stanje.matrika[j][i] == Polje.BelMoz){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
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
					}else if(stanje.matrika[j][i] == Polje.BelKralj){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
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
				}else if(napotezi == Igralec.I2){
					if(stanje.matrika[j][i] == Polje.CrniMoz){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
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
					}else if(stanje.matrika[j][i] == Polje.CrniKralj){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
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
	
	public LinkedList<Poteza> generirajEnostavne(){
		LinkedList<Poteza> f = new LinkedList<Poteza>();
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				if(napotezi == Igralec.I1){
					if(stanje.matrika[j][i] == Polje.BelMoz){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
						LinkedList<Poteza> gen = generirajEnostavne_belmoz(pot);
						for(Poteza poteza: gen){
							f.add(poteza);
						}
					}else if(stanje.matrika[j][i] == Polje.BelKralj){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
						LinkedList<Poteza> gen = generirajEnostavne_kralj(pot);
						for(Poteza poteza: gen){
							f.add(poteza);
						}
					}
				}else if(napotezi == Igralec.I2){
					if(stanje.matrika[j][i] == Polje.CrniMoz){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
						LinkedList<Poteza> gen = generirajEnostavne_crnimoz(pot);
						for(Poteza poteza: gen){
							f.add(poteza);
						}
					}else if(stanje.matrika[j][i] == Polje.CrniKralj){
						Lokacija lok = new Lokacija(i, j);
						Poteza pot = new Poteza();
						pot.sestavljena.add(lok);
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
	
	public LinkedList<Poteza> generirajPoteze_belmoz(Poteza pot){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0}};
		int x = pot.sestavljena.get(pot.size()-1).x;
		int y = pot.sestavljena.get(pot.size()-1).y;
		boolean lahkoje = false;
			// zaèetna poteza = <lok>
		for(int[] xy: smeri){
			
			// Èe pade ven:
			if(0 <= y + 2*xy[1] && y + 2*xy[1] <= 7 && 0 <= x + 2*xy[0] && x + 2*xy[0] <= 7){
				if(stanje.matrika[y + xy[1]][x + xy[0]] == Polje.CrniMoz || stanje.matrika[y + xy[1]][x + xy[0]] == Polje.CrniKralj){
					if(stanje.matrika[y + 2*xy[1]][x + 2*xy[0]] == Polje.Prazno){
						lahkoje = true;
						// Èe beli moz pride na konèno polje se tam poteza zakljuèi, spremeni se v kralja:
						if(y + 2*xy[1] == 0){
							Poteza nova = new Poteza();
							nova = pot.clone();
							Lokacija novalok = new Lokacija(x + 2*xy[0], y + 2*xy[1]);
							nova.sestavljena.add(novalok);
							set.add(nova);
						}else{
							//Naredimo potezo na matriki in si zapomnemo koga smo pojedli:
							
							Polje pojeden = stanje.matrika[y + xy[1]][x + xy[0]];
							stanje.matrika[y][x] = Polje.Prazno;
							stanje.matrika[y + xy[1]][x + xy[0]] = Polje.Prazno;
							stanje.matrika[y + 2*xy[1]][x + 2*xy[0]] = Polje.BelMoz;
							Poteza nova = new Poteza();
							nova = pot.clone();
							Lokacija novalok = new Lokacija(x + 2*xy[0], y + 2*xy[1]);
							nova.sestavljena.add(novalok);
							
							//Rekurzivni klic:
							LinkedList<Poteza> novset = generirajPoteze_belmoz(nova);
							for(Poteza poteza: novset){
								set.add(poteza);
							}
							
							//razveljavimo:
							stanje.matrika[y + xy[1]][x + xy[0]] = pojeden;
							stanje.matrika[y + 2*xy[1]][x + 2*xy[0]] = Polje.Prazno;
							stanje.matrika[y][x] = Polje.BelMoz;
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
	
	public LinkedList<Poteza> generirajPoteze_crnimoz(Poteza pot){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,1},{0,1},{1, 1},{1, 0}};
		int x = pot.sestavljena.get(pot.size()-1).x;
		int y = pot.sestavljena.get(pot.size()-1).y;
		boolean lahkoje = false;
			// zaèetna poteza = <lok>
		for(int[] xy: smeri){
			// Èe pade ven:
			if(0 <= y + 2*xy[1] && y + 2*xy[1] <= 7 && 0 <= x + 2*xy[0] && x + 2*xy[0] <= 7){
				if(stanje.matrika[y + xy[1]][x + xy[0]] == Polje.BelMoz || stanje.matrika[y + xy[1]][x + xy[0]] == Polje.BelKralj){
					if(stanje.matrika[y + 2*xy[1]][x + 2*xy[0]] == Polje.Prazno){
						lahkoje = true;
						// Èe crni moz pride na konèno polje se tam poteza zakljuèi, spremeni se v kralja:
						if(y + 2*xy[1] == 7){
							Poteza nova = new Poteza();
							nova = pot.clone();
							Lokacija novalok = new Lokacija(x + 2*xy[0], y + 2*xy[1]);
							nova.sestavljena.add(novalok);
							set.add(nova);
						}else{
							//Naredimo potezo na matriki in si zapomnemo koga smo pojedli:
							Polje pojeden = stanje.matrika[y + xy[1]][x + xy[0]];
							stanje.matrika[y][x] = Polje.Prazno;
							stanje.matrika[y + xy[1]][x + xy[0]] = Polje.Prazno;
							stanje.matrika[y + 2*xy[1]][x + 2*xy[0]] = Polje.CrniMoz;
							Poteza nova = new Poteza();
							nova = pot.clone();
							Lokacija novalok = new Lokacija(x + 2*xy[0], y + 2*xy[1]);
							nova.sestavljena.add(novalok);
							
							//Rekurzivni klic
							LinkedList<Poteza> novset = generirajPoteze_crnimoz(nova);
							for(Poteza poteza: novset){
								set.add(poteza);
							}
							//razveljavimo:
							stanje.matrika[y + xy[1]][x + xy[0]] = pojeden;
							stanje.matrika[y + 2*xy[1]][x + 2*xy[0]] = Polje.Prazno;
							stanje.matrika[y][x] = Polje.CrniMoz;
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
	
	public LinkedList<Poteza> generirajPoteze_belkralj(Poteza pot){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0},{1,1},{0,1},{-1,1}};
		int x = pot.sestavljena.get(pot.size()-1).x;
		int y = pot.sestavljena.get(pot.size()-1).y;
		boolean lahkoje = false;
			// zaèetna poteza = <lok>
		for(int[] xy: smeri){
			boolean stikalo = true;
			int k = 2;
			while(stikalo){
				// Èe pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if((stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] == Polje.CrniMoz || stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] == Polje.CrniKralj)
					&& (stanje.matrika[y + k*xy[1]][x + k*xy[0]] == Polje.Prazno)) {
							lahkoje = true;
							
							//Naredimo potezo na matriki in si zapomnemo koga smo pojedli:
							Polje pojeden = stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]];
							stanje.matrika[y][x] = Polje.Prazno;
							stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] = Polje.Prazno;
							stanje.matrika[y + k*xy[1]][x + k*xy[0]] = Polje.BelKralj;
							Poteza nova = new Poteza();
							nova = pot.clone();
							Lokacija novalok = new Lokacija(x + k * xy[0], y + k * xy[1]);
							nova.sestavljena.add(novalok);
							
							
							LinkedList<Poteza> novset = generirajPoteze_belkralj(nova);
							for(Poteza poteza: novset){
								set.add(poteza);
							}
							//razveljavimo:
							stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] = pojeden;
							stanje.matrika[y + k*xy[1]][x + k*xy[0]] = Polje.Prazno;
							stanje.matrika[y][x] = Polje.BelKralj;
							stikalo = false;
					}else if(!(stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] == Polje.Prazno)) {stikalo = false;}
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
	
	public LinkedList<Poteza> generirajPoteze_crnikralj(Poteza pot){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0},{1,1},{0,1},{-1,1}};
		int x = pot.sestavljena.get(pot.size()-1).x;
		int y = pot.sestavljena.get(pot.size()-1).y;
		boolean lahkoje = false;
			// zaèetna poteza = <lok>
		for(int[] xy: smeri){
			boolean stikalo = true;
			int k = 2;
			while(stikalo){
				
				// Èe pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if((stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] == Polje.BelMoz || stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] == Polje.BelKralj)
					&& (stanje.matrika[y + k*xy[1]][x + k*xy[0]] == Polje.Prazno)){
							lahkoje = true;
							
							//Naredimo potezo na matriki in si zapomnemo koga smo pojedli:
							Polje pojeden = stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]];
							stanje.matrika[y][x] = Polje.Prazno;
							stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] = Polje.Prazno;
							stanje.matrika[y + k*xy[1]][x + k*xy[0]] = Polje.CrniKralj;
							Poteza nova = new Poteza();
							nova = pot.clone();
							Lokacija novalok = new Lokacija(x + k*xy[0], y + k*xy[1]);
							nova.sestavljena.add(novalok);
							
							
							LinkedList<Poteza> novset = generirajPoteze_crnikralj(nova);
							for(Poteza poteza: novset){
								set.add(poteza);
							}
							//razveljavimo:
							stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] = pojeden;
							stanje.matrika[y + k*xy[1]][x + k*xy[0]] = Polje.Prazno;
							stanje.matrika[y][x] = Polje.CrniKralj;
							stikalo = false;
					}else if(!(stanje.matrika[y + (k-1)*xy[1]][x + (k-1)*xy[0]] == Polje.Prazno)) {stikalo = false;}
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
	
	public LinkedList<Poteza> generirajEnostavne_belmoz(Poteza pot){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0}};
		int x = pot.sestavljena.get(pot.size()-1).x;
		int y = pot.sestavljena.get(pot.size()-1).y;
		
		for(int[] xy: smeri){
			int k = 1;
			boolean stikalo = true;
			while(stikalo){
				// Èe pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if(stanje.matrika[y + k*xy[1]][x + k*xy[0]] == Polje.Prazno){
						Poteza nova = new Poteza();
						Lokacija lok1 = new Lokacija(x,y);
						nova.sestavljena.add(lok1);
						Lokacija novalok = new Lokacija(x + k*xy[0], y + k*xy[1]);
						nova.sestavljena.add(novalok);
						set.add(nova);
						stikalo = false;
					}else if(stanje.matrika[y + k*xy[1]][x + k*xy[0]] == Polje.BelMoz){}else{stikalo = false;}
				}else{stikalo = false;}
				k++;
			}	
		}
		return set;
	}
	
	public LinkedList<Poteza> generirajEnostavne_crnimoz(Poteza pot){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,1},{0,1},{1, 1},{1, 0}};
		int x = pot.sestavljena.get(pot.size()-1).x;
		int y = pot.sestavljena.get(pot.size()-1).y;
		
		for(int[] xy: smeri){
			int k = 1;
			boolean stikalo = true;
			while(stikalo){
				// Èe pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if(stanje.matrika[y + k*xy[1]][x + k*xy[0]] == Polje.Prazno){
						Poteza nova = new Poteza();
						Lokacija lok1 = new Lokacija(x,y);
						nova.sestavljena.add(lok1);
						Lokacija novalok = new Lokacija(x + k*xy[0], y + k*xy[1]);
						nova.sestavljena.add(novalok);
						set.add(nova);
						stikalo = false;
					}else if(stanje.matrika[y + k*xy[1]][x + k*xy[0]] == Polje.CrniMoz){}else{stikalo = false;}
				}else{stikalo = false;}
				k++;
			}
		}
		return set;
	}
	
	public LinkedList<Poteza> generirajEnostavne_kralj(Poteza pot){
		LinkedList<Poteza> set = new LinkedList<Poteza>();
		int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0},{-1,1},{0,1},{1, 1}};
		int x = pot.sestavljena.get(pot.size()-1).x;
		int y = pot.sestavljena.get(pot.size()-1).y;
		
		for(int[] xy: smeri){
			int k = 1;
			boolean stikalo = true;
			while(stikalo){
				// Èe pade ven:
				if(0 <= y + k*xy[1] && y + k*xy[1] <= 7 && 0 <= x + k*xy[0] && x + k*xy[0] <= 7){
					if(stanje.matrika[y + k*xy[1]][x + k*xy[0]] == Polje.Prazno){
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
