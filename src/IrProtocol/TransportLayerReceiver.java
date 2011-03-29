package IrProtocol;
import java.util.TooManyListenersException;


public class TransportLayerReceiver {
	
	ApplicationLayer application = null;
	NetworkLayer network = null;
	
	public String ACK = "1";
	
	int senderState = 0; 
	int receiverState = 0;
	
	byte hej = 4;
	
	long ts = 0;
	long timeout = 1000;
	
	boolean msgAcked = false;
	
	public TransportLayerReceiver(NetworkLayer netL) throws TooManyListenersException {
		network = netL;
		application = new ApplicationLayer();
	}
	
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
