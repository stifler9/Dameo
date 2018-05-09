package uporabniskiVmesnik;

import logika.Lokacija;

public class Clovek extends Strateg {
	private Okno master;
	
	public Clovek(Okno master){
		this.master = master;
	}

	@Override
	public void naPotezi() {
	}

	@Override
	public void prekini() {
	}

	@Override
	public void skok(Lokacija lok1, Lokacija lok2) {
		// TODO Auto-generated method stub
		master.odigraj(lok1, lok2);
		prekini();
	}
}
