import java.sql.Connection;
import java.lang.*;
import java.util.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.*;
import java.text.*;
import java.io.IOException;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.TableName;

import org.apache.hadoop.conf.Configuration;


//import org.apache.hadoop.conf.Configuration;

//import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.model.GaData;
import com.suphalam.util.ConfigGA;

import test.com.suphalam.util.ConfigGATest;
import java.util.*;
public class ProperSlug {
	private static final transient Logger log = Logger.getLogger(ProperSlug.class);
	
	
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/cakart";
   

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "spm";
   static int i;
   static int count;
   static String duplicate;
   static String rowkeyid;
   static String rowkey;
   static String alldate,hours;
   //static String modifiedDate;
   
   
   
   
   public static void main(String[] args)throws IOException {
	 
	   SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd") ;
	    Date today=new Date();
	   String modifiedDate=format1.format(today);
	  Calendar rightNow = Calendar.getInstance();
	  int hour = rightNow.get(Calendar.HOUR_OF_DAY);
	
	   String[] dates = modifiedDate.split("-");
	   
	   int y = Integer.parseInt(dates[2]);
	  
	  // System.out.println(y);
	   Configuration config = HBaseConfiguration.create();
	   Analytics analytics = null;
	   try {
			analytics = ConfigGA.initializeAnalytics();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   String profile = ConfigGA.getFirstProfileId(analytics);
  	log.info(" the profile is - "+profile);
  	HTable hTable = new HTable(config, "verifytab");
	
  	GaData results = analytics.data().ga()
   	        .get("ga:" + profile,"2016-06-20","2016-06-22", "ga:pageviews")
   	        .setDimensions("ga:pagePath,ga:dimension4,ga:channelGrouping,ga:browser,ga:date,ga:nthMinute")
   	        .setFilters("ga:pagePath=~^/courses/;ga:pagePath=@advanced-management-accounting")
   	        .setMaxResults(10000)
   	        .execute();
  	
  	if (results != null && !results.getRows().isEmpty()) {
    	int dataSize = results.getRows().size();
    	System.out.println(dataSize);
  	}  	
  	
  	
    for (Iterator<List<String>> iterator = results.getRows().iterator(); iterator.hasNext();)
	   {
		   
		   List<String> eachRow = (List<String>)iterator.next();
		   //System.out.println("\n" +i);i++;
	   
		   //String id = eachRow.get(3);
		  /* String sql1 = "SELECT cust_type from user_detail where Id LIKE '"+id+"'";  
		   ArrayList<ArrayList<String>>cust_type=DBManager.getResult(sql1);
		   for(ArrayList<String>arraycust:cust_type)
			   cu_type=arraycust.get(0);*/
		   
		   String slug = eachRow.get(0);
		   String cookie = eachRow.get(1);
		   String date=eachRow.get(4);
		   String min=eachRow.get(5);
		   System.out.println(slug+"\n"+cookie+"\n"+date+"\n"+min+"\n");
		   
		   
	   } }
	    
}//end FirstExample