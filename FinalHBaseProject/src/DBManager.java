import java.sql.*;
import java.util.ArrayList;

public class DBManager {

	
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/cakart";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "spm";
	   
	   public static ArrayList<ArrayList<String>> getResult (String query) {
		   Connection conn = null;
		   Statement stmt = null;
		  //System.out.println("The query is "+query);
		   
		   ArrayList<ArrayList<String>> lst = new ArrayList<ArrayList<String>>();
		   try{
			   
			   //System.out.println("The query is "+);
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");
	
		      //STEP 3: Open a connection
		      //System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	
		      //STEP 4: Execute a query
		      //System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		     // String sql;
		    //  sql = "SELECT user_id, slug, date, hour , min FROM user_slug";
		      ResultSet rs = stmt.executeQuery(query);
		      
		      java.sql.ResultSetMetaData meta = rs.getMetaData();
		      
		      int colCount = meta.getColumnCount();
	
		      //STEP 5: Extract data from result set
		      while(rs.next()){
		    	  //System.out.println(rs);
		    	  ArrayList<String> tmp = new ArrayList<String>();
		    	  for (int i = 1; i <= colCount; i++) {
		    		  //System.out.println(i);
		    		  
		    		  tmp.add(rs.getString(i));
					
		    	  }
		    	 // System.out.println("the row added is " + tmp);
		    	  lst.add(tmp);
		         
		      }
		      
		      rs.close();
		      stmt.close();
		      conn.close();
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
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   return lst;
	}//end main
}