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
  	HTable hTable = new HTable(config, "cakart1");
	   
	   for(int pday=20;pday<y;pday++){
		   if(pday<10){ alldate="2016-06-0"+pday;
		  }
		   else{alldate="2016-06-"+pday;}
		   
		   
	  
	  
	   System.out.println(alldate);
	   for(int phour=00;phour<24;phour++){
		   if(phour<10)
		   hours="0"+phour;
		   else
		   hours=""+phour;
		  
		   
		   System.out.println(hours);
		 
	   
	   
	  
	  
	   
	 
   	String momentdate="ga:date=="+alldate;
    String momenthour=",ga:hour=="+hours;
    String moment=momentdate+momenthour;
   	GaData results = analytics.data().ga()
   	        .get("ga:" + profile,alldate,alldate, "ga:pageviews")
   	        .setDimensions("ga:date,ga:pagePath,ga:dimension4,ga:channelGrouping,ga:browser,ga:nthMinute")//ga:deviceCategory")
   	        .setFilters(moment)
   	      
   	       // .setSort("-ga:date,ga:dimension4,ga:browser,ga:channelGrouping,ga:nthMinute")
   	        .setMaxResults(10000)
   	        .execute();
 
	   
	   
	   
	 
	      
	   duplicate="NULL";
	  // String sql = "SELECT id,path,channel,browser,date,min FROM user_email order by id,date,min";
	   
	  // ArrayList<ArrayList<String>> userLlist = DBManager.getResult(sql);
	  // int i=0;count=1;
	   if (results != null && !results.getRows().isEmpty()) {
	    	
	    	int dataSize = results.getRows().size();
	   
	    	 int i= 0;
	  
	  // String cu_type="Null";
	   for (Iterator<List<String>> iterator = results.getRows().iterator(); iterator.hasNext();)
	   {
		   
		   List<String> eachRow = (List<String>)iterator.next();
		   //System.out.println("\n" +i);i++;
	   
		   //String id = eachRow.get(3);
		  /* String sql1 = "SELECT cust_type from user_detail where Id LIKE '"+id+"'";  
		   ArrayList<ArrayList<String>>cust_type=DBManager.getResult(sql1);
		   for(ArrayList<String>arraycust:cust_type)
			   cu_type=arraycust.get(0);*/
		   
		   String slug = eachRow.get(1);
		   String channel = eachRow.get(3);
		   String browser = eachRow.get(4);
		   String date = eachRow.get(0);
		   String cookie_id=eachRow.get(2);
		   //String hour = eachRow.get(5);
		   String min = eachRow.get(5);
		   //String type=eachRow.get(5);
		//   System.out.println(cookie_id + " "+slug+" "+date +" "+ min);
		  if(cookie_id.equals("unknown-visitor"))
			 continue;
         rowkeyid=date+min+cookie_id;
         
        // System.out.println(rowkeyid);
         if(duplicate.equals(rowkeyid)==true){
        	 count+=1;
        	rowkey=rowkeyid+"~"+Integer.toString(count);
        	 
         }
         else{
        	 count=1;
        	 rowkey=rowkeyid+"~"+Integer.toString(count);
         }
        		 
        		 duplicate=rowkeyid;
         
        // System.out.println(rowkey + "\n");
        		 String a1="/books/";String a2="/courses/";String a3="/blog/";
        		 String a4="/downloads/";String a5="ambassadors_account_dashboard";
        		 String a6="-add-kart-virtual-view";
        		 //String a7="/community.cakart.in/";
        		 //System.out.println("----------"+slug+ "\n"+moment +"\n" );
        		 String[] parts = slug.split("/");
     			int size=parts.length;
     			if(size>2){ 
			if(slug.contains(a1)||slug.contains(a2)||slug.contains(a3)||slug.contains(a4)||slug.contains(a5)||slug.contains(a6)||parts[1].matches("[0-9]+")||
					parts[1].matches("jobs")||parts[1].matches("why2join")||
					parts[1].matches("questions")||parts[1].matches("jobs")||parts[1].matches("ask")||parts[1].matches("unanswered")||
					parts[1].matches("tag"))
			{ Put p = new Put(Bytes.toBytes(rowkey)); 
			System.out.println(slug);
			 String prefix = parts[1];
			 System.out.println("0 index:"+parts[0]+"hi");
			 System.out.println("PREFIX 1 index:"+prefix );
			// if(parts[2]=="NULL"){continue;}
			String slug_part = parts[2];
			System.out.println("SlUG part[2]:"+slug_part );
			 //System.out.println("" + slug_part+ '\n');
			
			 //System.out.println("" +prefix);
			
			
			 UserSlugVO user = new UserSlugVO(cookie_id,slug,date,min,channel,browser,prefix) ;
			
			// if (.matches("[0-9]+") && text.length() > 2) {
			 
			 if(prefix.equals("jobs")==true){System.out.println("---------Hey----------"+ slug_part);
				// String value=parts[2];
				 p.add(Bytes.toBytes("QnA"),
					      Bytes.toBytes("JOBFLAG"),Bytes.toBytes("1"));}
			 if(prefix.equals("tag")==true){System.out.println("---------Hey----------"+ slug_part);
				// String value=parts[2];
				 p.add(Bytes.toBytes("QnA"),
					      Bytes.toBytes("TAGFLAG"),Bytes.toBytes("1"));}
			 if(prefix.equals("ask")==true){System.out.println("---------Hey----------"+ slug_part);
				// String value=parts[2];
				 p.add(Bytes.toBytes("QnA"),
					      Bytes.toBytes("ASKFLAG"),Bytes.toBytes("1"));}
			 if(prefix.equals("questions")==true){System.out.println("---------Hey----------"+ slug_part);
				// String value=parts[2];
				 p.add(Bytes.toBytes("QnA"),
					      Bytes.toBytes("QFLAG"),Bytes.toBytes("1"));}
			 if(prefix.equals("unanswered")==true){System.out.println("---------Hey----------"+ slug_part);
				// String value=parts[2];
				 p.add(Bytes.toBytes("QnA"),
					      Bytes.toBytes("UANSFLAG"),Bytes.toBytes("1"));}
			 if(prefix.equals("why2join")==true){System.out.println("---------Hey----------"+ slug_part);
				// String value=parts[2];
				 p.add(Bytes.toBytes("QnA"),
					      Bytes.toBytes("JOINFLAG"),Bytes.toBytes("1"));}
				 
				 if(prefix.matches("[0-9]+")){System.out.println("---------Hey----------"+ slug_part);
					// String value=parts[2];
					 p.add(Bytes.toBytes("QnA"),
						      Bytes.toBytes("QUESTIONFLAG"),Bytes.toBytes("1"));}
			 if(prefix.equals(a5)==true){System.out.println("---------Hey----------"+ slug_part);
				// String value=parts[2];
				 p.add(Bytes.toBytes("ambassador"),
					      Bytes.toBytes("AMBASSADORID"),Bytes.toBytes(slug_part));
				 
			 }
			 if(slug.contains(a6)){System.out.println("---------Hey----------"+ slug_part);
				// String value=parts[2];
				 p.add(Bytes.toBytes("ADD-KART"),
					      Bytes.toBytes("FLAG"),Bytes.toBytes("1"));
				 
			 }
			 

			  p.add(Bytes.toBytes("user"),
				      Bytes.toBytes("COOKIEID"),Bytes.toBytes(cookie_id));
			  p.add(Bytes.toBytes("user"),
				      Bytes.toBytes("DATE"),Bytes.toBytes(date));
			  p.add(Bytes.toBytes("user"),
				      Bytes.toBytes("MIN"),Bytes.toBytes(min));
			  p.add(Bytes.toBytes("user"),
				      Bytes.toBytes("URL"),Bytes.toBytes(slug));
			
			  p.add(Bytes.toBytes("channel"),
				      Bytes.toBytes("TYPE"),Bytes.toBytes(channel));
			  p.add(Bytes.toBytes("browser"),
				      Bytes.toBytes("TYPE"),Bytes.toBytes(browser));
			  
			  if(prefix.equals("blog"))
				  p.add(Bytes.toBytes("blog"),
					      Bytes.toBytes("TYPE"),Bytes.toBytes("1"));
			  if(prefix.equals("downloads"))
				  p.add(Bytes.toBytes("asset_type"),
					      Bytes.toBytes("DOWNLOAD"),Bytes.toBytes("1"));
			 // hTable.put(p);
			if(prefix.equals("books")){
				
				
				
				 p.add(Bytes.toBytes("asset_type"),
					      Bytes.toBytes("BOOK"),Bytes.toBytes("1"));
				

			    

				 //System.out.println("HEY BOOKS");
	    		 String bookQuery = "Select book_id,book_title from book_slug where book_slug LIKE '"+slug_part+"' limit 1";
	    		  
	    		  ArrayList<ArrayList<String>> book = DBManager.getResult(bookQuery);
	    		  if (book != null && book.size() > 0){
	    			  ArrayList<String> tmp = book.get(0);
	    			  String book_id = tmp.get(0);
	    			  p.add(Bytes.toBytes("asset_id"),
	    				      Bytes.toBytes("ID"),Bytes.toBytes(book_id));
	    			 
	    			  String AuthorQuery = "Select author_id from books where '"+book_id+"'=books.id";
	    		    	 		//+ " and p.product_type='Book'"; 	  
	    	    		  ArrayList<ArrayList<String>> book2 = DBManager.getResult(AuthorQuery);
	    	    		  //System.out.println("The books array is "+ book1);
	    	    		  if (book2 != null && book2.size() > 0){
	    	    			  char e='A';//char e2='A';char e3='A';
	    	    			  for (ArrayList<String> arrayList2 : book2) {
	    	    				  
	    	    				  if(arrayList2.get(0)!=null){
	    	    	    		      String exm_id=arrayList2.get(0);
	    	    	    		      
	    	    	        		  String f="AUTHORID "+ e;e++;
	    	    	    		      
	    	    	    		      // adding values using add() method
	    	    				      // accepts column family name, qualifier/row name ,value
	    	    				      p.add(Bytes.toBytes("AUTHOR"),
	    	    				      Bytes.toBytes(f),Bytes.toBytes(exm_id));
	    	    	    		      System.out.println("AUTHORID :" +exm_id);}}}
	    			
	    			 // System.out.println("BOOK_ID " +book_id);
	    			  String productQuery = "Select cat_id,catc_id,catb_id,'"+book_id+"' from product_cats p where '"+book_id+"'=p.product_id"
	    		    	 		+ " and p.product_type='Book'"; 	  
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
	    		  }
			
			
			
			
			
			if(prefix.equals("courses")){
				
				 p.add(Bytes.toBytes("asset_type"),
					      Bytes.toBytes("COURSE"),Bytes.toBytes("1"));
				
				
				

			    

				 //System.out.println("HEY BOOKS");
	    		  String courseQuery = "Select course_id,course_title from course_slug where course_slug LIKE '"+slug_part+"' limit 1";
	    		  
	    		  
	    		  
	    		  ArrayList<ArrayList<String>> course = DBManager.getResult(courseQuery);
	    		  if (course != null && course.size() > 0){
	    			  ArrayList<String> tmp = course.get(0);
	    			  String course_id = tmp.get(0);
	    			  
	    			  // adding values using add() method
				      // accepts column family name, qualifier/row name ,value
				      p.add(Bytes.toBytes("asset_id"),
				      Bytes.toBytes("ID"),Bytes.toBytes(course_id));
				      
				      String FacultyQuery = "Select faculty_id from courses where courses.id LIKE '"+course_id+"'";
		    	 		//+ " and p.product_type='Book'"; 	  
	  		  ArrayList<ArrayList<String>> book2 = DBManager.getResult(FacultyQuery);
	  		  //System.out.println("The books array is "+ book1);
	  		  if (book2 != null && book2.size() > 0){
	  			  char e='A';//char e2='A';char e3='A';
	  			  for (ArrayList<String> arrayList2 : book2) {
	  				  
	  				  if(arrayList2.get(0)!=null){
	  	    		      String exm_id=arrayList2.get(0);
	  	    		      
	  	        		  String f="FACULTYID "+ e;e++;
	  	    		      
	  	    		      // adding values using add() method
	  				      // accepts column family name, qualifier/row name ,value
	  				      p.add(Bytes.toBytes("FACULTY"),
	  				      Bytes.toBytes(f),Bytes.toBytes(exm_id));
	  	    		      System.out.println("FACULTYID :" +exm_id);}}}
		    		  
				   
	    			  String productQuery = "Select cat_id,catc_id,catb_id,'"+course_id+"' from product_cats p where '"+course_id+"'=p.product_id"
	    		    	 		+ " and p.product_type='Course'"; 	  
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
	    	  }
			
			if(prefix.equals("downloads")){
				if(parts[2].equals("new")){ p.add(Bytes.toBytes("uploads"),
					      Bytes.toBytes("FLAG"),Bytes.toBytes("1"));}else{
				
				 p.add(Bytes.toBytes("asset_type"),
					      Bytes.toBytes("DOWNLOAD"),Bytes.toBytes("1"));
				
				
	    		  String downQuery = "Select id,title from downloads where slug LIKE '"+slug_part+"' limit 1";
	    		  
	    		  ArrayList<ArrayList<String>> down = DBManager.getResult(downQuery);
	    		  if (down != null && down.size() > 0){
	    			  ArrayList<String> tmp = down.get(0);
	    			  String download_id = tmp.get(0);
	    			  // adding values using add() method
				      // accepts column family name, qualifier/row name ,value
				      p.add(Bytes.toBytes("asset_id"),
				      Bytes.toBytes("ID"),Bytes.toBytes(download_id));
				    //  hTable.put(p);
				     // System.out.println("data inserted");
	    			//  String book_title = tmp.get(1);
	    			 // System.out.println("BOOK_ID " +book_id);
	    			  String productQuery = "Select cat_id,catc_id,catb_id,'"+download_id+"' from product_cats p where '"+download_id+"'=p.product_id"
	    		    	 		+ " and p.product_type='Download'"; 	  
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
	    	  }
			}
		
			 hTable.put(p);	} }}System.out.println(i);  } else {
		      log.info("No results found");
		    }
	  // 
	  } }  hTable.close(); }//end main
	    
}//end FirstExample