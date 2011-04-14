package IrProtocol;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TooManyListenersException;

/**
 * NetworkLayer
 * 
 * Handles echo by the use of adressing
 * 
 * @author Nicolai Sonne
 *
 */

public class NetworkLayer {

	// Neighbor Layers
	LinkLayer link = null;
	TransportLayerSender transportSe = null;
	TransportLayerReceiver transportRe = null;
	
	// boolean indicating if this is a sender or receiver initialization
	boolean sender = false;
	
	/**
	 * Constructor - Sender mode
	 * 
	 * Set transportSe layer to the initializing layer
	 * Create new link layer with the given port
	 * 
	 * @param port - Port transfered to link layer
	 * @param transp - Transport layer of the initializer
	 * @throws TooManyListenersException
	 */
	public NetworkLayer(String port, TransportLayerSender transp) throws TooManyListenersException {
		transportSe = transp;
		link = new LinkLayer(port, this);
		sender = true;
	}
	
	/**
	 * Constructor - Receiver mode
	 * 
	 * Set link layer to the initializing layer
	 * Create new transport receiver layer
	 * 
	 * @param linkL - link layer of the initializer
	 * @throws TooManyListenersException
	 */
	public NetworkLayer(LinkLayer linkL) throws TooManyListenersException {
		link = linkL;
		transportRe = new TransportLayerReceiver(this);
		sender = false;
	}
	
	/**
	 * Sender
	 * 
	 * Find local IP
	 * Add local IP and length of this IP to the data
	 * Send data to the link layer
	 * 
	 * @param data - Data you wish to send
	 */
	public void Sender(String data) {
		String localIP = "";
		String IPlength = "";
		
		try {
			localIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("Can't find local IP");
		}
		
		IPlength = localIP.length() + "";
		
		if(IPlength.length() < 2) {
			IPlength = "0" + IPlength;
		}
				
		link.Sender(IPlength + localIP + data);
	}
	
	/**
	 * Receiver
	 * 
	 * Find local IP
	 * Compare local IP to IP of received data
	 * If they don't match: Send to transport layer
	 * If they match: Discard data
	 * 
	 * @param data - Data received
	 */
	public void receiver(String data) {
		
		String senderIP = "";
		String localIP = "";
		int IPlength = 0;
		
		try {
			localIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("Can't find local IP");
		}
		
		IPlength = Integer.parseInt(data.substring(0, 2));
		
		senderIP = data.substring(2, 2+IPlength);
		if(!senderIP.equals(localIP)) {
			if(sender) {
				transportSe.receiver(data.substring(2+IPlength));
			} else {
				transportRe.receiver(data.substring(2+IPlength));
			}
		}
	}
}