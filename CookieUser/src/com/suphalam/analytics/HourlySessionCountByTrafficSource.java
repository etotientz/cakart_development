package com.suphalam.analytics;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.GaData;

import com.suphalam.analytics.Configuration;
import com.suphalam.analytics.TrafficBySourceBean;


/**
 * A simple example of how to access the Google Analytics API using a service
 * account.
 */
public class HourlySessionCountByTrafficSource {


	
  public static LinkedHashMap<String,TrafficBySourceBean> buildData() {
    try {
    	
    	//get the configuration
    	Analytics analytics = Configuration.initializeAnalytics();
    	String profile = Configuration.getFirstProfileId(analytics);
    	System.out.println("First Profile Id: "+ profile);
    	
    	
    	
    	//this is the final list which will be returned.
    	LinkedHashMap<String,TrafficBySourceBean> list = new LinkedHashMap<String,TrafficBySourceBean>();
    	populateResults(getResults(analytics, profile),list);
    	
    	populateAnnoucementResult(getAnnoucementResults(analytics, profile),list);
     
    	//System.out.println(list);
    	return list;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  private static GaData getResults(Analytics analytics, String profileId) throws IOException {

	  // to understand how to write it
	  //https://developers.google.com/analytics/devguides/reporting/core/dimsmets#view=detail&group=custom_variables_or_columns&jump=ga_dimensionxx
	  // Query the Core Reporting API for the number of sessions
	  return analytics.data().ga()
        .get("ga:" + profileId, "today", "today", "ga:sessions")
        .setDimensions("ga:dateHour,ga:channelGrouping").
        setSort("ga:dateHour").
        setMaxResults(20000).
        execute();
  }
  
  private static GaData getAnnoucementResults(Analytics analytics, String profileId) throws IOException {

	  return analytics.data().ga()
		        .get("ga:" + profileId, "today", "today", "ga:sessions")
		        	.setDimensions("ga:dateHour")
		        	.setSort("ga:dateHour")
		        	.setFilters("ga:pagePath==/annoucements")
		        	.setMaxResults(20000).execute();
  }
  
 

  private static void populateResults(GaData results, LinkedHashMap<String,TrafficBySourceBean> listByRef) {
	  
    // Parse the response from the Core Reporting API for
    // the profile name and number of sessions.
    if (results != null && !results.getRows().isEmpty()) {
    	
      //now iterate through each row in the result
      for (Iterator<List<String>> iterator = results.getRows().iterator(); iterator.hasNext();) {
    	  
    	List<String> eachRow = (List<String>)iterator.next();
    	  
    	  String hourKey = eachRow.get(0);
    	  String sourceValue = eachRow.get(1);
    	  String sessionCountValue = eachRow.get(2);
    	  
    	  TrafficBySourceBean beanFromList = listByRef.get(hourKey) ;
    	  
    	  //construct the bean if it is null in the list and put it back once filled.
    	  if(beanFromList == null){
    		  beanFromList = new TrafficBySourceBean(hourKey);
    		 
    	  }
    	  
    	  if(sourceValue.equals("Direct")){
			  beanFromList.setDirectSessionCount(Integer.parseInt(sessionCountValue));
		  }
    	  else if (sourceValue.equals("Email")){
			  beanFromList.setEmailSessionCount(Integer.parseInt(sessionCountValue));
		  }
    	  else if (sourceValue.equals("Organic Search")){
			  beanFromList.setOrganicSearchSessionCount(Integer.parseInt(sessionCountValue));
		  }
    	  else if(sourceValue.equals("Referral")){
			  beanFromList.setReferralSessionCount(Integer.parseInt(sessionCountValue));
		  }
    	  else if(sourceValue.equals("Social")){
			  beanFromList.setSocialSessionCount(Integer.parseInt(sessionCountValue));
		  }
    	  else{
    		  int currentValue = beanFromList.getOrganicSearchSessionCount();
    		  currentValue = currentValue+ Integer.parseInt(sessionCountValue);
    		  beanFromList.setNotSetSessionCount(currentValue); 
    	  }
    	  listByRef.put(hourKey,beanFromList);

      }//row-loop
    } else {
      System.out.println("No results found");
    }
  }
  
  private static void populateAnnoucementResult(GaData results, LinkedHashMap<String,TrafficBySourceBean> listByRef) {
	  
	    // Parse the response from the Core Reporting API for
	    // the profile name and number of sessions.
	    if (results != null && !results.getRows().isEmpty()) {
	    	
	      //now iterate through each row in the result
	      for (Iterator<List<String>> iterator = results.getRows().iterator(); iterator.hasNext();) {
	    	  
	    	List<String> eachRow = (List<String>)iterator.next();
	    	  
	    	  String hourKey = eachRow.get(0);
	    	  String sessionCountValue = eachRow.get(1);
	    	  
	    	  TrafficBySourceBean beanFromList = listByRef.get(hourKey) ;
	    	  
	    	  //construct the bean if it is null in the list and put it back once filled.
	    	  if(beanFromList == null){
	    		  beanFromList = new TrafficBySourceBean(hourKey);
	    		 
	    	  }

			  beanFromList.setAnnoucementSessionCount(Integer.parseInt(sessionCountValue));
			  
	    	  listByRef.put(hourKey,beanFromList);

	      }//row-loop
	    } else {
	      System.out.println("No results found for announcement");
	    }
	  }
}
