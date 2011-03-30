package IrProtocol;

import GUI.*;
import java.util.TooManyListenersException;

public class ApplicationLayer {

	TransportLayerSender transportSe = null;
	
	TaxiModuleGUI tmGUI = null;
	
	public ApplicationLayer(String port) throws TooManyListenersException {
		transportSe = new TransportLayerSender(port, this);
	}
	
	public ApplicationLayer() throws TooManyListenersException {
		tmGUI = new TaxiModuleGUI();
		tmGUI.setVisible(true);
	}
	
	public void Sender(String data) {
		transportSe.Sender(data);
	}
	
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
