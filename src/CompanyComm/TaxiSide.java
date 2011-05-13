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
	
	String TaxiID = "A00001";
	String Coords = "9999,9999";
	
	TaxiModuleGUI tmGUI = null; 
	
	public TaxiSide(TaxiModuleGUI tmGUI) {
		
		tripList = new ArrayList<TripLockedTime>();
		
		this.tmGUI = tmGUI;
		
		try {
			//host = InetAddress.getLocalHost();
			host = InetAddress.getByAddress(address);
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
			
			int accepted;
			String time, coords;
			
			TripLockedTime curTrip = null;
			
			tripList.clear();
			
			String[] trips = response.split("%");
			
			if(trips.length > 0 && trips[0].length() > 15) {
				for(int i=0; i<trips.length; i++) {
					tripID = trips[i].substring(0, 10);
					accepted = Integer.parseInt(trips[i].substring(10, 11));
					time = trips[i].substring(11, 16);
					coords = trips[i].substring(16);
					
					curTrip = new TripLockedTime(tripID, accepted, time, coords);
					
					tripList.add(curTrip);
				}
			}
			
			tmGUI.taxiCanvas.setTripList(tripList);
			
		} catch(IOException ioEx) {
			ioEx.printStackTrace();
		} finally {
			datagramSocket.close();
		}
		
		timer = new Timer();
		timer.schedule(new updateTable(), 5000, 5000);
	}
	
	public void getTable() {
		try {
			datagramSocket = new DatagramSocket();
			
			String message = TaxiID + Coords + "0";
			
			//System.out.println(message);
			
			outPacket = new DatagramPacket(message.getBytes(), message.length(), host, PORT);
			datagramSocket.send(outPacket);
			
			String response;
			
			buffer = new byte[512];
			inPacket = new DatagramPacket(buffer, buffer.length);
			datagramSocket.receive(inPacket);
			response = new String(inPacket.getData(),0,inPacket.getLength());
			
			int accepted;
			String tripID, time, coords;
			
			TripLockedTime curTrip = null;
			
			tripList.clear();
			
			String[] trips = response.split("%");
			
			if(trips.length > 0 && trips[0].length() > 15) {
				for(int i=0; i<trips.length; i++) {
					System.out.println("length: " + trips.length);
					System.out.println("Trip " + i + ": " + trips[i]);
					tripID = trips[i].substring(0, 10);
					accepted = Integer.parseInt(trips[i].substring(10, 11));
					time = trips[i].substring(11, 16);
					coords = trips[i].substring(16);
					
					curTrip = new TripLockedTime(tripID, accepted, time, coords);
					
					tripList.add(curTrip);
				}
			}
			
			tmGUI.taxiCanvas.setTripList(tripList);
			
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