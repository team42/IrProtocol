package IrProtocol;
import java.util.TooManyListenersException;

/**
 * TransportLayerReceiver
 * 
 * Handles the receiver side og reliability
 * 
 * @author Nicolai Sonne
 *
 */

public class TransportLayerReceiver {
	
	// Neighbor Layers
	ApplicationLayer application = null;
	NetworkLayer network = null;
	
	// State of the receiver method 
	int receiverState = 0;
	
	/**
	 * Constructor
	 * 
	 * Set network layer to the initializing layer
	 * Create new application layer
	 * 
	 * @param netL- network layer of the initializer
	 * @throws TooManyListenersException
	 */
	public TransportLayerReceiver(NetworkLayer netL) throws TooManyListenersException {
		network = netL;
		application = new ApplicationLayer();
	}
	
	/**
	 * Receiver
	 * 
	 * Sends data to the application layer or discard it depending on the sequence number.
	 * Also sends the appropriate ack back to the sender.
	 * 
	 * @param data - Data received
	 */
	public void receiver(String data) {
		switch(receiverState) {
		case 0:
			if (data.substring(0,1).equals("0")) {
				network.Sender("ACK0");
				application.receiver(data.substring(1));
				receiverState = 1;
			} else {
				network.Sender("ACK1");
				receiverState = 0;
			}
			break;
		case 1:
			if (data.substring(0,1).equals("1")) {
				network.Sender("ACK1");
				application.receiver(data.substring(1));
				receiverState = 0;
			} else {
				network.Sender("ACK0");
				receiverState = 1;
			}
			break;
		}
	}
}
