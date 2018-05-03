package test;

import static org.junit.Assert.*;

import org.junit.Test;

import logika.*;

public class JUnitTesti {

	@Test
	public void test() {
		Igra dameo = new Igra();
		Lokacija lok1 = new Lokacija(7,7);
		Lokacija lok2 = new Lokacija(4,4);
		dameo.odigraj(lok1, lok2);
		assertEquals(dameo.stanje.get(4, 4), Polje.BelMoz); 
		
		Lokacija lok3 = new Lokacija(4,0);
		Lokacija lok4 = new Lokacija(1,3);
		dameo.odigraj(lok3, lok4);
		assert(dameo.stanje.get(4, 0) == Polje.Prazno); 

		Lokacija lok5 = new Lokacija(4,7);
		Lokacija lok6 = new Lokacija(1,4);
		dameo.odigraj(lok5, lok6);
		
		//preverimo, ce je dana poteza med možnimi
		Lokacija lok7 = new Lokacija(1,3);
		Lokacija lok8 = new Lokacija(1,5);
		
		boolean ali = false;
		for(Poteza poteza: dameo.moznePoteze){
			if(poteza.naPrvemMestu(lok7) && poteza.naDrugemMestu(lok8)){
				ali = true;
			}
		}
		if(!ali){
			fail("ta poteza bi morala biti med moznimi");
		}
		

		dameo.odigraj(lok7, lok8);
		//preverimo, ce po tem, ce odigra napacno potezo, še zmeraj ostane isti na potezi
		Igralec igralec = dameo.napotezi;
		
		Lokacija lok9 = new Lokacija(6,6);
		Lokacija lok10 = new Lokacija(3,3);
		//To se nebi smelo odigrati
		assert(!dameo.odigraj(lok9, lok10));
		assert(dameo.napotezi == igralec);
	}

}
