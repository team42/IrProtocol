package Model;

import java.util.*;

public class TripLockedTime {

	private String tripID;
	private String coords;
	private String time;
	private int accepted;
	
	public TripLockedTime(String tripID, int accepted, String time, String coords) {
		this.tripID = tripID;
		this.accepted = accepted;
		this.coords = coords;
		this.time = time;
	}
	
	public String getTripID() {
		return tripID;
	}
	
	public int getAccepted() {
		return accepted;
	}
	
	public String getCoords() {
		return coords;
	}
	
	public String getTime() {
		return time;
	}
}
