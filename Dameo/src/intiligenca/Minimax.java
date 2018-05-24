package intiligenca;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import logika.Igra;
import logika.IgralecIgre;
import logika.Lokacija;
import logika.Poteza;
import uporabniskiVmesnik.Igralec;
import uporabniskiVmesnik.Okno;

public class Minimax extends SwingWorker<Poteza, Lokacija>{
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
		
		LinkedList<Poteza> najboljse = new LinkedList<Poteza>();
		int ocena = Ocena.PORAZ;
		
		//Pogledamo ocene po odigranih moznih potezah,
		// najboljsa poteza je tista, ki da najboljso oceno
		for(Poteza poteza: trenutna.generirajPoteze()) {
			Igra poTejPotezi = new Igra(trenutna);
			poTejPotezi.odigrajPotezo(poteza);
			int novaOcena;
			if (poteza.enostavnost) {
				novaOcena = pomnozi * minimaksBelega(poTejPotezi, 0);
			}else {
				novaOcena = pomnozi * minimaksBelega(poTejPotezi, -1);
			}
			if(novaOcena > ocena) {
				ocena = novaOcena;
				najboljse.clear();
				najboljse.add(poteza);
			}else if(novaOcena == ocena){
				najboljse.add(poteza);
			}
		}
		
		assert(!najboljse.isEmpty());
		Random x = new Random();
		
		// po kosih narisemo potezo
		Poteza p = najboljse.get(x.nextInt(najboljse.size()));
		for(int i = 0; i < p.size(); i++){
			Thread.sleep(600);
			this.publish(p.get(i));
		}
		
		return najboljse.get(x.nextInt(najboljse.size()));
	}
	
	@Override
	public void done() {
		
	}
	
	@Override
	protected void process(List<Lokacija> chunks) {
		super.process(chunks);
		for(Lokacija lok: chunks){
			master.veljavenKlik(lok);
		}
	}


	private int minimaksBelega(Igra igra, int g) {
		if(igra.napotezi == IgralecIgre.ZMAGABELI) {
			return Ocena.ZMAGA;
		}
		if(igra.napotezi == IgralecIgre.ZMAGACRNI) {
			return Ocena.PORAZ;
		}
		if(g >= globina) {
			
			// TrdaOcena naj bo za boljšo oceno narejena, ko se ve, da so naslednje poteze mozne enostavne.
			LinkedList<Poteza> moznosti = igra.generirajPoteze();
			if (moznosti.get(0).enostavnost) {
				return Ocena.trdaOcena(igra.stanje);
			}else {
				if(igra.napotezi == IgralecIgre.BELI) {
					int ocena = Ocena.PORAZ;
					for(Poteza poteza: moznosti) {
						Igra naslednja = new Igra(igra);
						naslednja.odigrajPotezo(poteza);
						
						int novaocena = minimaksBelega(naslednja, g);
						if(novaocena > ocena) {
							ocena = novaocena;
						}
					}
					return ocena;
				}else{
					int ocena = Ocena.ZMAGA;
					for(Poteza poteza: moznosti) {
						Igra naslednja = new Igra(igra);
						naslednja.odigrajPotezo(poteza);
						
						int novaocena = minimaksBelega(naslednja, g);
						if(novaocena < ocena) {
							ocena = novaocena;
						}
					}
					return ocena;
				}
			}
		}else {
			if(igra.napotezi == IgralecIgre.BELI) {
				int ocena = Ocena.PORAZ;
				for(Poteza poteza: igra.generirajPoteze()) {
					Igra naslednja = new Igra(igra);
					naslednja.odigrajPotezo(poteza);
					int novaocena;
					
					/*
					 * Enostavnih potez je vec, zato pri enostavnih povecujemo g, ki gre do globine,
					 * pri potezah s skoki, pa globine ne povecamo.
					 */
					if(poteza.enostavnost) {
						novaocena = minimaksBelega(naslednja, g+1);
					}else {
						novaocena = minimaksBelega(naslednja, g);
					}
					if(novaocena > ocena) {
						ocena = novaocena;
					}
				}
				return ocena;
			}else{
				int ocena = Ocena.ZMAGA;
				for(Poteza poteza: igra.generirajPoteze()) {
					Igra naslednja = new Igra(igra);
					naslednja.odigrajPotezo(poteza);
					int novaocena;
					
					/*
					 * Enostavnih potez je vec, zato pri enostavnih povecujemo g, ki gre do globine,
					 * pri potezah s skoki, pa globine ne povecamo.
					 */
					if(poteza.enostavnost) {
						novaocena = minimaksBelega(naslednja, g+1);
					}else {
						novaocena = minimaksBelega(naslednja, g);
					}
					if(novaocena < ocena) {
						ocena = novaocena;
					}
				}
				return ocena;
			}
		}
	}

}
