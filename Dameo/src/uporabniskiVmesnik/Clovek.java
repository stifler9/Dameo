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
	public void klik(Lokacija lok){
		// TODO Auto-generated method stub
		master.platno.veljavenKlik(lok);
	}
}
