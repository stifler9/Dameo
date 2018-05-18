package intiligenca;

import java.util.LinkedList;

import javax.swing.SwingWorker;

import logika.Igra;
import logika.Igralec;
import logika.Poteza;
import uporabniskiVmesnik.Okno;

public class Minimax extends SwingWorker<Poteza, Object>{
	private Okno master;
	private int globina;
	private Igralec jaz;

	public Minimax(Okno master, int globina, Igralec jaz) {
		this.master = master;
		this.globina = globina;
		this.jaz = jaz;
	}
	
	@Override
	protected Poteza doInBackground() throws Exception {
		Igra trenutna = master.copyIgra();
		
		//Naredimo listo potez in iger, ki sledijo tem potezam
		LinkedList<Poteza> moznePoteze = trenutna.generirajPoteze();
		Igra[] igrePotez = new Igra[moznePoteze.size()];
		for(int i = 0; i < moznePoteze.size(); i++) {
			Igra dodana = trenutna.clone();
			dodana.odigrajPotezo(moznePoteze.get(i));
			igrePotez[i] = dodana;
		}
		
		//Naraèunamo ocene za igrePotez
		double[] ocene = new double[moznePoteze.size()];
		
		//Ce smo sploh poklicali misleca, je nekdo na vrsti
		int pomnozi = -1;
		if(jaz == Igralec.BELI) {pomnozi = 1;}
		
		for(int i = 0; i < moznePoteze.size(); i++) {
			ocene[i] = pomnozi*minimaksBelega(igrePotez[i], 0);
		}
		
		//Pogledamo, katera ocena nam je najboljsa in vrnemo to potezo
		Poteza najboljsa = null;
		double ocena = Ocena.PORAZ;
		for(int i = 0; i < moznePoteze.size(); i++) {
			if(ocene[i] > ocena) {
				najboljsa = moznePoteze.get(i);
			}
		}
		
		return najboljsa;
	}
	
	@Override
	public void done() {
		try {
			Poteza p = this.get();
			if (p != null) { 
				for(int i = 0; i < p.size(); i++) {
					Thread.sleep(1000);
					master.veljavenKlik(p.get(i));
				}
			}
		} catch (Exception e) {
		}
	}
	
	private double minimaksBelega(Igra igra, int g) {
		if(igra.napotezi == Igralec.ZMAGABELI) {
			return Ocena.ZMAGA;
		}
		if(igra.napotezi == Igralec.ZMAGACRNI) {
			return Ocena.PORAZ;
		}
		if(g >= globina) {
			return Ocena.trdaOcena(igra);
		}else {
			if(igra.napotezi == Igralec.BELI) {
				LinkedList<Poteza> moznePoteze = igra.generirajPoteze();
				double ocena = Ocena.PORAZ;
				for(Poteza poteza: moznePoteze) {
					Igra naslednja = igra.clone();
					naslednja.odigrajPotezo(poteza);
					double novaocena = minimaksBelega(naslednja, g+1);
					if(novaocena > ocena) {
						ocena = novaocena;
					}
				}
				return ocena;
			}else if(igra.napotezi == Igralec.CRNI) {
				LinkedList<Poteza> moznePoteze = igra.generirajPoteze();
				double ocena = Ocena.ZMAGA;
				for(Poteza poteza: moznePoteze) {
					Igra naslednja = igra.clone();
					naslednja.odigrajPotezo(poteza);
					double novaocena = minimaksBelega(naslednja, g+1);
					if(novaocena < ocena) {
						ocena = novaocena;
					}
				}
				return ocena;
			}
		}
	}

}
