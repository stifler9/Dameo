package uporabniskiVmesnik;

import logika.Lokacija;

public abstract class Strateg {
	
	public abstract void naPotezi();
	
	public abstract void prekini();
	
	public abstract void skok(Lokacija lok1, Lokacija lok2);

}
