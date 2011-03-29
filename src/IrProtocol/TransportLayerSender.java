package IrProtocol;
import java.util.TooManyListenersException;


public class TransportLayerSender {
	
	ApplicationLayer application = null;
	NetworkLayer network = null;
	
	public String ACK = "1";
	
	double samRTT, estRTT, devRTT = 0;
	
	long ts, sRTT = 0;
	long timeout = 2000;
	
	int senderState = 0;
	
	boolean msgAcked = false;
	boolean firstRTT = true;
	
	public TransportLayerSender(String port, ApplicationLayer appL) throws TooManyListenersException {
		application = appL;
		network = new NetworkLayer(port, this);
	}
	
	public void Sender(String data) {
		do {
			switch(senderState) {
				case 0:
					network.Sender("0" + data);
					ts = System.currentTimeMillis();
					sRTT = System.currentTimeMillis();
					msgAcked = false;
					senderState = 1;
					break;
				case 1:
					if ((System.currentTimeMillis()-ts) > timeout) {
						network.Sender("0" + data);
						ts = System.currentTimeMillis();
						msgAcked = false;
						senderState = 1;
					} else if (ACK.equals("0")) {
						msgAcked = true;
						samRTT = System.currentTimeMillis()-sRTT;
						if(firstRTT) {
							estRTT = samRTT;
							devRTT = 0.25*estRTT;
							firstRTT = false;
						} else {
							estRTT = (1-0.125)*estRTT+0.125*samRTT;
							devRTT = (1-0.25)*devRTT+0.25*Math.abs(samRTT-estRTT);
						}
						timeout = (long)(estRTT + 4*devRTT);
						//System.out.println(samRTT + "/" + estRTT + "/" + devRTT + "/" + timeout);
						senderState = 2;
					}
					break;
				case 2:
					network.Sender("1" + data);
					ts = System.currentTimeMillis();
					sRTT = System.currentTimeMillis();
					msgAcked = false;
					senderState = 3;
					break;
				case 3:
					if ((System.currentTimeMillis()-ts) > timeout) {
						network.Sender("1" + data);
						ts = System.currentTimeMillis();
						msgAcked = false;
						senderState = 3;
					} else if (ACK.equals("1")) {
						msgAcked = true;
						samRTT = System.currentTimeMillis()-sRTT;
						if(firstRTT) {
							estRTT = samRTT;
							devRTT = 0.25*estRTT;
							firstRTT = false;
						} else {
							estRTT = (1-0.125)*estRTT+0.125*samRTT;
							devRTT = (1-0.25)*devRTT+0.25*Math.abs(samRTT-estRTT);
						}
						timeout = (long)(estRTT + 4*devRTT);
						//System.out.println(samRTT + "/" + estRTT + "/" + devRTT + "/" + timeout);
						senderState = 0;
					}
					break;
			}
		} while(!msgAcked);
	}
	
	public void receiver(String data) {
		if (data.equals("ACK1")){
			ACK = "1";
		} else if (data.equals("ACK0")) {
			ACK = "0";
		}
		System.out.println("Last ACK: " + ACK);
	}

}
