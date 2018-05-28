package uporabniskiVmesnik;

import javax.swing.SwingWorker;

import intiligenca.AlfaBeta;
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
		if(c > 40) {
			mislec = new AlfaBeta(master, 4, jaz);
		}else if(c > 15) {
			mislec = new AlfaBeta(master, 3, jaz);
		}else {
			mislec = new AlfaBeta(master, 2, jaz);
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
