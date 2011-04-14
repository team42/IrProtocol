package IrProtocol;

import java.util.TooManyListenersException;

/**
 * Transport Layer - Sender
 * 
 * Will send the data and retransmit if an acknowledgement
 * haven't been received in a calculated time. 
 * 
 * @author Nicolai Sonne
 *
 */

public class TransportLayerSender {

	// Neighbor layers
	ApplicationLayer application = null;
	NetworkLayer network = null;
	
	// "Last acked"
	public String ACK = "1";
	
	// Variables for time measurements and timeout calculation
	long samRTT, estRTT, devRTT = 0;
	long ts, sRTT = 0;
	long timeout = 500;
	
	// Current state
	int senderState = 0;
	
	// Ack status
	boolean msgAcked = false;
	
	// Set first RTT
	boolean firstRTT = true;
	
	// Constructor
	// Create new networklayer and set application layer to the initializer for this object
	public TransportLayerSender(String port, ApplicationLayer appL) throws TooManyListenersException {
		application = appL;
		network = new NetworkLayer(port, this);
	}
	
	/**
	 * Sender
	 * 
	 * Sends data to the network layer and waits for acknowledgement
	 * 
	 * @param data
	 */
	public void Sender(String data) {
		do {
			switch(senderState) {
				case 0:
					// Send data with sequense number 0
					network.Sender("0" + data);
					
					// Start timer for timeout and RTT measurements
					ts = System.currentTimeMillis();
					sRTT = System.currentTimeMillis();
					
					// Set message acked false
					msgAcked = false;

					// Go to state 1
					senderState = 1;
					break;
				case 1:
					// If timer exceeds timeout:
					// Retransmit and restart timer
					if ((System.currentTimeMillis()-ts) > timeout) {
						network.Sender("0" + data);
						ts = System.currentTimeMillis();
						msgAcked = false;
						senderState = 1;
					
					// If message have been acked:
					// Calculate timeout and go to next state
					} else if (ACK.equals("0")) {
						msgAcked = true;
						samRTT = System.currentTimeMillis()-sRTT;

						timeout = CalcTimeout(samRTT);
						
						System.out.println(samRTT + "/" + estRTT + "/" + devRTT + "/" + timeout);
						senderState = 2;
					}
					break;
				case 2:
					// Send data with sequense number 0
					network.Sender("1" + data);
					
					// Start timer for timeout and RTT measurements
					ts = System.currentTimeMillis();
					sRTT = System.currentTimeMillis();
					
					// Set message acked false
					msgAcked = false;
					
					// Go to state 3
					senderState = 3;
					break;
				case 3:
					// If timer exceeds timeout:
					// Retransmit and restart timer
					if ((System.currentTimeMillis()-ts) > timeout) {
						network.Sender("1" + data);
						ts = System.currentTimeMillis();
						msgAcked = false;
						senderState = 3;
					
					// If message have been acked:
					// Calculate timeout and go to next state
					} else if (ACK.equals("1")) {
						msgAcked = true;
						samRTT = System.currentTimeMillis()-sRTT;
						
						timeout = CalcTimeout(samRTT);
						
						System.out.println(samRTT + "/" + estRTT + "/" + devRTT + "/" + timeout);
						senderState = 0;
					}
					break;
			}
		} while(!msgAcked);
	}
	
	/**
	 * Receiver
	 * 
	 * Set "last acked" if acks are received 
	 * 
	 * @param data - Incoming data
	 */
	public void receiver(String data) {
		if (data.equals("ACK1")){
			ACK = "1";
		} else if (data.equals("ACK0")) {
			ACK = "0";
		}
		System.out.println("Last ACK: " + ACK);
	}
	
	/**
	 * CalcTimeout
	 * 
	 * Calcualte timeout the same way as TCP
	 * 
	 * @param SampleRTT - Last RTT measurement
	 * @returns the calculated timeout
	 */
	private long CalcTimeout(long SampleRTT){
		// Define alpha and beta
		double alpha = 0.125;
		double beta = 0.25;
		
		// Initialise temporary calculated timeout
		long calcedTO = 0;		
		
		// If it's the first time:
		// Set estimated RTT to the sample timeout
		// Set devRTT to beta*estRTT
		if(firstRTT) {
			estRTT = samRTT;
			devRTT = (long)beta*estRTT;
			firstRTT = false;
		// If it's now the first time:
		// Estimated RTT is modified by sampletimeout
		// devRTT is also calculated
		} else {
			estRTT = (long)((1-alpha)*estRTT+alpha*samRTT);
			devRTT = (long)((1-beta)*devRTT+beta*Math.abs(samRTT-estRTT));
		}
		
		// the Timeout is set to the estimated RTT plus 4 times devRTT
		calcedTO = (long)(estRTT + 4*devRTT);
		
		// if the calculated timeout is above 1 second, it's set to a second
		if(calcedTO > 1000) {
			calcedTO = 1000;
		}
		
		return calcedTO;
	}
}
