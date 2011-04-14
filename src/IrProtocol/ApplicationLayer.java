package IrProtocol;

import GUI.*;
import java.util.TooManyListenersException;

public class ApplicationLayer {
	
	// Neighbor layer
	TransportLayerSender transportSe = null;
	
	// Taxi Module GUI
	TaxiModuleGUI tmGUI = null;
	
	/**
	 * Constructor - Sender mode
	 * 
	 * Create new transport layer with the given port
	 * 
	 * @param port - Serial port used
	 * @throws TooManyListenersException
	 */
	public ApplicationLayer(String port) throws TooManyListenersException {
		transportSe = new TransportLayerSender(port, this);
	}
	
	/**
	 * Constructor - Receiver mode
	 * 
	 * Initialize Taxi module GUI
	 * 
	 * @throws TooManyListenersException
	 */
	public ApplicationLayer() throws TooManyListenersException {
		tmGUI = new TaxiModuleGUI();
		tmGUI.setVisible(true);
	}
	
	/**
	 * Sender
	 * 
	 * Send data to Transport layer
	 * 
	 * @param data - Data you wish to send
	 */
	public void Sender(String data) {
		transportSe.Sender(data);
	}
	
	/**
	 * Receiver
	 * 
	 * If an accepted command is received the corresponding 
	 * method is called of the taxi module GUI.
	 * 
	 * @param data
	 */
	public void receiver(String data) {
		System.out.println("Data received: " + data);
		if(data.equals("UP")) {
			tmGUI.taxiMenuCanvas1.UP();
		} else if(data.equals("DW")) {
			tmGUI.taxiMenuCanvas1.DW();
		} else if(data.equals("OK")) {
			tmGUI.taxiMenuCanvas1.OK();
		}
	}	
}
