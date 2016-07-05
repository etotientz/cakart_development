package com.suphalam.analytics;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;


import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.GaData;

import com.suphalam.analytics.Configuration;

//https://developers.google.com/analytics/devguides/reporting/core/dimsmets#view=detail&group=user_timings

/**
 * A simple example of how to access the Google Analytics API using a service
 * account.
 */
public class SignupReport {


	public static void main(String [] args){
		
		System.out.println(buildData());
		
	}
	
  public static SignupBean buildData() {
    try {
    	
    	//get the configuration
    	Analytics analytics = Configuration.initializeAnalytics();
    	String profile = Configuration.getFirstProfileId(analytics);
    	System.out.println("<buildData><37>First Profile Id: "+ profile);
    	
    	SignupBean signupBean = new SignupBean();
    	for(int i=8;i>0;i--){
    		
    		String dayString = String.valueOf(i-1)+"daysAgo";
    		System.out.println("<buildData><43>Day value "+ dayString);
    		if(i==8){
    			populateResultsStartDay(getVisitorResults(analytics, profile,dayString),signupBean,i);
    		}
    		else{
    			//day is passed as day-1 due to index
    			populateResultsRestDay(getVisitorResults(analytics, profile,dayString),signupBean,i);
    		}
    		System.out.println("<buildData><51>"+signupBean);
    	}
    	
    	
     
    	System.out.println(signupBean.getSignupByDayCount(0));
    	return signupBean;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  private static GaData getVisitorResults(Analytics analytics, String profileId, String day) throws IOException {
	  
	  System.out.println("<getVisitorResults><67>I am processing : "+ day);

	  //get all the new visitor but system is able to identify them with visitor track
	  return analytics.data().ga()
        .get("ga:" + profileId, day, day, "ga:sessions")
        .setDimensions("ga:dimension2,ga:city,ga:browser")
        .setSort("-ga:sessions")
        
        .setMaxResults(20000)
        .execute();
	  
	  //.setFilters("ga:newUsers==1")
	  
	  
	  
  }
  
 
 

  private static void populateResultsStartDay(GaData results, SignupBean signupBean, int day) {
	  
	int maxIdOfTheDay = 0;
	  
    // Parse the response from the Core Reporting API for
    // the profile name and number of sessions.
    if (results != null && !results.getRows().isEmpty()) {
    	
      //now iterate through each row in the result
      for (Iterator<List<String>> iterator = results.getRows().iterator(); iterator.hasNext();) {
    	  
    	List<String> eachRow = (List<String>)iterator.next();
    	  
    	  String visitorString = eachRow.get(0);			//this is the visitor id
    	  
    	  String visitorID = "";

    	  if(visitorString != null && visitorString.length() > 0 && visitorString.indexOf(":")!= -1 ){
    		  visitorID = visitorString.substring(0,visitorString.indexOf(":") );

	    	  if(Integer.parseInt(visitorID)>maxIdOfTheDay){
	    		  maxIdOfTheDay = Integer.parseInt(visitorID);
	    	  }
    	  }
      }//row-loop
    } else {
      System.out.println("<populateResultsStartDay>104..>No results found");
    }
    signupBean.setMaximumSignupID(day-1, maxIdOfTheDay);
    
  }
  
  
  private static void populateResultsRestDay(GaData results, SignupBean signupBean, int day) {
	  
	  System.out.println("<populateResultsRestDay> -------- day Value is - "+day +" ---------------");
	  
		int maxIdOfTheDay = 0;
		int counter  =0;
		LinkedHashMap<String,Integer> mapName = new LinkedHashMap<String,Integer>();
		LinkedHashMap<String,Integer> mapCity = new LinkedHashMap<String,Integer>();
		LinkedHashMap<String,Integer> mapDevice = new LinkedHashMap<String,Integer>();
	    // Parse the response from the Core Reporting API for
	    // the profile name and number of sessions.
	    if (results != null && !results.getRows().isEmpty()) {
	    	
	      //now iterate through each row in the result
	      for (Iterator<List<String>> iterator = results.getRows().iterator(); iterator.hasNext();) {
	    	  
	    	List<String> eachRow = (List<String>)iterator.next();
	    	  
	    	  String visitorString = eachRow.get(0);			//this is the visitor id
	    	  String city = eachRow.get(1);
	    	  String device = eachRow.get(2);
	    	  String sessionCount = eachRow.get(3);				//this is the session count
	    	  //System.out.println("<populateResultsRestDay>"+visitorString + " <> " +sessionCount);
	    	  String visitorID = "";
	    	  if(visitorString != null && visitorString.length() > 0 && visitorString.indexOf(":")!= -1 ){
	    		  visitorID = visitorString.substring(0,visitorString.indexOf(":") );
	    		  
	    		//consider the id only if it is bigger than the previous day stuff
		    	  if(Integer.parseInt(visitorID) > signupBean.getMaximumSignupID(day)){
		    		  counter++;
		    		  if(Integer.parseInt(visitorID)>maxIdOfTheDay){
			    		  maxIdOfTheDay = Integer.parseInt(visitorID);
			    	  }
		    		  mapName.put(visitorString, Integer.parseInt(sessionCount));
		    		  
		    		  // get the city count and add one extra
		    		  int cityCount = 0;
		    		  if(mapCity.get(city)!= null){
		    			  cityCount = mapCity.get(city);
		    			  cityCount++;
		    			  mapCity.put(city, cityCount);
		    		  }
		    		  else{
		    			  mapCity.put(city, 1);
		    		  }
		    		  
		    		// get the device count and add one extra
		    		  int deviceCount = 0;
		    		  if(mapDevice.get(device)!= null){
		    			  deviceCount = mapCity.get(city);
		    			  deviceCount++;
		    			  mapDevice.put(device, deviceCount);
		    		  }
		    		  else{
		    			  mapDevice.put(device, 1);
		    		  }
		    		  
		    	  	
		    	  	
	    	  }
	    	 
	    	  }//end-of-valid-loop
	      }//row-loop
	    } else {
	      System.out.println("<populateResultsRestDay>No results found");
	    }
	    int indexset = day-1;
	    System.out.println("<populateResultsRestDay><152> Index value is "+indexset);
	    signupBean.setMaximumSignupID(indexset, maxIdOfTheDay);
	    signupBean.setSignupByDayCount(indexset, counter);
	    System.out.println("<populateResultsRestDay> map:-"+mapName);
	    signupBean.setSignupByDayNameList(indexset, mapName);
	    signupBean.setSignupByCity(indexset, mapCity);
	    signupBean.setSignupByDevice(indexset, mapDevice);
	    
	  }
  
  
}
