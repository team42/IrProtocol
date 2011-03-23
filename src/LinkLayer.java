
public class LinkLayer {

	PhysicalLayer physical = new PhysicalLayer();
	NetworkLayer network = new NetworkLayer();
	
	String SoF = "%S";
	String EoF = "%E";
	
	int receiverState = 0;
	
	public void Sender(String data) {
		String checksum = Checksum(data);
		physical.Sender(checksum + data);
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
