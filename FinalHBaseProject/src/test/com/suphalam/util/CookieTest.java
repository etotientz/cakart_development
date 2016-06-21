package test.com.suphalam.util;


import com.google.api.services.analytics.model.GaData;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.google.api.services.analytics.Analytics;
import com.suphalam.util.ConfigGA;

import org.apache.log4j.Logger;

public class CookieTest {
	
	//get the logger
	private static final transient Logger log = Logger.getLogger(CookieTest.class);
	
	public static void main(String [] args) throws Exception {

    	Analytics analytics = null;
    	//System.out.println(" 25    MAIN CLASS");
		try {
			analytics = ConfigGA.initializeAnalytics();
			//System.out.println(" 28    MAIN CLASS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String profile = ConfigGA.getFirstProfileId(analytics);
    	log.info(" the profile is - "+profile);
    	//System.out.println(" 34 of test class");
    	
    	//System.out.println(" ---41");
    	/*GaData results = analytics.data().ga()
    	        .get("ga:" + profile, "2016-06-14", "2016-06-14", "ga:pageviews")
    	        .setDimensions("ga:dimension2,ga:dimension4")
    	        .setMaxResults(10000)
    	        .execute();
    	*/
    	GaData results = analytics.data().ga()
        .get("ga:" + profile, "2016-06-14", "2016-06-14", "ga:pageviews")
        .setDimensions("ga:dimension4")
        .setMaxResults(10000)
        .execute();
    	//System.out.println(" ---41");
    	if (results != null && !results.getRows().isEmpty()) {
	    	
	    	int dataSize = results.getRows().size();
	    
	    	
	      //now iterate through each row in the result
	      int i= 0;
	      for (Iterator<List<String>> iterator = results.getRows().iterator(); iterator.hasNext();) {
	    	  
	    	List<String> eachRow = (List<String>)iterator.next();
	    	  
	    	//System.out.println(i +" => " + eachRow.get(0) +" | " +eachRow.get(1)+" | " +eachRow.get(2));
	    	//System.out.println(i +" => " + eachRow.get(0) +" | " +eachRow.get(1));
	    	  i++;
	    	 
	      }//row-loop
	    } else {
	      log.info("No results found");
	    }
    	

	}

}
