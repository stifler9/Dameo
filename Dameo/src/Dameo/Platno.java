package Dameo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.LinkedList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener{
	public Igra dameo;
	public Lokacija izbranaFigura;
	public LinkedList<Poteza> obarvanePoteze;
	
	public Platno(){
		super();
		this.setBackground(Color.white);
		dameo = new Igra();
		izbranaFigura = null;
		obarvanePoteze = new LinkedList<Poteza>();
		addMouseListener(this);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		for(int i = 1; i<=7; i++) {
			g.drawLine(i*64, 0, i*64, 512);
			g.drawLine(0, i*64, 512, i*64);
		}
		for(int i=0;i<8;i++) {
			for(int j=0;j<8;j++) {
				if(dameo.stanje.matrika[j][i] == Polje.CrniMoz) {
					g.fillOval(i*64+2, j*64+2, 60, 60);
				}else if(dameo.stanje.matrika[j][i] == Polje.BelMoz) {
					g.drawOval(i*64+2, j*64+2, 60, 60);
				}else if(dameo.stanje.matrika[j][i] == Polje.CrniKralj) {
					g.fillOval(i*64+2, j*64+2, 60, 60);
					g.setColor(Color.ORANGE);
					g.fillOval(i*64+16, j*64+16, 32, 32);
					g.setColor(Color.black);
				}else if(dameo.stanje.matrika[j][i] == Polje.BelKralj) {
					g.drawOval(i*64+2, j*64+2, 60, 60);
					g.setColor(Color.ORANGE);
					g.fillOval(i*64+16, j*64+16, 32, 32);
					g.setColor(Color.black);
				}
			}
		}
		g.setColor(Color.GREEN);
		for(Poteza pot: obarvanePoteze) {
			for(Lokacija lok: pot.sestavljena) {
				g.fillOval(lok.x*64 + 24, lok.y*64 + 24, 16, 16);
			}
		}
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(512, 512);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("kliknil sem");
		Lokacija lokacija = new Lokacija(e.getX()/64, e.getY()/64);
		if(izbranaFigura == null) {
			boolean dodaj = false;
			for(Poteza pot: dameo.moznePoteze) {
				if(pot.sestavljena.get(0).equals(lokacija)) {
					obarvanePoteze.add(pot);
					dodaj = true;
				}
			}
			if(dodaj) {izbranaFigura = lokacija;}
		}else {
			if(lokacija.equals(izbranaFigura)) {
				obarvanePoteze.clear();
				izbranaFigura = null;
			}else {
				for(Poteza pot: obarvanePoteze) {
					if(pot.sestavljena.get(1).equals(lokacija)) {
						dameo.Odigraj(izbranaFigura, lokacija);
						obarvanePoteze.clear();
						if(dameo.nujnost == null) {
							izbranaFigura = null;
						}else {
							izbranaFigura = dameo.nujnost;
							obarvanePoteze.addAll(dameo.moznePoteze);
						}
					}
				}
			}
		}
		repaint();
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