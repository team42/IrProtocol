package Start;

import java.util.TooManyListenersException;
import IrProtocol.*;

public class startTaxiModule {

	public static void main(String[] args) throws TooManyListenersException, InterruptedException {
		PhysicalLayer phy = new PhysicalLayer("/dev/ttyUSB0");
		System.out.println("Receiver Started!");
	}
}
