package com.suphalam.analytics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.util.Properties;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.analytics.Analytics;

import com.google.api.services.analytics.AnalyticsScopes;


/*
 * This class will have analytics configuration including oAuth call
 */
public class Configuration {
	
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	
	//load the property file
	private static Properties props = new Properties();
	
	//set the basic configurations
	//APPLICATION_NAME = "Hello Analytics";
	private static String APPLICATION_NAME = props.getProperty("APPLICATION_NAME");
	
	
	//KEY_FILE_LOCATION "C://Users//Hiresh.Roy//Documents//GitHub//Analytics101//src//com//cakartdata//client_secrets.p12";
	private static String KEY_FILE_LOCATION = props.getProperty("KEY_FILE_LOCATION");
	
	//SERVICE_ACCOUNT_EMAIL = "110902564229-23fgr8rlflsr8653qdnbinf9oqqoj8vu@developer.gserviceaccount.com";
	private static String SERVICE_ACCOUNT_EMAIL = props.getProperty("SERVICE_ACCOUNT_EMAIL");
	
	
	/*
	 * old method ************* remove it later *****************
	 * This method load the property from the class path
	 */
	public static Properties analyticsConfiProperties(){
		
		//System.out.println(System.getProperties());
		Properties props = new Properties();
		String propsPath = System.getProperty("user.dir")+"/bin/resources/"+"analytics.properties";
		try{
			props.load(new FileInputStream(propsPath));
		}
		catch(Exception ex){
			System.out.println("Unable to load the analytics properties file which has all the oAuth keys");
			ex.printStackTrace();
			return null;
			//exit();
		}//end-of-catch
		
		//return the props
		return props;
	}
	
	public static void analyticsConfiProperties(String path){
		

		String propsPath = path+"/analytics.properties";
		System.out.println("propsPath:-"+propsPath);
		try{
			Configuration.props.load(new FileInputStream(propsPath));
			Configuration.props.setProperty("PATH", path);
			Configuration.APPLICATION_NAME = props.getProperty("KEY_FILE_LOCATION");
			Configuration.KEY_FILE_LOCATION = path+"/"+props.getProperty("KEY_FILE_LOCATION");
			Configuration.SERVICE_ACCOUNT_EMAIL = props.getProperty("SERVICE_ACCOUNT_EMAIL");
			System.out.println("Property file is loaded:-"+props);
		}
		catch(Exception ex){
			System.out.println("Unable to load the analytics properties file which has all the oAuth keys");
			ex.printStackTrace();
			//return null;
			//exit();
		}//end-of-catch
		
		//return the props
		//return props;
	}
	/*
	 * Initialize analytics
	 */
	public static Analytics initializeAnalytics() throws Exception {
		
		System.out.println("SERVICE_ACCOUNT_EMAIL:-"+SERVICE_ACCOUNT_EMAIL);
		System.out.println("KEY_FILE_LOCATION:-"+KEY_FILE_LOCATION);
		System.out.println("APPLICATION_NAME:-"+APPLICATION_NAME);
	    // Initializes an authorized analytics service object.

	    // Construct a GoogleCredential object with the service account email
	    // and p12 file downloaded from the developer console.
		  HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
	    GoogleCredential credential = new GoogleCredential.Builder()
	        .setTransport(httpTransport)
	        .setJsonFactory(JSON_FACTORY)
	        .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
	        .setServiceAccountPrivateKeyFromP12File(new File(KEY_FILE_LOCATION))
	        .setServiceAccountScopes(AnalyticsScopes.all())
	        .build();

	    // Construct the Analytics service object.
	    return new Analytics.Builder(httpTransport, JSON_FACTORY, credential)
	        .setApplicationName(APPLICATION_NAME).build();
	  }//end-of-method
	
	
	/*
	 * get the first profile id
	 */
	public static String getFirstProfileId(Analytics analytics) throws IOException {
	    // Get the first view (profile) ID for the authorized user.
	    String profileId = null;

	    // Query for the list of all accounts associated with the service account.
	    com.google.api.services.analytics.model.Accounts accounts = analytics.management().accounts().list().execute();

	    if (accounts.getItems().isEmpty()) {
	      System.err.println("No accounts found");
	    } else {
	      String firstAccountId = accounts.getItems().get(0).getId();

	      // Query for the list of properties associated with the first account.
	      com.google.api.services.analytics.model.Webproperties properties = analytics.management().webproperties()
	          .list(firstAccountId).execute();

	      if (properties.getItems().isEmpty()) {
	        System.err.println("No Webproperties found");
	      } else {
	        String firstWebpropertyId = properties.getItems().get(0).getId();

	        // Query for the list views (profiles) associated with the property.
	        com.google.api.services.analytics.model.Profiles profiles = analytics.management().profiles()
	            .list(firstAccountId, firstWebpropertyId).execute();

	        if (profiles.getItems().isEmpty()) {
	          System.err.println("No views (profiles) found");
	        } else {
	          // Return the first (view) profile associated with the property.
	          profileId = profiles.getItems().get(0).getId();
	        }
	      }
	    }
	    return profileId;
	  }//end-of-profile-id
	
}//end-of-class
