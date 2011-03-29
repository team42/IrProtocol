package Main;

import java.util.TooManyListenersException;
import IrProtocol.*;

public class Main {

	static boolean direction = true;

	public static void main(String[] args) throws TooManyListenersException, InterruptedException {
		
		int cs1 = Integer.parseInt(checksum("gulle", true), 16);
		int cs2 = Integer.parseInt(checksum("gulle", false), 16);
		
		System.out.println(cs1 + " + " + cs2 + " = " + (cs1&cs2));
		
		/*if(direction == true) {
			ApplicationLayer application = new ApplicationLayer("COM11");
			System.out.println("Sender Started!");
			application.Sender("HELLO LASSE!");
			application.Sender("HELLO AGAIN!");
			application.Sender("HELLO AGAIN AGAIN!");
		} else {
			@SuppressWarnings("unused")
			PhysicalLayer phy = new PhysicalLayer("/dev/ttyUSB1");
			System.out.println("Receiver Started!");
		}*/
	}
	
	static String checksum(String data, boolean sender) {
	    byte buf[] = data.getBytes(); 

		long sum = 0;
	    
	    for(int i=0; i < buf.length; i++) {
	    	sum += buf[i];
	        System.out.println("1: " + Long.toHexString(sum));
	    }
	    
	    while (sum > 255) {
	    	sum = (sum & 0xFF) + (sum >> 8);
	    	System.out.println("2: " + sum);
	    }
	    
	    if(sender) {
	    	sum = (~sum) & 0xFF;
	    }
	    System.out.println("3: " + Long.toHexString((~sum) & 0xFF));
	    
	    return Long.toHexString(sum);
	}
}
