import javax.swing.*;

import IrProtocol.ApplicationLayer;

//http://www.particle.kth.se/~lindsey/JavaCourse/Book/Part1/Java/Chapter12/catchingKeystrokes.html

import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;
import java.util.TooManyListenersException;

@SuppressWarnings("serial")

public class SteeringWheelGUI extends JFrame {

	public SteeringWheelGUI() throws TooManyListenersException {
		
		final ApplicationLayer appLayer = new ApplicationLayer("COM11");
		
		JPanel canvas = new JPanel ();
		add (canvas, "Center");
		canvas.setBackground (Color.BLUE);
		canvas.setPreferredSize(new Dimension(350, 125));
		
		canvas.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 38) {
					appLayer.Sender("UP");
					//Send UP
					//Change UP Icon
					//System.out.println("UP");
				}
				if(e.getKeyCode() == 40) {
					appLayer.Sender("DW");
					//Send DW
					//Change DW Icon
					//System.out.println("DW");
				}
				if(e.getKeyCode() == 17) {
					appLayer.Sender("OK");
					//Send OK
					//Change OK Icon
					//System.out.println("OK");
				}
			}
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == 38) {
					//Change UP Icon back
					//System.out.println("UP icon back");
				}
				if(e.getKeyCode() == 40) {
					//Change DW Icon back
					//System.out.println("DW icon back");
				}
				if(e.getKeyCode() == 17) {
					//Change OK Icon back
					//System.out.println("OK icon back");
				}
		    } 
		});
		
		canvas.setFocusable (true);
    }

    public static void main(String[] args) throws TooManyListenersException {

    	SteeringWheelGUI SWG = new SteeringWheelGUI();
    	SWG.setVisible(true);
    	SWG.setPreferredSize(new Dimension(500,500));
    	SWG.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
