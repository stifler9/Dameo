package Dameo;

public class Stanje {
	public int[][] matrika;
	
	public Stanje(){
		matrika = new int[8][8];
		for(int i=0; i<8; i++){
			matrika[0][i] = 1;
			matrika[7][i] = -1;
		}
		for(int i=1; i<7; i++){
			matrika[1][i] = 1;
			matrika[6][i] = -1;
		}
		for(int i=2; i<6; i++){
			matrika[2][i] = 1;
			matrika[5][i] = -1;
		}
	}
}
