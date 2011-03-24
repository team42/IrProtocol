import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TooManyListenersException;


public class NetworkLayer {

	LinkLayer link = null;
	TransportLayer transport = null;
	
	public NetworkLayer(String port, TransportLayer transp) throws TooManyListenersException {
		transport = transp;
		link = new LinkLayer(port, this);
	}
	
	public NetworkLayer(LinkLayer linkL) throws TooManyListenersException {
		link = linkL;
		transport = new TransportLayer(this);
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
		
		System.out.println("Full Data: " + data);
		System.out.println("IP length: " + IPlength);
		System.out.println("Sender IP: " + senderIP);
		System.out.println("Data: " + data.substring(2+IPlength));
		System.out.println("Local IP: " + localIP);
		
		if(!senderIP.equals(localIP)) {
			transport.receiver(data.substring(2+IPlength));
			System.out.println("data sent!");
		}
	}
}
