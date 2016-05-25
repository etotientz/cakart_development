//STEP 1. Import required packages
import java.sql.*;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class JDBCExample {
	String P[]={"P_NAME2","P_NAME3","P_NAME4"};
	String A[]={"A_NAME2","A_NAME3","A_NAME4","A_NAME5"};
	
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/CAKART_development";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "spm";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to a selected database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected database successfully...");
      
      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();

      String sql = "SELECT b.id, b.title, p.name as publisher, a.name as author from books b, publishers p, authors a where b.publisher_id = p.id and b.author_id = a.id order by b.id";
      ResultSet rs = stmt.executeQuery(sql);
      
      Configuration config = HBaseConfiguration.create();

      // Instantiating HTable class
      HTable hTable = new HTable(config, "HTa");
      //STEP 5: Extract data from result set
String comp="NOTA";
      while(rs.next() && rs.getString("b.id")!=comp){
         //Retrieve by column name
         String id  = rs.getString("b.id");
         Put p = new Put(Bytes.toBytes(id)); 
         //int age = rs.getInt("age");
         String title=rs.getString("b.title");
         p.add(Bytes.toBytes("Title"),
        	      Bytes.toBytes("B_Title"),Bytes.toBytes(title));
        // String first = rs.getString("name");
         String publisher= rs.getString("publisher");
         p.add(Bytes.toBytes("Publisher"),
        	      Bytes.toBytes("P_Name"),Bytes.toBytes(publisher));
        
         String author = rs.getString("author");
         p.add(Bytes.toBytes("Author"),
       	      Bytes.toBytes("A_Name"),Bytes.toBytes(author));
        // String last = rs.getString("last");
         comp=id;
         hTable.put(p);
         System.out.println("data inserted");
         //Display values
         System.out.print("ID: " + id);
        // System.out.print(", Age: " + age);
         //System.out.print(", First: " + first);
         System.out.println(", Book Name: " + title);
         System.out.println(", PUB_Name: " + publisher);
         System.out.println(", A_Name: " + author);
      }
      if(rs.getString("b.id")==comp)
      {
    	  
      }
      rs.close();
      hTable.close();
   }catch(SQLException se){
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