import java.util.TooManyListenersException;


public class ApplicationLayer {

	TransportLayer transport = null;
	
	public ApplicationLayer() throws TooManyListenersException {
		transport = new TransportLayer(this);
	}
	
	public void Sender(String data) {
		
	}
	
	public void receiver(String data) {
		
	}	
}
