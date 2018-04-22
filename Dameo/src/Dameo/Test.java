package Dameo;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		Igra dameo = new Igra();
		dameo.moznePoteze = dameo.generirajPoteze();
		for(Poteza pot: dameo.moznePoteze){
			System.out.println(" ");
			for(Lokacija lok: pot.sestavljena){
				System.out.print(" " + lok.x + "," + lok.y);
			}
		}
		System.out.println("na novo");
		
		dameo.stanje.matrika = dameo.praznoPolje();
		dameo.stanje.matrika[0][0] = Polje.CrniMoz;
		dameo.stanje.matrika[0][1] = Polje.CrniMoz;
		dameo.stanje.matrika[0][2] = Polje.CrniMoz;
		dameo.stanje.matrika[1][0] = Polje.CrniMoz;
		dameo.stanje.matrika[1][2] = Polje.CrniMoz;
		dameo.stanje.matrika[1][3] = Polje.CrniMoz;
		dameo.stanje.matrika[1][1] = Polje.BelMoz;
		dameo.stanje.matrika[3][1] = Polje.BelMoz;
		
		dameo.napotezi = Igralec.I2;
		dameo.moznePoteze = dameo.generirajPoteze();
		for(Poteza pot: dameo.moznePoteze){
			System.out.println(" ");
			for(Lokacija lok: pot.sestavljena){
				System.out.print(" " + lok.x + "," + lok.y);
			}
		}
		
		Lokacija lok1 = new Lokacija(1,0);
		Lokacija lok2 = new Lokacija(1,2);
		
		dameo.Odigraj(lok1, lok2);
		
		System.out.println(dameo.nujnost.x + "," + dameo.nujnost.y);
		for(Poteza pot: dameo.moznePoteze){
			System.out.println(" ");
			for(Lokacija lok: pot.sestavljena){
				System.out.print(" " + lok.x + "," + lok.y);
			}
		}
		
		Lokacija lok3 = new Lokacija(1,4);
		dameo.Odigraj(lok2, lok3);
		
		System.out.println("na novo!  ");
		dameo.stanje.matrika = dameo.praznoPolje();
		dameo.stanje.matrika[1][1] = Polje.CrniMoz;
		dameo.stanje.matrika[0][3] = Polje.CrniMoz;
		dameo.stanje.matrika[2][0] = Polje.BelKralj;
		dameo.napotezi = Igralec.I1;
		dameo.moznePoteze = dameo.generirajPoteze();
		for(Poteza pot: dameo.moznePoteze){
			System.out.println(" ");
			for(Lokacija lok: pot.sestavljena){
				System.out.print(" " + lok.x + "," + lok.y);
			}
		}
		
		Lokacija lok4 = new Lokacija(0,2);
		Lokacija lok5 = new Lokacija(2,0);
		
		
		dameo.Odigraj(lok4, lok5);
		
		for(Poteza pot: dameo.moznePoteze){
			System.out.println(" ");
			for(Lokacija lok: pot.sestavljena){
				System.out.print(" " + lok.x + "," + lok.y);
			}
		}
		
		Lokacija lok6 = new Lokacija(3,0);
		Lokacija lok7 = new Lokacija(1,0);
		dameo.Odigraj(lok6, lok7);
		*/
		Okno glavnoOkno = new Okno();
		glavnoOkno.pack();
		glavnoOkno.setVisible(true);
		
	}

}
