package IrProtocol;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TooManyListenersException;

public class NetworkLayer {

	LinkLayer link = null;
	TransportLayerSender transportSe = null;
	TransportLayerReceiver transportRe = null;
	
	boolean sender = false;
	
	public NetworkLayer(String port, TransportLayerSender transp) throws TooManyListenersException {
		transportSe = transp;
		link = new LinkLayer(port, this);
		sender = true;
	}
	
	public NetworkLayer(LinkLayer linkL) throws TooManyListenersException {
		link = linkL;
		transportRe = new TransportLayerReceiver(this);
		sender = false;
	}
	
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