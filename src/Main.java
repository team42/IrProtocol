import java.util.TooManyListenersException;


public class Main {

	static boolean direction = false;

	public static void main(String[] args) throws TooManyListenersException, InterruptedException {
		
		if(direction == true) {
			ApplicationLayer application = new ApplicationLayer("COM13");
			application.Sender("HELLO!");
			
			while(true) {
				//Thread.sleep(5000);
				application.Sender("");
			}
		} else {
			PhysicalLayer phy = new PhysicalLayer("COM14");
			phy.transmit("HEJ");
			while(true) {
				//Thread.sleep(5000);
				//phy.transmit("");
			}
		}
	}
}
