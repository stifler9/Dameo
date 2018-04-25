package Dameo;

public class Stanje {
	private Polje[][] matrika;
	
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
	
	public Polje narediPotezo(final int x1,final int y1,final int x2,final int y2,final int x3,final int y3) {
		matrika[y3][x3] = matrika[y1][x1];
		Polje polje = matrika[y2][x2];
		matrika[y2][x2] = Polje.Prazno;
		matrika[y1][x1] = Polje.Prazno;
		return polje;
	}
	
	public void narediPotezo(final int x1, final int y1, final int x2, final int y2) {
		matrika[y2][x2] = matrika[y1][x1];
		matrika[y1][x1] = Polje.Prazno;
	}
	
	public void razveljaviPotezo(final int x1,final int y1,final int x2,final int y2,final int x3,final int y3, Polje pojeden) {
		matrika[y1][x1] = matrika[y3][x3];
		matrika[y2][x2] = pojeden;
		matrika[y3][x3] = Polje.Prazno;
	}
	
	public void mozjeVKralje() {
		for(int i = 0; i<8; i++){
			if(matrika[7][i] == Polje.CrniMoz){
				matrika[7][i] = Polje.CrniKralj;
			}
			if(matrika[0][i] == Polje.BelMoz){
				matrika[0][i] = Polje.BelKralj;
			}
		}
	}
	
	public Polje get(final int x, final int y){
		return matrika[y][x];
	}
}
