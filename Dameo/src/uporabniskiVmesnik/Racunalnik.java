package uporabniskiVmesnik;

import javax.swing.SwingWorker;

import intiligenca.Minimax;
import logika.Lokacija;
import logika.Poteza;

public class Racunalnik extends Strateg{
	private Okno master;
	private Igralec jaz;
	private SwingWorker<Poteza,Lokacija> mislec;
	private int c;
	
	
	public Racunalnik(Okno master, Igralec jaz){
		this.master = master;
		this.jaz = jaz;
		c = 1;
	}
	
	@Override
	protected void naPotezi() {
		c++;
		if(c > 35) {
			mislec = new Minimax(master, 3, jaz);
		}else if(c > 10) {
			mislec = new Minimax(master, 2, jaz);
		}else {
			mislec = new Minimax(master, 1, jaz);
		}
		mislec.execute();
	}

	@Override
	protected void prekini() {
		if (mislec != null) {
			mislec.cancel(true);
		}
	}

	@Override
	protected void klik(Lokacija lok) {}

	@Override
	protected void zmanjsajC() {c--;}

}
