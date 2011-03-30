package Main;

import java.util.TooManyListenersException;
import IrProtocol.*;

public class Main {

	public static void main(String[] args) throws TooManyListenersException, InterruptedException {
		
		PhysicalLayer phy = new PhysicalLayer("/dev/ttyUSB1");
		System.out.println("Receiver Started!");
		
	}
}
