package GUI;
import javax.swing.*;

import IrProtocol.ApplicationLayer;

//http://www.particle.kth.se/~lindsey/JavaCourse/Book/Part1/Java/Chapter12/catchingKeystrokes.html

import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;
import java.util.TooManyListenersException;

@SuppressWarnings("serial")

public class SWsimulation extends JFrame {

	public SWsimulation() throws TooManyListenersException {
		
		final TaxiModuleGUI tmGUI = new TaxiModuleGUI();
		
		JPanel canvas = new JPanel ();
		add (canvas, "Center");
		canvas.setBackground (Color.DARK_GRAY);
		canvas.setPreferredSize(new Dimension(350, 125));
		
		canvas.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 38) {
					System.out.println("hej1");
					tmGUI.taxiMenuCanvas1.UP();
				}
				if(e.getKeyCode() == 40) {
					System.out.println("hej2");
					tmGUI.taxiMenuCanvas1.DW();
				}
				if(e.getKeyCode() == 17) {
					System.out.println("hej3");
					tmGUI.taxiMenuCanvas1.OK();
				}
			} 
		});
		
		canvas.setFocusable (true);
    }

    public static void main(String[] args) throws TooManyListenersException {

    	SWsimulation SWS = new SWsimulation();
    	SWS.setVisible(true);
    	SWS.setPreferredSize(new Dimension(500,500));
    	SWS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}