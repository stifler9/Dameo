package uporabniskiVmesnik;

import logika.Lokacija;

public class Clovek extends Strateg {
	private Okno master;
	
	public Clovek(Okno master){
		this.master = master;
	}

	@Override
	protected void naPotezi() {
	}

	@Override
	protected void prekini() {
	}

	@Override
	protected void klik(Lokacija lok){
		// TODO Auto-generated method stub
		master.platno.veljavenKlik(lok);
	}
}
