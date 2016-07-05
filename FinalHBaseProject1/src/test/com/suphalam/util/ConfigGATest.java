package test.com.suphalam.util;


import com.google.api.services.analytics.model.GaData;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.google.api.services.analytics.Analytics;
import com.suphalam.util.ConfigGA;

import org.apache.log4j.Logger;

public class ConfigGATest {
	
	//get the logger
	private static final transient Logger log = Logger.getLogger(ConfigGATest.class);
	
	public static void main(String [] args) throws Exception {
		
		/*ClassLoader loader = ConfigGATest.class.getClassLoader();
        System.out.println(loader.getResource("test/com/suphalam/util/ConfigGATest.class"));
        
        ClassLoader classLoader = ConfigGATest.class.getClassLoader();
        File classpathRoot = new File(classLoader.getResource("").getPath());
        System.out.println(classpathRoot.getPath());*/
		//get the configuration
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
    	GaData results = analytics.data().ga()
        .get("ga:" + profile, "2016-06-14", "2016-06-14", "ga:pageviews")
        .setDimensions("ga:date,ga:pagePath,ga:dimension4,ga:channelGrouping,ga:browser,ga:nthMinute")
        .setFilters("ga:pagePath=~/details$,ga:pagePath=~^/courses/,ga:pagePath=~^/blog/,ga:pagePath=~^/downloads/")
        .setSort("-ga:date,ga:dimension4,ga:browser,ga:channelGrouping,ga:nthMinute")
        .setMaxResults(10000)
        .execute();
    	//System.out.println(" ---41");
    	if (results != null && !results.getRows().isEmpty()) {
	    	
	    	int dataSize = results.getRows().size();
	    
	    	
	      //now iterate through each row in the result
	      int i= 0;
	      for (Iterator<List<String>> iterator = results.getRows().iterator(); iterator.hasNext();) {
	    	  
	    	List<String> eachRow = (List<String>)iterator.next();
	    	  
	    	//System.out.println(" ---61--" + eachRow.get(0) +" <> " +eachRow.get(1)+" <> " +eachRow.get(2)+" <> " +eachRow.get(3)+" <> " +eachRow.get(4)+" <> " +eachRow.get(5)+" <> " +eachRow.get(6));
	    	  
	    	 
	      }//row-loop
	    } else {
	      log.info("No results found");
	    }
    	

	}

}
