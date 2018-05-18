package intiligenca;

public class Ocena {
	
	/*
	 * V bistvu ocenjujemo za belega igralca, na koncu mislec ugotovi, glede na igralca, ki ga igra, ali oceno pomnozi z 1 ali z -1
	 */
	
	
	/*
	 * Trdo oceno bomo dobili v realnih številih na lestvici od -1 do 1:
	 * 	- Ocenili bomo figure belega v celih številih (pozitivnih)
	 * 	- Ocenili bomo figure crnega v celih številih (negativnih)
	 * 	- Ocena pozicije =
	 * 
	 * 			Ocena belih figur(+) + Ocena crnih figur(-)
	 * 				- - - - -- -- - - -- - - -- -- - -
	 * 			Ocena belih figur(+) - Ocena crnih figur(-)
	 * 
	 * Tako bomo dobili oceno, koliko ocena belih presega oceno crnih, glede na oceno vseh figur na plošèi.
	 */
	
	public static double ZMAGA = 1;
	public static double PORAZ = -ZMAGA;
	
	public static int BelMoz = 1;
	public static int BelKralj = 5;
	public static int CrniMoz = -1;
	public static int CrniKralj = -5;

}
