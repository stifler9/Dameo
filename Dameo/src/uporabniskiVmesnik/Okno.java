package uporabniskiVmesnik;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import logika.Igra;
import logika.Igralec;
import logika.Lokacija;


@SuppressWarnings("serial")
public class Okno extends JFrame{
	private Platno platno;
	private JLabel status;
	protected Igra dameo;
	protected Strateg strategCrni;
	protected Strateg strategBeli;

	
	public Okno() {
		super();
		setTitle("Dameo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		dameo = null;

		//menu 
		//...
		
		//platno
		platno = new Platno(this);
		GridBagConstraints platnoLayout = new GridBagConstraints();
		platnoLayout.gridx = 0;
		platnoLayout.gridy = 0;
		platnoLayout.weightx = 1;
		platnoLayout.weighty = 1;
		platnoLayout.fill = GridBagConstraints.BOTH;
		getContentPane().add(platno, platnoLayout);
		
		//statusna vrstica
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(), status.getFont().getStyle(), 20));
		GridBagConstraints statusLayout = new GridBagConstraints();
		
		statusLayout.gridx = 0;
		statusLayout.gridy = 1;
		statusLayout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, statusLayout);
		
		//zaènemo novo igro
		novaIgra();
	}
	
	public void novaIgra(){
		if(strategCrni != null){ strategCrni.prekini();}
		if(strategBeli != null){ strategBeli.prekini();}

		dameo = new Igra();
		strategCrni = new Clovek(this);
		strategBeli = new Clovek(this);
		if(dameo.napotezi == Igralec.CRNI){
			strategCrni.naPotezi();
		} else if(dameo.napotezi == Igralec.BELI){
			strategBeli.naPotezi();
		}
		osveziGUI();
		
	}
	
	public boolean odigraj(Lokacija lok1, Lokacija lok2){
		boolean ali = dameo.odigraj(lok1, lok2);
		if(dameo.napotezi == Igralec.CRNI){
			strategCrni.naPotezi();
		} else if(dameo.napotezi == Igralec.BELI){
			strategBeli.naPotezi();
		}
		return ali;
	}
	
	public void osveziGUI(){
		if(dameo == null){
			status.setText("Igra ni v teku");
		} else{
			switch(dameo.napotezi){
			case CRNI: status.setText("Na potezi je èrni"); break;
			case BELI: status.setText("Na potezi je beli"); break;
			case ZMAGACRNI: status.setText("Zmagal je èrni"); break;
			case ZMAGABELI: status.setText("Zmagal je beli"); break;

			}
		}
		platno.repaint();
	}

}

