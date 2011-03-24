import java.util.TooManyListenersException;


public class LinkLayer {

	PhysicalLayer physical = null;
	NetworkLayer network = null;
	
	String SoF, EoF = null;
	
	int receiverState = 0;
	
	public LinkLayer(NetworkLayer netL) throws TooManyListenersException {
		
		//physical = new PhysicalLayer("/dev/ttyUSB0", this);
		network = netL;
		
		SoF = "%S";
		EoF = "%E";
		
		receiverState = 0;
		
	}
	
	public void Sender(String data) {
		String checksum = Checksum(data);
		physical.transmit(checksum + data);
		physical.closePort();
	}
	
	public void receiver(String data) {
		String reChecksum = "";
		String upChecksum = Checksum(data);
		
		switch(receiverState) {
			case 0:
				if(data.substring(0, 2).equals("%S")) {
					receiverState = 1;
				}
				break;
			case 1:
				reChecksum = data.substring(2,7);
				break;
			case 2:
				
				break;
		
		}
	}
	
	private String Checksum(String data) {
		String checksum = "checksum";
		
		return checksum;
	}
}
