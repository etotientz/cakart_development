import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProperSlug {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/cakart";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "spm";
   
   public static void main(String[] args) {
	   
	   String sql = "SELECT user_id, slug, date, hour , min FROM user_slug";
	   
	   ArrayList<ArrayList<String>> userLlist = DBManager.getResult(sql);
	   
	   for (ArrayList<String> arrayList : userLlist) {
		   String id = arrayList.get(0);
		   String slug = arrayList.get(1);
		   String date = arrayList.get(2);
		   String hour = arrayList.get(3);
		   String min = arrayList.get(4);

			 String[] parts = slug.split("/");
			 String slug_part = parts[2]; // 004-
			 String prefix = parts[1];
			 
			 UserSlugVO user = new UserSlugVO(id,slug_part, date,hour,min,prefix) ;
			 
			 if(prefix.equalsIgnoreCase("books")){
	    		  String bookQuery = "Select book_id, title from book_slug where slug = '"+slug_part+"' limit 1";
	    		  
	    		  ArrayList<ArrayList<String>> book = DBManager.getResult(bookQuery);
	    		  if (book != null){
	    			  ArrayList<String> tmp = book.get(0);
	    			  String book_id = tmp.get(0);
	    			  String book_title = tmp.get(1);
	    			  
	    			  
	    		  }
	    		  
	    		  //get the book id
	    		  //based on book id you try to get the exam/subject etc
	    		  //then insert this record to HBase
	    	  }
	    	  
	    	/*  if(prefix.equalsIgnoreCase("course")){
 String bookQuery = "Select id, title from course_slug where slug = '"+slug_part+"' limit 1";
	    		  
	    		  ArrayList<ArrayList<String>> course = DBManager.getResult(bookQuery);
	    		  if (course != null){
	    			  ArrayList<String> tmp = course.get(0);
	    			  String course_id = tmp.get(0);
	    			  String course_title = tmp.get(1);
	    			  
	    			  
	    		  }
	    		  
	    		  
	    	  }*/
	    	  
	    	  String productQuery = "Select exam_id,sub_id,group_id from product_detail p,subject_detail s,book_slug,exam_detail e,group_details where book_slug.book_id=p.product_id and p.p_type='Book' and e.exam_id=p.cat_id and s.sub_id=p.catc_id and g.group_id =p.catb_id;"; 	  
    		  ArrayList<ArrayList<String>> book = DBManager.getResult(productQuery);
    		  if (book != null){
    			  ArrayList<String> tmp = book.get(0);
    			  String exam_id = tmp.get(0);
    			  String sub_id = tmp.get(1);
    			  String group_id = tmp.get(2);
    			  
    			   }
	    	  
	    	  
		
	   }
}//end main
}//end FirstExample