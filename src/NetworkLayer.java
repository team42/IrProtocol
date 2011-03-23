import java.net.InetAddress;
import java.net.UnknownHostException;


public class NetworkLayer {

	LinkLayer link = new LinkLayer();
	TransportLayer transport = new TransportLayer();
	
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
		
		System.out.println(data);
		System.out.println(IPlength);
		System.out.println(senderIP);
		System.out.println(data.substring(2+IPlength));
		System.out.println("\n" + localIP);
		
		if(!senderIP.equals(localIP)) {
			transport.receiver(data.substring(2+IPlength));
			System.out.println("data sent!");
		}
	}
}
