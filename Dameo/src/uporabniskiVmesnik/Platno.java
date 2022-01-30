package uporabniskiVmesnik;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.LinkedList;

import javax.swing.JPanel;

import logika.Lokacija;
import logika.Polje;
import logika.Poteza;

@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener{
	private Lokacija izbranaFigura;
	private LinkedList<Poteza> obarvanePoteze;
	private LinkedList<Lokacija> pokazaneFigure;
	private Okno master;
	
	public Platno(Okno master){
		super();
		this.setBackground(Color.white);
		izbranaFigura = null;
		obarvanePoteze = new LinkedList<Poteza>();
		pokazaneFigure = new LinkedList<Lokacija>();
		addMouseListener(this);
		this.master = master;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(512, 512);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!(master.dameo == null)) {
			final int sirina = getBounds().width/8;
			final int visina = getBounds().height/8;
			
			// Narisemo igralno plosco
			g.setColor(new Color(154, 97, 76));
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					g.fillRect(i * 2*sirina, visina + j * 2*visina, sirina, visina);
					g.fillRect(sirina + i * 2*sirina, 2*visina * j, sirina, visina);
				}
			}
			g.setColor(new Color(201, 184, 162));
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					g.fillRect(i * 2*sirina, j * 2*visina, sirina, visina);
					g.fillRect(sirina + i * 2*sirina, visina + j * 2*visina, sirina, visina);
				}
			}
			
			// Narisemo figure
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (master.dameo.stanje.get(i, j) == Polje.CrniMoz) {
						g.setColor(Color.black);
						g.fillOval(i * sirina + sirina/32, j * visina + visina/32, sirina - 2*(sirina/32), visina - 2*(visina/32));
					} else if (master.dameo.stanje.get(i, j) == Polje.BelMoz) {
						g.setColor(Color.WHITE);
						g.fillOval(i * sirina + sirina/32, j * visina + visina/32, sirina - 2*(sirina/32), visina - 2*(visina/32));
					} else if (master.dameo.stanje.get(i, j) == Polje.CrniKralj) {
						g.setColor(Color.black);
						g.fillOval(i * sirina + sirina/32, j * visina + visina/32, sirina - 2*(sirina/32), visina - 2*(visina/32));
						g.setColor(new Color(197, 180, 48));
						g.fillOval(i * sirina + sirina/8, j * visina + visina/8, sirina - 2*(sirina/8), visina - 2*(visina/8));
						g.setColor(Color.black);
						g.fillOval(i * sirina + sirina/6, j * visina + visina/6, sirina - 2*(sirina/6), visina - 2*(visina/6));
					} else if (master.dameo.stanje.get(i, j) == Polje.BelKralj) {
						g.setColor(Color.WHITE);
						g.fillOval(i * sirina + sirina/32, j * visina + visina/32, sirina - 2*(sirina/32), visina - 2*(visina/32));
						g.setColor(new Color(197, 180, 48));
						g.fillOval(i * sirina + sirina/8, j * visina + visina/8, sirina - 2*(sirina/8), visina - 2*(visina/8));
						g.setColor(Color.WHITE);
						g.fillOval(i * sirina + sirina/6, j * visina + visina/6, sirina - 2*(sirina/6), visina - 2*(visina/6));
					}
				}
			}
			// Obarvamo poteze
			g.setColor(Color.GREEN);
			for (Poteza pot : obarvanePoteze) {
				int[] xi = new int[pot.size()];
				int[] yi = new int[pot.size()];
				int n = 0;
				for (int i = 0; i < pot.size(); i++) {
					Lokacija lok = pot.get(i);
					g.fillOval(lok.getX() * sirina + 3*sirina/8, lok.getY() * visina + 3*visina/8, sirina - 6*(sirina/8), visina - 6*(visina/8));
					xi[n] = lok.getX() * sirina + sirina/2;
					yi[n] = lok.getY() * visina + visina/2;
					n++;
				}
				g.drawPolyline(xi, yi, n);
			}
			// Obarvamo izbrano figuro
			if (!(izbranaFigura == null)) {
				g.setColor(new Color(17, 140, 183));
				g.fillOval(izbranaFigura.getX() * sirina + 3*sirina/8, izbranaFigura.getY() * visina + 3*visina/8, sirina - 6*(sirina/8), visina - 6*(visina/8));
			} else {
				// Obarvamo pokazane mozne figure
				g.setColor(new Color(235, 34, 244));
				for(Lokacija lok: pokazaneFigure) {
					g.fillOval(lok.getX() * sirina + 3*sirina/8, lok.getY() * visina + 3*visina/8, sirina - 6*(sirina/8), visina - 6*(visina/8));
				}
			}
		}
	}
	
	protected void izbrisiOznacitve(){
		obarvanePoteze.clear();
		pokazaneFigure.clear();
		izbranaFigura = null;
	}

	protected void pokaziMoznePoteze() {
		izbrisiOznacitve();
		for(Poteza pot: master.dameo.generirajPoteze()) {
			Lokacija lok = pot.get(0);
			if(!pokazaneFigure.contains(lok)) {
				pokazaneFigure.add(lok);
			}
		}
		repaint();
	}
	
	protected void veljavenKlik(Lokacija lokacija) {
		boolean dodaj = false;
		if(izbranaFigura == null) {
			for(Poteza pot: master.dameo.generirajPoteze()) {
				if(pot.naPrvemMestu(lokacija)) {
					obarvanePoteze.add(pot);
					dodaj = true;
				}
			}
			if(dodaj){
				izbranaFigura = lokacija;
				pokazaneFigure.clear();
			}
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
						
						obarvanePoteze.clear();
						if(master.dameo.nujnost == null) {
							izbranaFigura = null;
						}else {
							izbranaFigura = master.dameo.nujnost;
							for(Poteza poteza: master.dameo.generirajPoteze()) {
								obarvanePoteze.add(poteza);
							}
						}
						master.osveziGUI();
						return;
					}
				}
				// Ce izberem katero drugo figuro za igranje
				for(Poteza pot: master.dameo.generirajPoteze()) {
					if(pot.naPrvemMestu(lokacija)) {
						if(!dodaj) {
							dodaj = true;
							obarvanePoteze.clear();
						}
						obarvanePoteze.add(pot);
					}
				}
				if(dodaj){izbranaFigura = lokacija;}
			}
		}
		master.osveziGUI();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		master.klik(new Lokacija(e.getX()/(getBounds().width/8), e.getY()/(getBounds().height/8)));
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
