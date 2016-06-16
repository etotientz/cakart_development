package com.suphalam.analytics;

import java.io.IOException;

import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.GaData;

public class UserPaymentAlertService extends BookPurchaseAlertServices{

	private static GaData getVisitorResultsVisitedPayPage(Analytics analytics, String profileId) throws IOException {
		  
		  //get all the new visitor but system is able to identify them with visitor track
		  return analytics.data().ga()
	        .get("ga:" + profileId, "today", "today", "ga:hits")
	        .setDimensions("ga:dimension2")
	        .setFilters("ga:pagePath=@-add-kart-virtual-view")
	        .setSort("-ga:dimension2")
	        .setMaxResults(1000)
	        .execute();
	  }//end-of-method
}
