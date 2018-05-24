package uporabniskiVmesnik;

import logika.Lokacija;

public abstract class Strateg {
	
	protected abstract void naPotezi();
	
	protected abstract void prekini();
	
	protected abstract void klik(Lokacija lok);

	protected abstract void zmanjsajC();

}
