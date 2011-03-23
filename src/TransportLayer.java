import java.util.Timer;


public class TransportLayer {
	
	ApplicationLayer application = new ApplicationLayer();
	NetworkLayer network = new NetworkLayer();
	
	int senderState = 0;
	int receiverState = 0;
	
	long ts = 0;
	long timeout = 1000;
	
	public void Sender(String data) {
		switch(senderState) {
			case 0:
				network.Sender("0" + data);
				ts = System.currentTimeMillis();
				senderState = 1;
				break;
			case 1:
				if ((System.currentTimeMillis()-ts) > timeout) {
					network.Sender("0" + data);
					ts = System.currentTimeMillis();
					senderState = 1;
				}
				// else if ack = 0, state 2
				break;
			case 2:
				network.Sender("1" + data);
				ts = System.currentTimeMillis();
				senderState = 3;
				break;
			case 3:
				if ((System.currentTimeMillis()-ts) > timeout) {
					network.Sender("1" + data);
					ts = System.currentTimeMillis();
					senderState = 3;
				}
				// else if ack = 1, state 0
				break;
		}
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
			}
			break;
		case 1:
			if (data.substring(0,1).equals("1")) {
				network.Sender("ACK1");
				application.receiver(data.substring(1));
				receiverState = 0;
			} else {
				network.Sender("ACK0");
			}
			break;
		}
	}

}
