package IrProtocol;

import java.util.TooManyListenersException;

public class ApplicationLayer {

	TransportLayerSender transportSe = null;
	
	public ApplicationLayer(String port) throws TooManyListenersException {
		transportSe = new TransportLayerSender(port, this);
	}
	
	public ApplicationLayer() throws TooManyListenersException {
	}
	
	public void Sender(String data) {
		transportSe.Sender(data);
	}
	
	public void receiver(String data) {
		System.out.println("Data received: " + data);
	}	
}
