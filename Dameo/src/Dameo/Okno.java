package Dameo;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Okno extends JFrame{
	public Platno platno;
	
	public Okno() {
		super();
		setTitle("Dameo");
		platno = new Platno();
		this.add(this.platno);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
