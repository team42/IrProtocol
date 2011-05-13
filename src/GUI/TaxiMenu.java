package GUI;

import Model.*;
import java.awt.*;
import java.util.ArrayList;

// @author Nicolai

public class TaxiMenu {

	int buWidth = 190;
	int buHeight = 50;
	
	int free = 1;
	
    int currentSelected = 1;
    
    ArrayList<TripLockedTime> tripLIST;
    
    public TaxiMenu() {
    	tripLIST = new ArrayList<TripLockedTime>();
    }

    public void draw(Graphics g) {
    	
    	TripLockedTime curTrip = null;
    	
    	/*
    	for (int i = 0; i < tripLIST.size(); i++) {
    		curTrip = tripLIST.get(i);
    		if(curTrip.getAccepted() == 1) free = 0;
    	}*/
    	
    	free = 0;
    	
    	g.fillRect(7, 7 + ((currentSelected - 1) * (buHeight+10)), buWidth+7, buHeight+7);
    	
    	if(free == 1) {
    		g.setColor(Color.GREEN);
        	g.fillRect(10, 10, buWidth, buHeight);
            
        	g.setColor(Color.BLACK);
        	g.setFont(new Font("Verdana Bold", 1, 24));
            g.drawString("Start Trip", 38, 43);
    	}
    	
        g.setColor(Color.LIGHT_GRAY);
    	for (int i = 0; i < tripLIST.size(); i++) {
            curTrip = tripLIST.get(i);
            if(curTrip.getAccepted() == 1) {
            	g.setColor(Color.GREEN);
            	g.fillRect(10, 10 + ((free+i) * (buHeight+10)), buWidth, buHeight);
            	g.setColor(Color.LIGHT_GRAY);
            } else {
            	g.fillRect(10, 10 + ((free+i) * (buHeight+10)), buWidth, buHeight);
            }
        }

        g.setColor(Color.black);

        g.setFont(new Font("Verdana Bold", 0, 12));
        
        for (int i = 0; i < tripLIST.size(); i++) {
            curTrip = tripLIST.get(i);
        	g.drawString("ID: " + curTrip.getTripID(), 20, 30 + ((free+i) * (buHeight+10)));
        	g.drawString(curTrip.getCoords(), 20, 50 + ((free+i) * (buHeight+10)));
        	g.drawString(curTrip.getTime(), 152, 30 + ((free+i) * (buHeight+10)));
        }
        
        for (int i = 0; i < tripLIST.size()+free; i++) {
            g.drawRect(10, 10 + (i * (buHeight+10)), buWidth, buHeight);
        }
    }

    public void Up() {
        currentSelected = currentSelected-1;
        if (currentSelected < 1) {
            currentSelected = tripLIST.size();
        }
    }

    public void Down() {
        currentSelected = currentSelected+1;
        if (currentSelected > tripLIST.size()) {
            currentSelected = 1;
        }
    }
    
    public void setTripList(ArrayList<TripLockedTime> tripList) {
    	tripLIST = tripList;
    }
}
