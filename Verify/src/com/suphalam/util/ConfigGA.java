package com.suphalam.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.security.GeneralSecurityException;
import java.util.Properties;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.analytics.Analytics;

import com.google.api.services.analytics.AnalyticsScopes;


import org.apache.log4j.Logger;

import test.com.suphalam.util.ConfigGATest;


/*
 * This class will have analytics configuration including oAuth call
 */
public class ConfigGA {
	
	
	//get the logger
	private static final transient Logger log = Logger.getLogger(ConfigGA.class);
	
	//load the property file
	private static Properties props = null;
	private static String classPath = null;
	
	//the json factory
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	
	/*
	 * This is synchronize block which checks if prop file instantiated or not, if yes
	 * then it avoid instance creation
	 */
	public synchronized void analyticsConfiProperties() throws ConfigFileLoadingFailedExcep{
		
		//System.out.println(" 46   ConfigGA class");
		if(ConfigGA.props != null && ConfigGA.props.getProperty("KEY_FILE_LOCATION") != null){
			System.out.println("  48   If condition of ConfigGA class");
			log.info("The config props file is already loaded, same instance is returned");
		}
		else{
			try{
				//System.out.println(" 53   try of else block of ConfigGA class");
				ClassLoader classLoader = ConfigGA.class.getClassLoader();
				//System.out.println("55   try of else block of ConfigGA class classloader");
		        File classpathRoot = new File(classLoader.getResource("").getPath());
		       // System.out.println("57    try of else block of ConfigGA class classloader file");
		        ConfigGA.classPath = classpathRoot.getPath();
		       // System.out.println(" 59    try of else block of ConfigGA class classpath");
		        
				ConfigGA.props = new Properties();
				String propPath = ConfigGA.classPath+"/resources/configuration/analytics.properties";
				//System.out.println("63   try of else block of ConfigGA class propPath");
				
				log.info("Going to load the properties file from class folder:- " + propPath);
				//System.out.println("66   try of else block of ConfigGA class log info");
				ConfigGA.props.load(new FileInputStream(propPath));
				//System.out.println("68   try of else block of ConfigGA class prop load");
				log.info("file loaded now, check value :-" + ConfigGA.props.getProperty("KEY_FILE_LOCATION"));
				//System.out.println("70    try of else block of ConfigGA class log info");
			}
			catch(Exception ex){//System.out.println(" 72   catch of else block of ConfigGA class");
				throw new ConfigFileLoadingFailedExcep("Unable to load the analytics properties file which has all the oAuth related keys", ex);
			}//end-of-catch
		}//end-of-else

	}//end-of-class
	
	/*
	 * get the first profile id
	 */
	public static String getFirstProfileId(Analytics analytics) throws IOException {
		//System.out.println("getFirstProfile method");

	    // Get the first view (profile) ID for the authorized user.
	    String profileId = null;

	    // Query for the list of all accounts associated with the service account.
	    com.google.api.services.analytics.model.Accounts accounts = analytics.management().accounts().list().execute();

	    if (accounts.getItems().isEmpty()) {
	    	//System.out.println("92   getFirstProfile empty");
	    	log.error("No accounts is found");
	    } else {
	    	//System.out.println("95   else of log error");
	      String firstAccountId = accounts.getItems().get(0).getId();

	      // Query for the list of properties associated with the first account.
	      com.google.api.services.analytics.model.Webproperties properties = analytics.management().webproperties()
	          .list(firstAccountId).execute();

	      if (properties.getItems().isEmpty()) {
	    	  log.error("No Webproperties found");
	    	//  System.out.println("104   No Webproperties found");
	      } else {
	        String firstWebpropertyId = properties.getItems().get(0).getId();
	       
	        //System.out.println("108   else of No Webproperties found");
	        // Query for the list views (profiles) associated with the property.
	        com.google.api.services.analytics.model.Profiles profiles = analytics.management().profiles()
	            .list(firstAccountId, firstWebpropertyId).execute();
	       // System.out.println(" 112   else of No Webproperties found google api");
	        if (profiles.getItems().isEmpty()) { 
	        	//System.out.println("114  if after api block");
	        	log.error("No views (profiles) found");
	        } else {
	          // Return the first (view) profile associated with the property.
	          profileId = profiles.getItems().get(0).getId();
	         // System.out.println("119   else after api block");
	        }
	      }//System.out.println("121   terminate");
	    }
	    log.info("profile id found - " + profileId);
	    //System.out.println("124   before return ");
	    return profileId;
	    
	    
	  }//end-of-profile-id
	
	
	

	/*
	 * Initialize analytics
	 */
	public static Analytics initializeAnalytics() throws GAInitializationFailedExcep {
		//System.out.println("137    initialize analytics");
		
		//System.out.println("139     --------");
		ConfigGA configGA = null;
		try{
				configGA = 		new ConfigGA();
				configGA.analyticsConfiProperties();
		}
		catch(ConfigFileLoadingFailedExcep cflExcp){
			throw new GAInitializationFailedExcep("During initialization process, it failed",cflExcp);
		}
		
		
		String APPLICATION_NAME = ConfigGA.props.getProperty("APPLICATION_NAME");
		String KEY_FILE_LOCATION =ConfigGA.props.getProperty("KEY_FILE_LOCATION");
		String SERVICE_ACCOUNT_EMAIL =ConfigGA.props.getProperty("SERVICE_ACCOUNT_EMAIL");
		
		log.info("AppName= " + APPLICATION_NAME );
		log.info("KeyFile="+ConfigGA.classPath+"/"+KEY_FILE_LOCATION);
		log.info("tserviceacct="+SERVICE_ACCOUNT_EMAIL);
        
		
		try{
			//System.out.println(" 160   try 1st");
			 // Initializes an authorized analytics service object.

		    // Construct a GoogleCredential object with the service account email
		    // and p12 file downloaded from the developer console.
			  HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		    GoogleCredential credential = new GoogleCredential.Builder()
		        .setTransport(httpTransport)
		        .setJsonFactory(JSON_FACTORY)
		        .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
		        .setServiceAccountPrivateKeyFromP12File(new File(ConfigGA.classPath+"/"+KEY_FILE_LOCATION))
		        .setServiceAccountScopes(AnalyticsScopes.all())
		        .build();
		   // System.out.println("173    before return 1st");
		    // Construct the Analytics service object.
		    return new Analytics.Builder(httpTransport, JSON_FACTORY, credential)
		        .setApplicationName(APPLICATION_NAME).build();
		}
		catch(GeneralSecurityException gaGSE){
			throw new GAInitializationFailedExcep("GeneralSecurityException occurred",gaGSE);
		}
		catch(IOException ioe){
			throw new GAInitializationFailedExcep("IO Exception occurred",ioe);
		}
		catch(Exception excp){
			throw new GAInitializationFailedExcep("General occurred",excp);
		}
		//System.out.println("return 1st time");
	  }//end-of-method
	
	
	
	
}//end-of-class
