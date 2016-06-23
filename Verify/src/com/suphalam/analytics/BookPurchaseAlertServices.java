package com.suphalam.analytics;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.GaData;

import com.suphalam.analytics.bean.PageBean;
import com.suphalam.analytics.bean.VisitorBean;
import com.suphalam.util.ConfigGA;


/*
 * The logic which is required for this
 * 	1. Pull all the users who has visited the order/payment URL
 * 	2. For each of them iterate through and check if they have visited confirmation page
 * 	3. If not then was it 10min back or not
 * 	4. And then display them on the screen with complete path.
 */
public class BookPurchaseAlertServices {
	
	private static final transient Logger log = Logger.getLogger(BookPurchaseAlertServices.class);
	
	public static void main(String [] args) throws Exception{
		
		LinkedHashMap<String,VisitorBean> map =BookPurchaseAlertServices.buildData();
		 Iterator iter = map.keySet().iterator();
		 while(iter.hasNext()){
				String visitorID = (String)iter.next();
				VisitorBean bean = map.get(visitorID);
				bean.getVisitorID();
				bean.getTotalHit();
				bean.getVisitorID().substring(0,bean.getVisitorID().indexOf(":"));
				List urlList = bean.getPageList();
				int listLength = urlList.size();
				for (int i = 0; i < listLength; i++) {
					
				}
		 }
		 
	}
	
	 public static LinkedHashMap<String,VisitorBean>  buildData() throws Exception{
		//this data would be stored in HashMap.
		 LinkedHashMap<String,VisitorBean> map = new LinkedHashMap<String,VisitorBean>();
		 try {
			 
			 //get the analytics profile
			 Analytics analytics = ConfigGA.initializeAnalytics();
			 String profile = ConfigGA.getFirstProfileId(analytics);

			 //get the list of all users
			 String [] userList = populatUserList(getVisitorResultsVisitedPayPage(analytics, profile));

			 
			 
			 for(int k=0; k<userList.length;k++){
				 //now for each such users, check if they have made purchase or not
				 //if now tag them on alert
				  identifyTargetUser(getVisitorResults(analytics, profile,userList[k]),userList[k], map);
			 }
			 
			 //log.info(map);
			 
		    } catch (Exception e) {
		    	log.info("error occurred", e);
		      //e.printStackTrace();
		    }
		 
		 	return map;
		    
		  }


	 
	 /*
	  * Get all the users who has clicked on payment stuff
	 */
	  private static GaData getVisitorResultsVisitedPayPage(Analytics analytics, String profileId) throws IOException {
		  
		  //get all the new visitor but system is able to identify them with visitor track
		  return analytics.data().ga()
	        .get("ga:" + profileId, "today", "today", "ga:hits")
	        .setDimensions("ga:dimension2")
	        .setFilters("ga:pagePath=@-proceed-to-payment-virtual-view")
	        .setSort("-ga:dimension2")
	        .setMaxResults(1000)
	        .execute();
	  }//end-of-method
	  
	 /*
	  * Get the data for a specific user for today sort by time.
	  * Visitor ID = 87179: Vinod
	  */
	  private static GaData getVisitorResults(Analytics analytics, String profileId, String visitorID) throws IOException {
		  
		  
		  //get all the new visitor but system is able to identify them with visitor track
		  return analytics.data().ga()
	        .get("ga:" + profileId, "today",  "today", "ga:hits")
	        .setDimensions("ga:pagePath,ga:nthHour,ga:nthMinute,ga:city,ga:deviceCategory,ga:browser,ga:sourceMedium")
	        .setSort("-ga:nthMinute")
	        .setFilters("ga:dimension2=="+visitorID)
	        .setMaxResults(2000)
	        .execute();
	  }//end-of-method
		  
		 
	  /*
	   * this populates the data
	   */

	  private static String[] populatUserList(GaData results) {
		  
		String [] userList = null;
	    if (results != null && !results.getRows().isEmpty()) {
	    	
	    	int dataSize = results.getRows().size();
	    	userList = new String[dataSize];
	    	
	      //now iterate through each row in the result
	      int i= 0;
	      for (Iterator<List<String>> iterator = results.getRows().iterator(); iterator.hasNext();) {
	    	  
	    	List<String> eachRow = (List<String>)iterator.next();
	    	  
	    	  
	    	  userList[i] = eachRow.get(0);
	    	  i++;
	    	 
	      }//row-loop
	    } else {
	      log.info("No results found");
	    }
	    
	    return userList;
	    
	  }//end-of-class
	  
	  
	  private static LinkedHashMap<String,VisitorBean> identifyTargetUser(GaData results,String user,LinkedHashMap<String,VisitorBean> map) {
		  
		  	boolean alreadyBought = false;
			List<PageBean> list = new ArrayList<PageBean>();
			VisitorBean visitor = new VisitorBean();
			visitor.setVisitorID(user);
			
			if (results != null && !results.getRows().isEmpty()) {

		      for (Iterator<List<String>> iterator = results.getRows().iterator(); iterator.hasNext();) {
   
		    	List<String> eachRow = (List<String>)iterator.next();
		    	
		    	
		    	 String pagePath =  eachRow.get(0);
		    	 String hour = eachRow.get(1);
		    	 String min =  eachRow.get(2); 
		    	 int hrIn = Integer.parseInt(hour);
		    	 int minIn = Integer.parseInt(min)- (hrIn*60);
		    	 visitor.setLastTime(Integer.parseInt(hour)+":"+minIn+" Hr");
		    	 String city =  eachRow.get(3); visitor.setCity(city);
		    	 String device = eachRow.get(4); visitor.setDevice(device);
		    	 String browser = eachRow.get(5); visitor.setBrowser(browser);
		    	 String source = eachRow.get(6); visitor.setSource(source);
		    	 String hit = eachRow.get(7); visitor.setTotalHit(visitor.getTotalHit()+Integer.parseInt(hit));
		    	 
		    	 PageBean page = new PageBean(pagePath,hrIn, minIn,Integer.parseInt(hit));
		    	 list.add(page);
		    	 //if page path has payment confirmation page, means he has already purchased, else not.
		    	 if(pagePath.indexOf("/orders/confirm") != -1){
		    		 alreadyBought = true; 
		    	 }
		    	 
		      }//row-loop
		    }
			
			if(!alreadyBought){
				visitor.setPageList(list);
				map.put(user,visitor);
			}
		    
			return map;
		    
		  }//end-of-class

}
