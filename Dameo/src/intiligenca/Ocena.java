package intiligenca;

import logika.Polje;
import logika.Stanje;

public class Ocena {
	
	/*
	 * V bistvu ocenjujemo za belega igralca, na koncu mislec ugotovi, glede na igralca, ki ga igra, ali oceno pomnozi z 1 ali z -1
	 */
	
	
	/*
	 * 	- Ocenili bomo figure belega v celih številih (pozitivnih)
	 * 	- Ocenili bomo figure crnega v celih številih (negativnih)
	 * 	- Ocena pozicije =
	 * 
	 * 			Ocena belih figur - Ocena crnih figur
	 * 
	 * Tako bomo dobili oceno, koliko ocena belih presega oceno crnih, glede na oceno vseh figur na plošèi.
	 */
	
	protected static final int ZMAGA = 1<<15;
	protected static final int PORAZ = -ZMAGA;
	
	private static final int Moz = 1;
	private static final int Kralj = 3;
	
	protected static int trdaOcena(Stanje matrika) {
		int ocenaBeli = 0;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(matrika.get(i, j) == Polje.CrniMoz) {
					/*
					 * Gledamo koliko, je moz zasciten in, kako dolgo na polju je ze
					 * 
					 * Ce je za njim ali na strani njegova figura ali rob, se mu vrednost poveca
					 */
					int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0}};
					int vrednost = j + 1 + 8;
					for(int[] smer: smeri) {
						try {
							if(matrika.get(i + smer[0], j + smer[1]) == Polje.CrniMoz || matrika.get(i + smer[0], j + smer[1]) == Polje.CrniKralj) {
								vrednost++;
							}
						}catch(ArrayIndexOutOfBoundsException e) {
							vrednost++;
						}
					}
					
					ocenaBeli -= vrednost*Moz;
				}else if(matrika.get(i, j) == Polje.BelMoz) {
					/*
					 * Gledamo koliko, je moz zasciten in, kako dolgo na polju je ze
					 * 
					 * Ce je za njim ali na strani njegova figura ali rob, se mu vrednost poveca
					 */
					int[][] smeri = {{-1, 0},{-1,1},{0,1},{1, 1},{1, 0}};
					int vrednost = 8 - j + 8;
					for(int[] smer: smeri) {
						try {
							if(matrika.get(i + smer[0], j + smer[1]) == Polje.BelMoz || matrika.get(i + smer[0], j + smer[1]) == Polje.BelKralj) {
								vrednost++;
							}
						}catch(ArrayIndexOutOfBoundsException e) {
							vrednost++;
						}
					}
					
					ocenaBeli += vrednost*Moz;
				}else if(matrika.get(i, j) == Polje.BelKralj) {
					/*
					 * Gledamo koliko, je kralj zasciten
					 * 
					 * Ce je okoli njega njegova figura ali rob, se mu vrednost poveca
					 */
					int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0},{1,1},{0,1},{-1,1}};
					int vrednost = 8;
					for(int[] smer: smeri) {
						try {
							if(matrika.get(i + smer[0], j + smer[1]) == Polje.BelMoz || matrika.get(i + smer[0], j + smer[1]) == Polje.BelKralj) {
								vrednost++;
							}
						}catch(ArrayIndexOutOfBoundsException e) {
							vrednost++;
						}
					}
					
					ocenaBeli += vrednost*Kralj;
				}else if(matrika.get(i, j) == Polje.CrniKralj) {
					/*
					 * Gledamo koliko, je kralj zasciten
					 * 
					 * Ce je okoli njega njegova figura ali rob, se mu vrednost poveca
					 */
					int[][] smeri = {{-1, 0},{-1,-1},{0,-1},{1, -1},{1, 0},{1,1},{0,1},{-1,1}};
					int vrednost = 8;
					for(int[] smer: smeri) {
						try {
							if(matrika.get(i + smer[0], j + smer[1]) == Polje.CrniMoz || matrika.get(i + smer[0], j + smer[1]) == Polje.CrniKralj) {
								vrednost++;
							}
						}catch(ArrayIndexOutOfBoundsException e) {
							vrednost++;
						}
					}
					
					ocenaBeli -= vrednost*Kralj;
				}
			}
		}
		return ocenaBeli;
	}
}
