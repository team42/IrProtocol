import java.util.TooManyListenersException;


public class LinkLayer {

	PhysicalLayer physical = null;
	NetworkLayer network = null;
	
	String reChecksum, upChecksum, buffer = "";
		
	int receiverState, checksumSize = 0;
	
	public LinkLayer(String port, NetworkLayer netL) throws TooManyListenersException {
		network = netL;
		physical = new PhysicalLayer(port, this);
	}
	
	public LinkLayer(PhysicalLayer phy) throws TooManyListenersException {
		physical = phy;
		network = new NetworkLayer(this);
	}
	
	public void Sender(String data) {
		String checksum = Checksum(data);
		physical.transmit("%S" + checksum + data + "%E");
	}
	
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
				} else if(checksumSize == 8) {
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
					System.out.println("End of Frame!!");
					upChecksum = Checksum(buffer);
					if (upChecksum.equals(reChecksum)) {
						network.receiver(buffer);
					}
					receiverState = 0;
				} else if (data.equals("S")) {
					System.out.println("Start of Frame!!");
					receiverState = 2;
				}
				break;
		}
	}
	
	private String Checksum(String data) {
		String checksum = "checksum";
		
		return checksum;
	}
}
