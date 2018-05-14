package uporabniskiVmesnik;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logika.Igra;
import logika.Igralec;
import logika.Lokacija;


@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener{
	protected Platno platno;
	private JLabel status;
	protected Igra dameo;
	private Strateg strategCrni;
	private Strateg strategBeli;
	
	private JMenuItem igraClovekClovek;
	
	
	public Okno() {
		super();
		setTitle("Dameo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());

		//menu 
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Igra");
		menu_bar.add(igra_menu);
		
		//Igra Èlovek-èlovek
		igraClovekClovek = new JMenuItem("Èlovek – èlovek");
		igra_menu.add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);
		
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
		novaIgra(new Clovek(this), new Clovek(this));
	}
	
	private void novaIgra(Strateg beli, Strateg crni){
		if(strategCrni != null){ strategCrni.prekini();}
		if(strategBeli != null){ strategBeli.prekini();}

		dameo = new Igra();
		strategBeli = beli;
		strategCrni = crni;
		if(dameo.napotezi == Igralec.CRNI){
			strategCrni.naPotezi();
		} else if(dameo.napotezi == Igralec.BELI){
			strategBeli.naPotezi();
		}
		osveziGUI();
	}
	
	protected void klik(Lokacija lok) {
		if (!(dameo == null)) {
			if (dameo.napotezi == Igralec.CRNI) {
				strategCrni.klik(lok);
			} else if (dameo.napotezi == Igralec.BELI) {
				strategBeli.klik(lok);
			} 
		} else { osveziGUI();}
	}
	
	protected boolean odigraj(Lokacija lok1, Lokacija lok2){
		boolean ali = dameo.odigraj(lok1, lok2);
		if(dameo.napotezi == Igralec.CRNI){
			strategCrni.naPotezi();
		} else if(dameo.napotezi == Igralec.BELI){
			strategBeli.naPotezi();
		}
		return ali;
	}
	
	protected void osveziGUI(){
		if(dameo == null){
			status.setText("Igra ni v teku");
		} else{
			switch(dameo.napotezi){
			case CRNI: if(dameo.nujnost == null){status.setText("Na potezi je èrni");}else{status.setText("Èrni dela potezo...");}  break;
			case BELI: if(dameo.nujnost == null){status.setText("Na potezi je beli");}else{status.setText("Beli dela potezo...");}  break;
			case ZMAGACRNI: status.setText("Zmagal je èrni"); break;
			case ZMAGABELI: status.setText("Zmagal je beli"); break;

			}
		}
		platno.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == igraClovekClovek) {
			novaIgra(new Clovek(this),
			         new Clovek(this));
		}
		
	}

}

