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
					tmGUI.taxiCanvas.UP();
				}
				if(e.getKeyCode() == 40) {
					tmGUI.taxiCanvas.DW();
				}
				// Enter
				if(e.getKeyCode() == 10) {
					tmGUI.Accept();
				}
				// Backspace
				if(e.getKeyCode() == 8) {
					tmGUI.Decline();
				}
				// Space
				if(e.getKeyCode() == 32) {
					tmGUI.OK();
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