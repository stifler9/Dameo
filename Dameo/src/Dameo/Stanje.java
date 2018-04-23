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
	
	public Polje narediPotezo(int x1,int y1,int x2,int y2,int x3,int y3) {
		matrika[y3][x3] = matrika[y1][x1];
		Polje polje = matrika[y2][x2];
		matrika[y2][x2] = Polje.Prazno;
		matrika[y1][x1] = Polje.Prazno;
		return polje;
	}
	
	public void narediPotezo(int x1, int y1, int x2, int y2) {
		matrika[y2][x2] = matrika[y1][x1];
		matrika[y1][x1] = Polje.Prazno;
	}
	
	public void razveljaviPotezo(int x1,int y1,int x2,int y2,int x3,int y3, Polje pojeden) {
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
	
	public Polje get(int x, int y){
		return matrika[y][x];
	}
}
