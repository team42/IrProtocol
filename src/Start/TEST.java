package Start;

//import java.sql.*;
import java.text.*;
import java.util.*;

public class TEST {
	public static void main(String[] args) throws InterruptedException {
		
		Date d0 = new Date(111, 3, 21, 22, 55, 37);
		Date d1 = Calendar.getInstance().getTime();
		
		System.out.println(d0);
		System.out.println(d1);
		
		System.out.println(compareTime(d0,d1));
	}
	
	public static String compareTime(Date d1, Date d2) {       
	    long difference = Math.abs(d1.getTime()-d2.getTime());
	    difference = difference / 1000;
	    
	    System.out.println(difference);
	    
	    String seconds = ""+difference % 60;  
	    String minutes = ""+(difference % 3600)/60;
	    if(seconds.length() < 2) seconds = "0" + seconds;
	    if(minutes.length() < 2) minutes = "0" + minutes;
	    String time =  minutes + ":" + seconds;  
	    return time;
	}  
}
