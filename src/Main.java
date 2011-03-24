import java.util.TooManyListenersException;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws TooManyListenersException, InterruptedException {
		
		PhysicalLayer phy = new PhysicalLayer("/dev/ttyUSB0");
		phy.transmit("HEJ!");
		//phy.closePort();
		while(true) {
			
		}
		//phy.closePort();
		
		//LinkLayer link = new LinkLayer();
		//System.out.println("init done!");
		//link.Sender("hej");
		
		//NetworkLayer network = new NetworkLayer(new TransportLayer(new ApplicationLayer()));
		//network.Sender("hejmeddig!");
		//network.receiver("1010.22.44.1kkkkkkk");
		//network.receiver("10hhhhhhhhhhkkkkkkk");
		
		//TransportLayer transport = new TransportLayer();
		//transport.Sender("HEEEEJ!");
		
		//PhysicalLayer phy = new PhysicalLayer("/dev/ttyUSB0", new LinkLayer(new NetworkLayer(new TransportLayer(new ApplicationLayer()))));
		//PhysicalLayer phy = new PhysicalLayer("/dev/ttyUSB0");
		//phy.transmit("HEEEJ!");
		
		//System.exit(1);
	}

}
