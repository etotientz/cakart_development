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
	   
	   String sql = "SELECT user_id,slug,date,hour,min FROM user_slug";
	   
	   ArrayList<ArrayList<String>> userLlist = DBManager.getResult(sql);
	   int i=0;
	   
	   for (ArrayList<String> arrayList : userLlist) {i++;
		   String id = arrayList.get(0);
		   String slug = arrayList.get(1);
		   String date = arrayList.get(2);
		   String hour = arrayList.get(3);
		   String min = arrayList.get(4);
		   //System.out.println(id + " "+slug+" "+date + " "+ hour+" "+ min);

			 String[] parts = slug.split("/");
			 String slug_part = parts[2];
			 //System.out.println("" + slug_part+ '\n');
			 String prefix = parts[1];
			 //System.out.println("" +prefix);
			
			 
			 UserSlugVO user = new UserSlugVO(id,slug_part, date,hour,min,prefix) ;
			 
			if(prefix.equals("books")){
				 //System.out.println("HEY BOOKS");
	    		  String bookQuery = "Select book_id,book_title from book_slug where book_slug LIKE '"+slug_part+"' limit 1";
	    		  
	    		  ArrayList<ArrayList<String>> book = DBManager.getResult(bookQuery);
	    		  if (book != null && book.size() > 0){
	    			  ArrayList<String> tmp = book.get(0);
	    			  String book_id = tmp.get(0);
	    			  String book_title = tmp.get(1);
	    			  System.out.println("BOOK_ID " +book_id);
	    			  String productQuery = "Select cat_id,catc_id,catb_id,'"+book_id+"' from product_detail p where '"+book_id+"'=p.product_id"
	    		    	 		+ " and p.p_type='Book'"; 	  
	    	    		  ArrayList<ArrayList<String>> book1 = DBManager.getResult(productQuery);
	    	    		  //System.out.println("The books array is "+ book1);
	    	    		  if (book1 != null && book1.size() > 0){
	    	    			  
	    	    			  for (ArrayList<String> arrayList2 : book1) {
	    	    				  if(arrayList2.get(0)!=null){
	    	    	    		      String exm_id=arrayList2.get(0);
	    	    	    		      System.out.println("EXAMID :" +exm_id);}
	    	    	    			 if(arrayList2.get(1)!=null){
	    	    	    				  
	    	    	    			  String sub_id = arrayList2.get(1);
	    	    	    			  System.out.println("SUBID :"+sub_id);}
	    	    	    			 if(arrayList2.get(2)!=null){ 
	    	    	    				 
	    	    	    			  String group_id = arrayList2.get(2);
	    	    	    			  System.out.println("GROUPID : "+group_id);}
	    	    	    			  
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
	    
	    	 /*String productQuery = "Select exam_id,sub_id,group_id,'"+book_id+"' from product_detail p,s"
	    	 		+ "ubject_detail s,book_slug,exam_detail e,group_details g where book_slug.book_id=p.product_id"
	    	 		+ " and p.p_type='Book' and e.exam_id=p.cat_id and s.sub_id=p.catc_id and g.group_id =p.catb_id;"; 	  
    		  ArrayList<ArrayList<String>> book1 = DBManager.getResult(productQuery);
    		  //System.out.println("The books array is "+ book1);
    		  if (book1 != null && book1.size() > 0){
    			  
    			  ArrayList<String> tmp1 = book1.get(0);
    			  String exam_id = tmp1.get(0);
    			  String sub_id = tmp1.get(1);
    			  String group_id = tmp1.get(2);
    			  
    			   }
    		  else{
    			  System.out.println("The book list ie empty ");
    		  }
	    	*/  
	    	  
		
	   }//System.out.println(i);
}//end main
}//end FirstExample