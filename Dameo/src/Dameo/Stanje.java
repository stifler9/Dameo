package Dameo;

public class Stanje {
	public Polje[][] matrika;
	
	public Stanje(){
		matrika = new Polje[8][8];
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++){
				matrika[j][i] = Polje.Prazno;
			}
		}
		for(int i=0; i<8; i++){
			matrika[0][i] = Polje.CrniMoz;
			matrika[7][i] = Polje.BelMoz;
		}
		for(int i=1; i<7; i++){
			matrika[1][i] = Polje.CrniMoz;
			matrika[6][i] = Polje.BelMoz;
		}
		for(int i=2; i<6; i++){
			matrika[2][i] = Polje.CrniMoz;
			matrika[5][i] = Polje.BelMoz;
		}
	}
	
}
