package uporabniskiVmesnik;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.LinkedList;

import javax.swing.JPanel;

import logika.Igralec;
import logika.Lokacija;
import logika.Polje;
import logika.Poteza;

@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener{
	private Lokacija izbranaFigura;
	private LinkedList<Poteza> obarvanePoteze;
	private Okno master;
	
	public Platno(Okno master){
		super();
		this.setBackground(Color.white);
		izbranaFigura = null;
		obarvanePoteze = new LinkedList<Poteza>();
		addMouseListener(this);
		this.master = master;
	}
	

	public Igralec napotezi(){
		if(master.dameo != null){
			return master.dameo.napotezi;
		}
		return null;
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		for(int i = 0; i<4; i++) {
			for(int j = 0; j<4; j++) {
				g.fillRect(i*128, 64 + j*128, 64, 64);
				g.fillRect(64 + i*128, j*128, 64, 64);
			}
		}
		g.setColor(Color.black);
		for(int i = 1; i<=8; i++) {
			g.drawLine(i*64, 0, i*64, 512);
			g.drawLine(0, i*64, 512, i*64);
		}
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(master.dameo.stanje.get(i,j) == Polje.CrniMoz) {
					g.fillOval(i*64+2, j*64+2, 60, 60);
				}else if(master.dameo.stanje.get(i,j) == Polje.BelMoz) {
					g.setColor(Color.WHITE);
					g.fillOval(i*64+3, j*64+3, 58, 58);
					g.setColor(Color.black);
					g.drawOval(i*64+2, j*64+2, 60, 60);
				}else if(master.dameo.stanje.get(i,j) == Polje.CrniKralj) {
					g.fillOval(i*64+2, j*64+2, 60, 60);
					g.setColor(Color.ORANGE);
					g.fillOval(i*64+16, j*64+16, 32, 32);
					g.setColor(Color.black);
				}else if(master.dameo.stanje.get(i,j) == Polje.BelKralj) {
					g.setColor(Color.WHITE);
					g.fillOval(i*64+3, j*64+3, 58, 58);
					g.setColor(Color.ORANGE);
					g.fillOval(i*64+16, j*64+16, 32, 32);
					g.setColor(Color.black);
					g.drawOval(i*64+2, j*64+2, 60, 60);
				}
			}
		}
		g.setColor(Color.GREEN);
		for(Poteza pot: obarvanePoteze) {
			int[] xi = new int[pot.size()];
			int[] yi = new int[pot.size()];
			int n = 0;
			for(int i=0; i<pot.size();i++) {
				Lokacija lok = pot.get(i);
				g.fillOval(lok.getX()*64 + 24, lok.getY()*64 + 24, 16, 16);
				xi[n] = lok.getX()*64 + 32;
				yi[n] = lok.getY()*64 + 32;
				n++;
			}
			g.drawPolyline(xi, yi, n);
		}
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(512, 512);
	}
	
	public void mouseClicked(MouseEvent e) {
		Lokacija lokacija = new Lokacija(e.getX()/64, e.getY()/64);
		if(napotezi() == Igralec.BELI){
			master.strategBeli.klik(lokacija);
		}else if(napotezi() == Igralec.CRNI){
			master.strategCrni.klik(lokacija);
		}
	}
	

	public void veljavenKlik(Lokacija lokacija) {
		// TODO Auto-generated method stub
		if(izbranaFigura == null) {
			boolean dodaj = false;
			for(Poteza pot: master.dameo.generirajPoteze()) {
				if(pot.naPrvemMestu(lokacija)) {
					obarvanePoteze.add(pot);
					dodaj = true;
				}
			}
			if(dodaj) {izbranaFigura = lokacija;}
		}else {
			if(lokacija.equals(izbranaFigura)) {
				if(master.dameo.nujnost == null) {
					obarvanePoteze.clear();
					izbranaFigura = null;
				}
			}else {
				for(Poteza pot: obarvanePoteze){
					if(pot.naDrugemMestu(lokacija)) {
						master.odigraj(izbranaFigura, lokacija);
							
						if(napotezi() == Igralec.CRNI){
							master.strategCrni.naPotezi();
						} else if(napotezi() == Igralec.BELI){
							master.strategBeli.naPotezi();
						}
						obarvanePoteze.clear();
						if(master.dameo.nujnost == null) {
							izbranaFigura = null;
						}else {
							izbranaFigura = master.dameo.nujnost;
							for(Poteza poteza: master.dameo.generirajPoteze()) {
								obarvanePoteze.add(poteza);
							}
						}
						break;
					}
				}
			}
		}
		master.osveziGUI();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
