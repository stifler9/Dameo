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
		
		//Ce smo sploh poklicali misleca, je nekdo na vrsti (beli ali crni)
		// Ocena je za belega, za èrnega je - ocena belega.
		int pomnozi = -1; // Ce je na vrsti crni
		if(jaz == Igralec.BELI) {pomnozi = 1;}
		
		Poteza najboljsa = null;
		double ocena = Ocena.PORAZ;
		
		//Pogledamo ocene po odigranih moznih potezah,
		// najboljsa poteza je tista, ki da najboljso oceno
		for(Poteza poteza: trenutna.generirajPoteze()) {
			Igra poTejPotezi = trenutna.clone();
			poTejPotezi.odigrajPotezo(poteza);
			double novaOcena = pomnozi*minimaksBelega(poTejPotezi, 0);
			if(novaOcena >= ocena) {
				ocena = novaOcena;
				najboljsa = poteza;
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
				double ocena = Ocena.PORAZ;
				for(Poteza poteza: igra.generirajPoteze()) {
					Igra naslednja = igra.clone();
					naslednja.odigrajPotezo(poteza);
					double novaocena = minimaksBelega(naslednja, g+1);
					if(novaocena > ocena) {
						ocena = novaocena;
					}
				}
				return ocena;
			}else if(igra.napotezi == Igralec.CRNI) {
				double ocena = Ocena.ZMAGA;
				for(Poteza poteza: igra.generirajPoteze()) {
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
