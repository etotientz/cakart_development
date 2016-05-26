import java.util.*;
import java.sql.Timestamp;

public class DemoTime {

    public static void main(String[] args) {
        Date d1 = new Date();
       // Date d2 = new Date(d1.getTime());
       long i=2; Timestamp t = new Timestamp (d1.getTime());
        //while(i-->0){
       
        System.out.println ("date1 = " + d1 + " (" + d1.getTime() + ")" );
       /* System.out.println ("date2 = " + d2 + " (" + d2.getTime() + ")" );
        System.out.println ("timestamp = " + t + "  (" + t.getTime() + ")" );
        System.out.println ("d1 before d2: " + d1.before(d2));
        System.out.println ("d1 after  d2: " + d1.after(d2));
        System.out.println ("d1 before ts: " + d1.before(t));
        System.out.println ("d1 after  ts: " + d1.after(t)); //why true?
        */
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        System.out.println("Yesterday's date = "+ cal.getTime());
        }
      // Timestamp ts=new Timestamp();
        	/*	Calendar cal = Calendar.getInstance();
        		cal.setTime(t);
        		cal.add(Calendar.DAY_OF_WEEK, 14);
        		t.setTime(cal.getTime().getTime()); // or
        		t = new Timestamp(cal.getTime().getTime());
        		
    }*/
}