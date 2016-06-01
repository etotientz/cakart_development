//STEP 1. Import required packages
import java.sql.*;
import java.util.*;
import java.io.*;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class Slug {
	
	
	//static String s_a ="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUV";
	
	
	static String str="Nul";
   static String store1="Nul";
   static String store2="Nul";
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/analytics";

  
   static final String USER = "root";
   static final String PASS = "spm";
   //static char e='a';
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
	   
	   LinkedHashSet<String> userid = new LinkedHashSet();
	   
      Class.forName("com.mysql.jdbc.Driver");

     
      System.out.println("Connecting to a selected database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected database successfully...");
      System.out.println("Creating statement...");
      stmt = conn.createStatement();

      String sql = "select slug,user_id from user_book order by slug;";
      ResultSet rs = stmt.executeQuery(sql);
      
      Configuration config = HBaseConfiguration.create();

      // Instantiating HTable class
      HTable hTable = new HTable(config, "User");
      //STEP 5: Extract data from result set
      int co=0;
      String comp="NOTA";
      while(rs.next()){
    	  String st=rs.getString("slug");
    	  if(st.toLowerCase().contains("ca-final") && st.toLowerCase().contains("direct-tax"))
    	
    	  {
    	  if(st.equals(comp)==false){
    		  
    		  if(co==1){ Put p = new Put(Bytes.toBytes(comp));
    		  char e='A';int it=0;
    		  for (String s : userid) {it++;
    			  System.out.println("Hey "+ e);

    		   e++;
    		  String f=""+ e;
    			  p.add(Bytes.toBytes("USER"),
    	        	      Bytes.toBytes(f),Bytes.toBytes(s));
    	        	      hTable.put(p);
    			   
    			}
    		 //String f="";
    		  e-=it;
    		  
    		  
    		  userid.clear();
    		 
    		  }
    		  Put p1 = new Put(Bytes.toBytes(st));
         String id1=rs.getString("user_id");
         p1.add(Bytes.toBytes("USER"),
        	      Bytes.toBytes("ID"),Bytes.toBytes(id1));
        
         
         hTable.put(p1);
         System.out.println("data inserted");
        
         System.out.print("slug name: " + st);
        System.out.println("user id "+ id1);
         
      co=0; }
    	  else{
    		  
    		  co=1;
    		  String id1=rs.getString("user_id");
    	  

    	  
    		  
    		  //String s="";
    		 // Put p = new Put(Bytes.toBytes(comp));
          userid.add(id1);
         
     
      }
        comp=st;}
     
      
      System.out.println("data Updated");
      hTable.close();rs.close();}}
   
      // closing HTable
      
    
   catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }// do nothing
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
      
   }//end try
  
   System.out.println("Goodbye!");
}//end main
}//end JDBCExample