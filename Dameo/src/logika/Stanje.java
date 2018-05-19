package logika;

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
	
	public Stanje(Stanje stanje) {
		matrika = new Polje[8][8];
		for(int i=0; i<8;i++) {
			for(int j=0; j<8;j++) {
				matrika[i][j] = stanje.matrika[i][j];
			}
		}
	}
	
	private static int abs(int x){
		if(x>=0){
			return x;
		}else{return -x;}
	}
	
	protected Polje narediSkok(int x1,int y1,int x2,int y2) {
		
		int dolzina;
		if(x2 - x1 == 0){
			dolzina = abs(y2 - y1);
		}else{dolzina = abs(x2 - x1);}
		
		int[] smer = {(x2 - x1)/dolzina, (y2 - y1)/dolzina};
		
		matrika[y2][x2] = matrika[y1][x1];
		Polje polje = matrika[y2 - smer[1]][x2 - smer[0]];
		matrika[y2 - smer[1]][x2 - smer[0]] = Polje.Prazno;
		matrika[y1][x1] = Polje.Prazno;
		return polje;
	}
	
	protected void narediEnostavno(int x1, int y1, int x2, int y2) {
		matrika[y2][x2] = matrika[y1][x1];
		matrika[y1][x1] = Polje.Prazno;
	}
	
	protected void razveljaviSkok(int x1,int y1,int x2,int y2, Polje pojeden) {
		int dolzina;
		if(x2 - x1 == 0){
			dolzina = abs(y2 - y1);
		}else{dolzina = abs(x2 - x1);}
		
		int[] smer = {(x2 - x1)/dolzina, (y2 - y1)/dolzina};
		
		matrika[y1][x1] = matrika[y2][x2];
		matrika[y2 - smer[1]][x2 - smer[0]] = pojeden;
		matrika[y2][x2] = Polje.Prazno;
	}
	
	protected void mozjeVKralje() {
		for(int i = 0; i<8; i++){
			if(matrika[7][i] == Polje.CrniMoz){
				matrika[7][i] = Polje.CrniKralj;
			}
			if(matrika[0][i] == Polje.BelMoz){
				matrika[0][i] = Polje.BelKralj;
			}
		}
	}
	
	public Polje get(int x, int y) throws ArrayIndexOutOfBoundsException{
		return matrika[y][x];
	}
}
