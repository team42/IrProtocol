package CompanyComm;

import java.io.*;
import java.net.*;
import java.util.*;

import GUI.TaxiModuleGUI;
import Model.TripLockedTime;

public class TaxiSide {

	private InetAddress host;
	private final int PORT = 4242;
	private DatagramSocket datagramSocket;
	private DatagramPacket inPacket, outPacket;
	private byte[] buffer;
	private byte[] address = { (byte)192, (byte)168, (byte)1, (byte)102 };
	
	ArrayList<TripLockedTime> tripList;
	
	Timer timer;
	
	String TaxiID = "A01";
	String Coords = "55.55;22.22";
	
	TaxiModuleGUI tmGUI = null; 
	
	public TaxiSide(TaxiModuleGUI tmGUI) {
		
		tripList = new ArrayList<TripLockedTime>();
		
		this.tmGUI = tmGUI;
		
		try {
			host = InetAddress.getLocalHost();
			//host = InetAddress.getByAddress(address);
		} catch(UnknownHostException uhEx) {
			System.out.println("Host ID not found!");
			System.exit(1);
		}
		
		timer = new Timer();
		timer.schedule(new updateTable(), 1000, 5000);
	}
	
	public void Answer(char answer, String tripID){
		timer.cancel();
		
		try {
			
			datagramSocket = new DatagramSocket();
			
			String message = TaxiID + Coords + answer + tripID;
			
			outPacket = new DatagramPacket(message.getBytes(), message.length(), host, PORT);
			datagramSocket.send(outPacket);
			
			String response;
			
			buffer = new byte[512];
			inPacket = new DatagramPacket(buffer, buffer.length);
			datagramSocket.receive(inPacket);
			response = new String(inPacket.getData(),0,inPacket.getLength());
			
			// set table;
			
			System.out.println("Table: " + response);
			
		} catch(IOException ioEx) {
			ioEx.printStackTrace();
		} finally {
			datagramSocket.close();
		}
		
		timer.schedule(new updateTable(), 5000, 5000);
	}
	
	public void getTable() {
		try {
			datagramSocket = new DatagramSocket();
			
			String message = TaxiID + Coords + "0";
			
			outPacket = new DatagramPacket(message.getBytes(), message.length(), host, PORT);
			datagramSocket.send(outPacket);
			
			String response;
			
			buffer = new byte[512];
			inPacket = new DatagramPacket(buffer, buffer.length);
			datagramSocket.receive(inPacket);
			response = new String(inPacket.getData(),0,inPacket.getLength());
			
			int begin = 0;
			
			int accepted;
			String tripID, time, coords;
			
			TripLockedTime curTrip = null;
			
			tripList.clear();
			
			for(int i=1;i<response.length();i++) {
				if(response.charAt(i) == '%') {
					
					tripID = response.substring(begin, begin+3);
					accepted = Integer.parseInt(response.substring(begin+3, begin+4));
					time = response.substring(begin+4, begin+9);
					coords = response.substring(begin+9, i);
					
					curTrip = new TripLockedTime(tripID, accepted, time, coords);
					
					tripList.add(curTrip);
					begin = i+1;
				}
			}
			
			tmGUI.taxiMenuCanvas1.setTripList(tripList);
			
		} catch(IOException ioEx) {
			ioEx.printStackTrace();
		} finally {
			datagramSocket.close();
		}
	}
	
	class updateTable extends TimerTask  {
	    public void run (  )   {
	    	getTable();
	    }
	}
}