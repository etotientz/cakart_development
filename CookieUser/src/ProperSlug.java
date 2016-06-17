import java.sql.Connection;
import java.lang.*;
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
	
	//private static final transient Logger log = Logger.getLogger(ProperSlug.class);
   // JDBC driver name and database URL
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/cakart";
   //LinkedHashSet<String> extra = new LinkedHashSet();

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "spm";
   static int i;
   static int count;
   static String duplicate;
   static String rowkeyid;
   static String rowkey;
   //static String modifiedDate;
   
   
   
   
   public static void main(String[] args)throws IOException {
	   SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd") ;
	    Date today=new Date();
	   String modifiedDate=format1.format(today);
	   System.out.println(modifiedDate);
	   Configuration config = HBaseConfiguration.create();
	   Analytics analytics = null;
	   try {
			analytics = ConfigGA.initializeAnalytics();
			//System.out.println(" 28    MAIN CLASS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   String profile = ConfigGA.getFirstProfileId(analytics);
   	log.info(" the profile is - "+profile);
   	GaData results = analytics.data().ga()
   	        .get("ga:" + profile, modifiedDate, modifiedDate, "ga:pageviews")
   	        .setDimensions("ga:date,ga:pagePath,ga:dimension2,ga:dimension4,ga:channelGrouping,ga:browser,ga:nthMinute")
   	        .setFilters("ga:pagePath=~/details$,ga:pagePath=~^/courses/,ga:pagePath=~^/blog/,ga:pagePath=~^/downloads/")
   	        .setSort("-ga:date,ga:dimension2,ga:dimension4,ga:browser,ga:channelGrouping,ga:nthMinute")
   	        .setMaxResults(10000)
   	        .execute();
	   
	   
	   
	  // HBaseAdmin admin = new HBaseAdmin(config);

	      // Instantiating table descriptor class
	    ////  HTableDescriptor tableDescriptor = new
	     // HTableDescriptor(TableName.valueOf("cakart"));

	      // Adding column families to table descriptor
	     /* tableDescriptor.addFamily(new HColumnDescriptor("user"));
	      tableDescriptor.addFamily(new HColumnDescriptor("asset"));
	      tableDescriptor.addFamily(new HColumnDescriptor("exam"));
	      tableDescriptor.addFamily(new HColumnDescriptor("subject"));
	      tableDescriptor.addFamily(new HColumnDescriptor("group"));
	      tableDescriptor.addFamily(new HColumnDescriptor("QA"));
	      tableDescriptor.addFamily(new HColumnDescriptor("blog"));
	      tableDescriptor.addFamily(new HColumnDescriptor("channel"));
	      tableDescriptor.addFamily(new HColumnDescriptor("browser"));
	      tableDescriptor.addFamily(new HColumnDescriptor("downloads"));*/

	      // Execute the table through admin
	      //admin.createTable(tableDescriptor);
	      // Instantiating HTable class
	      HTable hTable = new HTable(config, "cookie");
	   duplicate="NULL";
	  // String sql = "SELECT id,path,channel,browser,date,min FROM user_email order by id,date,min";
	   
	  // ArrayList<ArrayList<String>> userLlist = DBManager.getResult(sql);
	  // int i=0;count=1;
	   if (results != null && !results.getRows().isEmpty()) {
	    	
	    	int dataSize = results.getRows().size();
	   
	    	 int i= 0;
	  
	  // String cu_type="Null";
	   for (Iterator<List<String>> iterator = results.getRows().iterator(); iterator.hasNext();) {
		   
		   List<String> eachRow = (List<String>)iterator.next();
		   //System.out.println("\n" +i);i++;
	   
		   String id = eachRow.get(3);
		  /* String sql1 = "SELECT cust_type from user_detail where Id LIKE '"+id+"'";  
		   ArrayList<ArrayList<String>>cust_type=DBManager.getResult(sql1);
		   for(ArrayList<String>arraycust:cust_type)
			   cu_type=arraycust.get(0);*/
		   
		   String slug = eachRow.get(1);
		   String channel = eachRow.get(4);
		   String browser = eachRow.get(5);
		   String date = eachRow.get(0);
		   String user_id=eachRow.get(2);
		   //String hour = eachRow.get(5);
		   String min = eachRow.get(6);
		   //String type=eachRow.get(5);
		   //System.out.println(id + " "+slug+" "+date + " "+ hour+" "+ min);
		  if(id.equals("unknown-visitor"))
			  continue;
         rowkeyid=id;
        // System.out.println(rowkeyid);
      /*   if(duplicate.equals(rowkeyid)==true){
        	 count+=1;
        	rowkey=rowkeyid+"~"+Integer.toString(count);
        	 
         }
         else{
        	 count=1;
        	 rowkey=rowkeyid+"~"+Integer.toString(count);
         }
        		 
        		 duplicate=rowkeyid;*/
         
        // System.out.println(rowkey + "\n");
			 String[] parts = slug.split("/");
			 String prefix = parts[1];
			 if(prefix.equals("blog")|| prefix.equals("downloads")){
				 continue;}
			String slug_part = parts[2];
			 //System.out.println("" + slug_part+ '\n');
			
			 //System.out.println("" +prefix);
			
			 
			 UserSlugVO user = new UserSlugVO(id,user_id,date,min,channel,browser,prefix) ;
			 Put p = new Put(Bytes.toBytes(rowkeyid)); 
			  p.add(Bytes.toBytes("user"),
				      Bytes.toBytes("COOKIEID"),Bytes.toBytes(id));
			  p.add(Bytes.toBytes("user"),
				      Bytes.toBytes("USERID"),Bytes.toBytes(user_id));
			  //p.add(Bytes.toBytes("user"),
				//      Bytes.toBytes("TYPE"),Bytes.toBytes(cu_type));
			 p.add(Bytes.toBytes("channel"),
				      Bytes.toBytes("TYPE"),Bytes.toBytes(channel));
			  p.add(Bytes.toBytes("browser"),
				      Bytes.toBytes("TYPE"),Bytes.toBytes(browser));
			  
			 /* if(prefix.equals("blog"))
				  p.add(Bytes.toBytes("blog"),
					      Bytes.toBytes("TYPE"),Bytes.toBytes("1"));
			  if(prefix.equals("downloads"))
				  p.add(Bytes.toBytes("downloads"),
					      Bytes.toBytes("TYPE"),Bytes.toBytes("1"));
			 // hTable.put(p);*/
			/*if(prefix.equals("books")){
				
				
				
				
				

			    

				 //System.out.println("HEY BOOKS");
	    		 String bookQuery = "Select book_id,book_title from book_slug where book_slug LIKE '"+slug_part+"' limit 1";
	    		  
	    		  ArrayList<ArrayList<String>> book = DBManager.getResult(bookQuery);
	    		  if (book != null && book.size() > 0){
	    			  ArrayList<String> tmp = book.get(0);
	    			  String book_id = tmp.get(0);
	    			  p.add(Bytes.toBytes("asset"),
	    				      Bytes.toBytes("BOOK "),Bytes.toBytes("1"));
	    			  // adding values using add() method
				      // accepts column family name, qualifier/row name ,value
				      p.add(Bytes.toBytes("asset"),
				      Bytes.toBytes("BOOKID"),Bytes.toBytes(book_id));
				    //  hTable.put(p);
				     // System.out.println("data inserted");
	    			//  String book_title = tmp.get(1);
	    			 // System.out.println("BOOK_ID " +book_id);
	    			  String productQuery = "Select cat_id,catc_id,catb_id,'"+book_id+"' from product_detail p where '"+book_id+"'=p.product_id"
	    		    	 		+ " and p.p_type='Book'"; 	  
	    	    		  ArrayList<ArrayList<String>> book1 = DBManager.getResult(productQuery);
	    	    		  //System.out.println("The books array is "+ book1);
	    	    		  if (book1 != null && book1.size() > 0){
	    	    			  char e='A';char e2='A';char e3='A';
	    	    			  for (ArrayList<String> arrayList2 : book1) {
	    	    				  
	    	    				  if(arrayList2.get(0)!=null){
	    	    	    		      String exm_id=arrayList2.get(0);
	    	    	    		      
	    	    	        		  String f="EXAMID "+ e;e++;
	    	    	    		      
	    	    	    		      // adding values using add() method
	    	    				      // accepts column family name, qualifier/row name ,value
	    	    				      p.add(Bytes.toBytes("exam"),
	    	    				      Bytes.toBytes(f),Bytes.toBytes(exm_id));
	    	    	    		      System.out.println("EXAMID :" +exm_id);}
	    	    	    			 if(arrayList2.get(1)!=null){
	    	    	    				  
	    	    	    			  String sub_id = arrayList2.get(1);
	    	    	    			  String g="SUBID "+ e2;e2++;
	    	    	    			  
	    	    	    			  // adding values using add() method
	    	    				      // accepts column family name, qualifier/row name ,value
	    	    				      p.add(Bytes.toBytes("subject"),
	    	    				      Bytes.toBytes(g),Bytes.toBytes(sub_id));
	    	    	    			  System.out.println("SUBID :"+sub_id);}
	    	    	    			 if(arrayList2.get(2)!=null){ 
	    	    	    				 
	    	    	    			  String group_id = arrayList2.get(2);
	    	    	    			  String h="GROUPID "+ e2;e2++;
	    	    	    			  // adding values using add() method
	    	    				      // accepts column family name, qualifier/row name ,value
	    	    				      p.add(Bytes.toBytes("group"),
	    	    				      Bytes.toBytes(h),Bytes.toBytes(group_id));
	    	    	    			  System.out.println("GROUPID : "+group_id);}
	    	    	    			  hTable.put(p);
	    	    	    		      System.out.println("data inserted");
	    	    	    			  
							}
	    	    			  //ArrayList<String> tmp1 = book1.get(0);
	    	    			  
	    	    			
	    	    			 
	    	    			 
	    	    			  
	    	    			  
	    	    			   }
	    	    		  else{
	    	    			  System.out.println("empty ");
	    	    		  }
	    			  
	    			  
	    		  }
	    		  else{
	    			  System.out.println("the book slug not available :- " +slug_part );
	    		  }
	    		  
	    		  //get the book id
	    		  //based on book id you try to get the exam/subject etc
	    		  //then insert this record to HBase
	    	  }*/
			
			
			
			
			
			/*if(prefix.equals("courses")){
				
				
				
				
				

			    

				 //System.out.println("HEY BOOKS");
	    		  String courseQuery = "Select course_id,course_title from course_slug where course_slug LIKE '"+slug_part+"' limit 1";
	    		  
	    		  ArrayList<ArrayList<String>> course = DBManager.getResult(courseQuery);
	    		  if (course != null && course.size() > 0){
	    			  ArrayList<String> tmp = course.get(0);
	    			  String course_id = tmp.get(0);
	    			  // adding values using add() method
				      // accepts column family name, qualifier/row name ,value
				      p.add(Bytes.toBytes("asset"),
				      Bytes.toBytes("COURSEID"),Bytes.toBytes(course_id));
				    //  hTable.put(p);
				     // System.out.println("data inserted");
	    			//  String book_title = tmp.get(1);
	    			 // System.out.println("BOOK_ID " +book_id);
	    			  String productQuery = "Select cat_id,catc_id,catb_id,'"+course_id+"' from product_detail p where '"+course_id+"'=p.product_id"
	    		    	 		+ " and p.p_type='Course'"; 	  
	    	    		  ArrayList<ArrayList<String>> course1 = DBManager.getResult(productQuery);
	    	    		  //System.out.println("The books array is "+ book1);
	    	    		  if (course1 != null && course1.size() > 0){
	    	    			  char e='A';char e2='A';char e3='A';
	    	    			  for (ArrayList<String> arrayList2 : course1) {
	    	    				  
	    	    				  if(arrayList2.get(0)!=null){
	    	    	    		      String exm_id=arrayList2.get(0);
	    	    	    		      
	    	    	        		  String f="EXAMID "+ e;e++;
	    	    	    		      
	    	    	    		      // adding values using add() method
	    	    				      // accepts column family name, qualifier/row name ,value
	    	    				      p.add(Bytes.toBytes("exam"),
	    	    				      Bytes.toBytes(f),Bytes.toBytes(exm_id));
	    	    	    		      System.out.println("EXAMID :" +exm_id);}
	    	    	    			 if(arrayList2.get(1)!=null){
	    	    	    				  
	    	    	    			  String sub_id = arrayList2.get(1);
	    	    	    			  String g="SUBID "+ e2;e2++;
	    	    	    			  
	    	    	    			  // adding values using add() method
	    	    				      // accepts column family name, qualifier/row name ,value
	    	    				      p.add(Bytes.toBytes("subject"),
	    	    				      Bytes.toBytes(g),Bytes.toBytes(sub_id));
	    	    	    			  System.out.println("SUBID :"+sub_id);}
	    	    	    			 if(arrayList2.get(2)!=null){ 
	    	    	    				 
	    	    	    			  String group_id = arrayList2.get(2);
	    	    	    			  String h="GROUPID "+ e2;e2++;
	    	    	    			  // adding values using add() method
	    	    				      // accepts column family name, qualifier/row name ,value
	    	    				      p.add(Bytes.toBytes("group"),
	    	    				      Bytes.toBytes(h),Bytes.toBytes(group_id));
	    	    	    			  System.out.println("GROUPID : "+group_id);}
	    	    	    			  hTable.put(p);
	    	    	    		      System.out.println("data inserted");
	    	    	    			  
							}
	    	    			  //ArrayList<String> tmp1 = book1.get(0);
	    	    			  
	    	    			
	    	    			 
	    	    			 
	    	    			  
	    	    			  
	    	    			   }
	    	    		  else{
	    	    			  System.out.println("empty ");
	    	    		  }
	    			  
	    			  
	    		  }
	    		  else{
	    			  System.out.println("the book slug not available :- " +slug_part );
	    		  }
	    		  
	    		  //get the book id
	    		  //based on book id you try to get the exam/subject etc
	    		  //then insert this record to HBase
	    	  }*/
			
			 hTable.put(p);
	    	
		
	   }System.out.println(i);} else {
		      log.info("No results found");
		    }
	  //  hTable.close();
   }//end main
	    
}//end FirstExample