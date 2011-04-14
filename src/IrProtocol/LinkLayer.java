package IrProtocol;

import java.util.TooManyListenersException;

/**
 * LinkLayer
 * 
 * Receives bytes from the physical layer
 * Handles framing and checksum
 * 
 * @author Nicolai Sonne
 *
 */

public class LinkLayer {
	// Neighbor layers
	PhysicalLayer physical = null;
	NetworkLayer network = null;
	
	// Strings used for checksum and buffer
	String reChecksum, upChecksum, buffer = "";
	
	// State and checksum-counter
	int receiverState, checksumSize = 0;
	
	/**
	 * Constructor - Sender mode
	 * 
	 * Set network layer to the initializing layer
	 * Create new physical layer with the given port
	 * 
	 * @param port - Port transfered to physical layer
	 * @param netL - network layer of the initializer
	 * @throws TooManyListenersException
	 */
	public LinkLayer(String port, NetworkLayer netL) throws TooManyListenersException {
		network = netL;
		physical = new PhysicalLayer(port, this);
	}
	
	/**
	 * Constructor - Receiver mode
	 * 
	 * Set physical layer to the initializing layer
	 * Create new network layer
	 * 
	 * @param phy - physical layer of the initializer
	 * @throws TooManyListenersException
	 */
	public LinkLayer(PhysicalLayer phy) throws TooManyListenersException {
		physical = phy;
		network = new NetworkLayer(this);
	}
	
	/**
	 * Sender
	 * 
	 * Checksum of data is calculated
	 * start/end frame flags and checksum is added to the data and transmitted
	 * 
	 * @param data - Data you wish to send
	 */
	public void Sender(String data) {
		String checksum = Checksum(data, true);
		physical.transmit("%S" + checksum + data + "%E");
	}
	
	/**
	 * Receiver
	 * 
	 * One byte is loaded each time
	 * Start frame flag is found
	 * Checksum is found
	 * Data is loaded to a buffer
	 * if End frame flag is found:
	 * - Checksum is calculated and compared to the received checksum
	 * - if they match: data is send to network layer - if not: discard
	 * if Start frame flag is found:
	 * - go back and find checksum
	 * 
	 * @param data - Data which is received
	 */
	public void receiver(String data) {

		switch(receiverState) {
			case 0:
				if(data.equals("%")) {
					receiverState = 1;
				}
				break;
			case 1:
				if(data.equals("S")) {
					receiverState = 2;
				}
				break;
			case 2:
				if(checksumSize == 0) {
					reChecksum = data;
					checksumSize++;
				} else if(checksumSize == 2) {
					buffer = data;
					receiverState = 3;
					checksumSize = 0;
				} else {
					reChecksum += data;
					checksumSize++;
				}
				break;
			case 3:
				if (data.equals("%")) {
					receiverState = 4;
				} else {
					buffer += data;
				}
				break;
			case 4:
				if (data.equals("E")) {
					upChecksum = Checksum(buffer, false);
					
					int cs1 = Integer.parseInt(reChecksum, 16);
					int cs2 = Integer.parseInt(upChecksum, 16);
					
					if((cs1 & cs2) == 0) {
						network.receiver(buffer);
					}
					receiverState = 0;
				} else if (data.equals("S")) {
					receiverState = 2;
				}
				break;
		}
	}
	
	/**
	 * Checksum
	 * 
	 * Checksum is calculated using 1's complement
	 * All bytes are added together
	 * Overflow is added to the first byte
	 * Checksum inverted if it's a sender checksum
	 * Converted to Hex
	 * If the Hex-string is only one character a "0" is added to the front
	 * 
	 * @param data - Data to be calculated checksum from
	 * @param sender - boolean noting if it's a sender checksum
	 * @returns the calculated checksum
	 */
	private String Checksum(String data, boolean sender) {
	    
		byte buf[] = data.getBytes(); 

		long sum = 0;
	    
	    for(int i=0; i < buf.length; i++) {
	    	sum += buf[i];
	    }
	    
	    while (sum > 255) {
	    	sum = (sum & 0xFF) + (sum >> 8);
	    }
	    
	    if(sender) {
	    	sum = (~sum) & 0xFF;
	    }
	    
	    String rtnStr = Long.toHexString(sum);
	    
	    while(rtnStr.length() < 2) {
	    	rtnStr = "0" + rtnStr;
	    }
	    
	    return rtnStr;
	}
}
