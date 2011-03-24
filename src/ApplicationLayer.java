import java.util.TooManyListenersException;


public class ApplicationLayer {

	TransportLayer transport = null;
	
	public ApplicationLayer(String port) throws TooManyListenersException {
		transport = new TransportLayer(port, this);
	}
	
	public ApplicationLayer() throws TooManyListenersException {
	}
	
	public void Sender(String data) {
		System.out.println("Send Data:\n" + data);
		transport.Sender(data);
	}
	
	public void receiver(String data) {
		System.out.println("Received Data:\n" + data);
	}	
}
