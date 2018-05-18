package uporabniskiVmesnik;

import javax.swing.SwingWorker;

import intiligenca.Minimax;
import logika.Igralec;
import logika.Lokacija;
import logika.Poteza;

public class Racunalnik extends Strateg{
	private Okno master;
	private Igralec jaz;
	private SwingWorker<Poteza,Object> mislec;
	
	
	public Racunalnik(Okno master, Igralec jaz){
		this.master = master;
		this.jaz = jaz;
	}
	
	@Override
	protected void naPotezi() {
		mislec = new Minimax(master, 4, jaz);
		mislec.execute();
	}

	@Override
	protected void prekini() {
		if (mislec != null) {
			mislec.cancel(true);
		}
	}

	@Override
	protected void klik(Lokacija lok) {
	}

}
