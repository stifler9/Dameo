package uporabniskiVmesnik;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logika.Igra;
import logika.IgralecIgre;
import logika.Lokacija;


@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener{
	private Platno platno;
	private JLabel status;
	protected Igra dameo;
	private Strateg strategCrni;
	private Strateg strategBeli;
	
	private LinkedList<Igra> undoIgre;
	
	private JMenuItem igraClovekClovek;
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	
	//Uredi meni
	private JMenuItem razveljavi;
	
	
	public Okno() {
		super();
		setTitle("Dameo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());

		undoIgre = new LinkedList<Igra>();
		
		//menu 
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("Nova igra");
		menu_bar.add(igra_menu);
		
		JMenu uredi = new JMenu("Uredi");
		menu_bar.add(uredi);
		
		//Igra Èlovek-èlovek
		igraClovekClovek = new JMenuItem("Èlovek – èlovek");
		igra_menu.add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);
		
		//Igra Èlovek-raèunalnik
		igraClovekRacunalnik = new JMenuItem("Èlovek – raèunalnik");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		//Igra Raèunalnik-raèunalnik
		igraRacunalnikRacunalnik = new JMenuItem("Raèunalnik – raèunalnik");
		igra_menu.add(igraRacunalnikRacunalnik);
		igraRacunalnikRacunalnik.addActionListener(this);
		
		//Igra Raèunalnik-èlovek
		igraRacunalnikClovek = new JMenuItem("Raèunalnik – èlovek");
		igra_menu.add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);
		
		//Razveljavi
		razveljavi = new JMenuItem("Razveljavi");
		uredi.add(razveljavi);
		razveljavi.addActionListener(this);

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
		novaIgra(new Clovek(this),
		         new Racunalnik(this, Igralec.CRNI));
	}
	
	private void novaIgra(Strateg beli, Strateg crni){
		if(strategCrni != null){ strategCrni.prekini();}
		if(strategBeli != null){ strategBeli.prekini();}
		undoIgre.clear();

		dameo = new Igra();
		strategBeli = beli;
		strategCrni = crni;
		if(dameo.napotezi == IgralecIgre.CRNI){
			strategCrni.naPotezi();
		} else if(dameo.napotezi == IgralecIgre.BELI){
			strategBeli.naPotezi();
		}
		osveziGUI();
	}
	
	protected void dodajUndo(){
		if(undoIgre.size() > 9){
			undoIgre.removeFirst();
		}
		undoIgre.add(copyIgra());
	}
	
	private void razveljavi() {
		if(!undoIgre.isEmpty()){
			strategCrni.prekini();
			strategBeli.prekini();
			
			if(!undoIgre.get(undoIgre.size()-1).equals(dameo)){
				dameo = new Igra(undoIgre.get(undoIgre.size()-1));
				strategCrni.zmanjsajC();
				strategBeli.zmanjsajC();
			}else{
				if (undoIgre.size() > 1) {
					undoIgre.removeLast();
					dameo = new Igra(undoIgre.get(undoIgre.size() - 1));
					strategCrni.zmanjsajC();
					strategBeli.zmanjsajC();
				}
			}
		}
		osveziGUI();
	}
	
	protected void klik(Lokacija lok) {
		if (!(dameo == null)) {
			if (dameo.napotezi == IgralecIgre.CRNI) {
				strategCrni.klik(lok);
			} else if (dameo.napotezi == IgralecIgre.BELI) {
				strategBeli.klik(lok);
			} 
		} else { osveziGUI();}
	}
	
	
	public void veljavenKlik(Lokacija lok) {
		platno.veljavenKlik(lok);
	}
	
	protected boolean odigraj(Lokacija lok1, Lokacija lok2){
		boolean ali = dameo.odigraj(lok1, lok2);
		if (dameo.nujnost == null) {
			if (dameo.napotezi == IgralecIgre.CRNI) {
				strategCrni.naPotezi();
			} else if (dameo.napotezi == IgralecIgre.BELI) {
				strategBeli.naPotezi();
			} 
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
		if (e.getSource() == igraClovekRacunalnik) {
			novaIgra(new Clovek(this),
			         new Racunalnik(this, Igralec.CRNI));
		}
		if (e.getSource() == igraRacunalnikRacunalnik) {
			novaIgra(new Racunalnik(this, Igralec.BELI),
			         new Racunalnik(this, Igralec.CRNI));
		}
		if(e.getSource() == razveljavi){
			razveljavi();
		}
		if (e.getSource() == igraRacunalnikClovek) {
			novaIgra(new Racunalnik(this, Igralec.BELI),
			         new Clovek(this));
		}
		
	}

	public Igra copyIgra() {
		return new Igra(dameo);
	}

}

