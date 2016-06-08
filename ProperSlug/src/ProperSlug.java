import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.*;
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

public class ProperSlug {
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
   public static void main(String[] args)throws IOException {
	   Configuration config = HBaseConfiguration.create();

	   
	   
	   
	   HBaseAdmin admin = new HBaseAdmin(config);

	      // Instantiating table descriptor class
	      HTableDescriptor tableDescriptor = new
	      HTableDescriptor(TableName.valueOf("cakart"));

	      // Adding column families to table descriptor
	      tableDescriptor.addFamily(new HColumnDescriptor("user"));
	      tableDescriptor.addFamily(new HColumnDescriptor("asset"));
	      tableDescriptor.addFamily(new HColumnDescriptor("exam"));
	      tableDescriptor.addFamily(new HColumnDescriptor("subject"));
	      tableDescriptor.addFamily(new HColumnDescriptor("group"));
	      tableDescriptor.addFamily(new HColumnDescriptor("QA"));
	      tableDescriptor.addFamily(new HColumnDescriptor("blog"));

	      // Execute the table through admin
	      admin.createTable(tableDescriptor);
	      // Instantiating HTable class
	      HTable hTable = new HTable(config, "cakart");
	   duplicate="NULL";
	   String sql = "SELECT user_id,slug,date,hour,min FROM user_slug order by user_id,date,hour,min";
	   
	   ArrayList<ArrayList<String>> userLlist = DBManager.getResult(sql);
	   int i=0;count=1;
	  
	   String cu_type="Null";
	   for (ArrayList<String> arrayList : userLlist) {//System.out.println("\n" +i);i++;
		   String id = arrayList.get(0);
		   String sql1 = "SELECT cust_type from user_detail where cust_type LIKE '"+id+"'";  
		   ArrayList<ArrayList<String>>cust_type=DBManager.getResult(sql1);
		   for(ArrayList<String>arraycust:cust_type)
			   cu_type=arraycust.get(0);
		   
		   String slug = arrayList.get(1);
		   String date = arrayList.get(2);
		   String hour = arrayList.get(3);
		   String min = arrayList.get(4);
		   //String type=arrayList.get(5);
		   //System.out.println(id + " "+slug+" "+date + " "+ hour+" "+ min);
		  
         rowkeyid=id+date+hour+min;
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
			 String[] parts = slug.split("/");
			 String slug_part = parts[2];
			 //System.out.println("" + slug_part+ '\n');
			 String prefix = parts[1];
			 //System.out.println("" +prefix);
			
			 
			 UserSlugVO user = new UserSlugVO(id,slug_part, date,hour,min,prefix) ;
			 Put p = new Put(Bytes.toBytes(rowkey)); 
			  p.add(Bytes.toBytes("user"),
				      Bytes.toBytes("USERID"),Bytes.toBytes(id));
			  p.add(Bytes.toBytes("user"),
				      Bytes.toBytes("TYPE"),Bytes.toBytes(cu_type));
			 
			if(prefix.equals("books")){
				
				
				
				
				

			    

				 //System.out.println("HEY BOOKS");
	    		  String bookQuery = "Select book_id,book_title from book_slug where book_slug LIKE '"+slug_part+"' limit 1";
	    		  
	    		  ArrayList<ArrayList<String>> book = DBManager.getResult(bookQuery);
	    		  if (book != null && book.size() > 0){
	    			  ArrayList<String> tmp = book.get(0);
	    			  String book_id = tmp.get(0);
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
	    	  }
	    
	    	
		
	   }//System.out.println(i);
	   hTable.close();
}//end main
}//end FirstExample