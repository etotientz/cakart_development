//STEP 1. Import required packages
import java.sql.*;
import java.util.*;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class HBase2 {
	
	
	static String s_a ="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUV";
	
	//static int it=0;
	//static int iterator2=0;
	static String str="Nul";
   static String store1="Nul";
   static String store2="Nul";
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/CAKART_development";

  
   static final String USER = "root";
   static final String PASS = "spm";
   static char e='a';
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
	   
	   LinkedHashSet<String> hs_bid = new LinkedHashSet();
	   LinkedHashSet<String> hs_btitle = new LinkedHashSet();
	   LinkedHashSet<String> hs_pname = new LinkedHashSet();
	   LinkedHashSet<String> hs_aname = new LinkedHashSet();
      Class.forName("com.mysql.jdbc.Driver");

     
      System.out.println("Connecting to a selected database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected database successfully...");
      System.out.println("Creating statement...");
      stmt = conn.createStatement();

      String sql = "select exam.name, b.id, b.title, p.name, a.name from books b, publishers p, authors a, product_cats c , cats exam where b.publisher_id = p.id and b.author_id = a.id and c.product_type = 'Book' and c.product_id = b.id and c.cat_id is not null and exam.id = c.cat_id order by exam.name;";
      ResultSet rs = stmt.executeQuery(sql);
      
      Configuration config = HBaseConfiguration.create();

      // Instantiating HTable class
      HTable hTable = new HTable(config, "H4");
      //STEP 5: Extract data from result set
      int co=0;
      String comp="NOTA";
      while(rs.next()){
    	  String st=rs.getString("exam.name");
    	
    	  
    	  if(st.equals(comp)==false){
    		  
    		  if(co==1){ Put p = new Put(Bytes.toBytes(comp));
    		  
    		  for (String s : hs_bid) {int it=0;

    		  e = e ++;
    		  String f=""+ e;
    			  p.add(Bytes.toBytes("book_id"),
    	        	      Bytes.toBytes(f),Bytes.toBytes(s));it++;
    	        	      hTable.put(p);
    			   
    			}
    		  e='a';
    		  for (String s : hs_btitle) {int it=0;
    		  e = e++;
    			  p.add(Bytes.toBytes("b.title"),
    	        	      Bytes.toBytes(e),Bytes.toBytes(s));it++;
    	        	      hTable.put(p);
    			   
    			}
    		  e='a';
    		  for (String s : hs_pname) {int it=0;
    		  e++;
    			  p.add(Bytes.toBytes("publisher"),
    	        	      Bytes.toBytes(e),Bytes.toBytes(s));it++;
    	        	      hTable.put(p);
    			   
    			}e='a';
    		  for (String s : hs_aname) {int it=0;
    		  e ++;
			  p.add(Bytes.toBytes("author"),
	        	      Bytes.toBytes(e),Bytes.toBytes(s));it++;
	        	      hTable.put(p);
			   
			}e='a';
    		  
    		  hs_bid.clear();
    		  hs_btitle.clear();
    		  hs_pname.clear();
    		  hs_aname.clear();}
    		  Put p1 = new Put(Bytes.toBytes(st));
         String id1=rs.getString("b.id");
         p1.add(Bytes.toBytes("book_id"),
        	      Bytes.toBytes("B_ID"),Bytes.toBytes(id1));
         String title=rs.getString("b.title");
         p1.add(Bytes.toBytes("b.title"),
        	      Bytes.toBytes("B_Title"),Bytes.toBytes(title));
      
         String publisher= rs.getString("p.name");
         p1.add(Bytes.toBytes("publisher"),
        	      Bytes.toBytes("P_Name"),Bytes.toBytes(publisher));
         String author= rs.getString("a.name");
         p1.add(Bytes.toBytes("author"),
       	      Bytes.toBytes("A_Name"),Bytes.toBytes(author));
      
         
         hTable.put(p1);
         System.out.println("data inserted");
        
         System.out.print("exam name: " + st);
        System.out.println("book_id "+ id1);
         System.out.println(", Book Name: " + title);
         System.out.println(", PUB_Name: " + publisher);
         System.out.println(", A_Name: " + author);
        
      }
    	  else{
    		  
    		  co=1;
    		  String id1=rs.getString("b.id");
    	  
    	  String title=rs.getString("b.title");
    	  String publisher= rs.getString("p.name");
    	  String author= rs.getString("a.name");
    		  
    		  //String s="";
    		  Put p = new Put(Bytes.toBytes(comp));
          hs_bid.add(id1);
          hs_btitle.add(title);
          hs_pname.add(publisher);
          hs_aname.add(author);
     
      }
    
   
		 
     // hTable.put(p);
      
    
      
      comp=st;}
     
      
      System.out.println("data Updated");
      hTable.close();rs.close();}
   
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