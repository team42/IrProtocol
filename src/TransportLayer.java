import java.util.TooManyListenersException;


public class TransportLayer {
	
	ApplicationLayer application = null;
	NetworkLayer network = null;
	
	public String ACK = "1";
	
	int senderState = 0; 
	int receiverState = 0;
	
	long ts = 0;
	long timeout = 1000;
	
	boolean msgAcked = false;
	
	public TransportLayer(String port, ApplicationLayer appL) throws TooManyListenersException {
		application = appL;
		network = new NetworkLayer(port, this);
	}
	
	public TransportLayer(NetworkLayer netL) throws TooManyListenersException {
		network = netL;
		application = new ApplicationLayer();
	}
	
	public void Sender(String data) {
		do {
			switch(senderState) {
				case 0:
					network.Sender("0" + data);
					ts = System.currentTimeMillis();
					senderState = 1;
					msgAcked = false;
					break;
				case 1:
					if ((System.currentTimeMillis()-ts) > timeout) {
						network.Sender("0" + data);
						ts = System.currentTimeMillis();
						senderState = 1;
						msgAcked = false;
					} else if (ACK.equals("0")) {
						senderState = 2;
						msgAcked = true;
					}
					break;
				case 2:
					network.Sender("1" + data);
					ts = System.currentTimeMillis();
					senderState = 3;
					msgAcked = false;
					break;
				case 3:
					if ((System.currentTimeMillis()-ts) > timeout) {
						network.Sender("1" + data);
						ts = System.currentTimeMillis();
						senderState = 3;
						msgAcked = true;
					} else if (ACK.equals("1")) {
						senderState = 0;
						msgAcked = false;
					}
					break;
			}
		} while(!msgAcked);
	}
	
	public void receiver(String data) {
		switch(receiverState) {
		case 0:
			if (data.substring(0,1).equals("0")) {
				network.Sender("ACK0");
				application.receiver(data.substring(1));
				receiverState = 1;
			} else if (data.equals("ACK1")){
				ACK = "1";
				network.Sender("ACK1");
			} else if (data.equals("ACK0")) {
				ACK = "1";
				network.Sender("ACK1");
			} else {
				network.Sender("ACK1");
			}
			break;
		case 1:
			if (data.substring(0,1).equals("1")) {
				network.Sender("ACK1");
				application.receiver(data.substring(1));
				receiverState = 0;
			} else if (data.equals("ACK1")){
				ACK = "1";
				network.Sender("ACK0");
			} else if (data.equals("ACK0")) {
				ACK = "1";
				network.Sender("ACK0");
			} else {
				network.Sender("ACK0");
			}
			break;
		}
	}

}
