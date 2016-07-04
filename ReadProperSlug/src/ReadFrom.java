import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

import java.util.*;
import java.util.Collection;
import java.util.Collection;
import java.sql.Connection;
import java.util.regex.Pattern;
import java.util.regex.*
;
public class ReadFrom{
	/*static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/cakart";
	   

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "spm";*/
	static String detail;
	static String subjectdetail,examdetail;
    static int p;
	
	//cldrdata.jar  dnsns.jar  icedtea-sound.jar  jaccess.jar  localedata.jar  nashorn.jar  sunec.jar  sunjce_provider.jar  sunpkcs11.jar  zipfs.jar

   public static void main(String[] args) throws IOException, Exception{
	   
	   
	    
      // Instantiating Configuration class
      Configuration config = HBaseConfiguration.create();

      // Instantiating HTable class
      HTable table = new HTable(config, "cakart1");
     // HTable table1 = new HTable(config, "cookie");
      
    //Filter filter = new SingleColumnValueFilter(Bytes.toBytes("user"),Bytes.toBytes("URL"),CompareFilter.CompareOp.EQUAL,
    		//new RegexStringComparator("^\\/courses\\/.*-advanced-management-accounting.*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL));
    		//new BinaryComparator(Bytes.toBytes("/courses/advanced-management-accounting")));
  // Filter filter1 = new SingleColumnValueFilter(Bytes.toBytes("asset_type"),Bytes.toBytes("TYPE"),CompareFilter.CompareOp.EQUAL, 
    		//new BinaryComparator(Bytes.toBytes("COURSE")));

      //Get g = new Get(Bytes.toBytes("User"));
      Scan s = new Scan();
     //s.addFami
    // s.addColumn(Bytes.toBytes("asset_type"), Bytes.toBytes("BOOK"));
     s.addColumn(Bytes.toBytes("asset_type"), Bytes.toBytes("COURSE"));
    // s.addFamily(Bytes.toBytes("asset_type"));
    // s.addColumn(Bytes.toBytes("user"), Bytes.toBytes("COOKIEID"));
   //  s.addFamily(Bytes.toBytes("exam"));
     s.addFamily(Bytes.toBytes("group"));
     // s.addFamily(Bytes.toBytes("exam"));
    //  s.addFamily(Bytes.toBytes("subject"));
     // String mat=new RegexStringComparator("^SUBID.*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
      FilterList list = new FilterList(FilterList.Operator.MUST_PASS_ALL);
     // Filter  filter1 = new SingleColumnValueFilter(Bytes.toBytes("asset_type"),Bytes.toBytes("BOOK"),CompareFilter.CompareOp.GREATER, new BinaryComparator(Bytes.toBytes("1")));
      Filter  filter5 = new SingleColumnValueFilter(Bytes.toBytes("asset_type"),Bytes.toBytes("COURSE"),CompareFilter.CompareOp.GREATER, new BinaryComparator(Bytes.toBytes("1")));

     // FamilyFilter filter1 = 
    		  //  new FamilyFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes("asset_type")));
     // list.addFilter(filter1);
      list.addFilter(filter5);;
    FamilyFilter filter3 = 
  		    new FamilyFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes("exam")));
   // list.addFilter(filter3);
    //filter3.
    FamilyFilter filter2 = 
  		    new FamilyFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes("group")));
   list.addFilter(filter2);
    FamilyFilter filter4 = 
  		    new FamilyFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes("subject")));
   //list.addFilter(filter4);
   
    
      	
      
    s.setFilter(list);
     
     
     // s.addColumn(Bytes.toBytes("channel"), Bytes.toBytes("TYPE"));
      ResultScanner scanner = table.getScanner(s);
      try {int i1=0;
    	// Scanners return Result instances.
    	// Now, for the actual iteration. One way is to use a while loop
    	// like so:
    	  int i=0;int count=0;
    	for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {p=0;
    		i++;String rowid=Bytes.toString(rr.getRow());
        	System.out.println(rowid+"----------");
        	Get g = new Get(Bytes.toBytes(rowid));

            // Reading the data
            Result result = table.get(g);
            
            System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("user"),Bytes.toBytes("URL"))));
    	
    		
    	         
    	        
    		// }
            System.out.println(":: "+ i);}System.out.println("END OF SEARCH:");
    		
    	//System.out.println("Found row: " + i);
    	
    	 
    	// The other approach is to use a foreach loop. Scanners are
    	// iterable!
    	// for (Result rr : scanner) {
    	// System.out.println("Found row: " + i);
    	
      }
      
    	 finally {
    	// Make sure you close your scanners when you are done!
    	// Thats why we have it inside a try/finally clause
    	scanner.close();}
    	
    	}
      }

    	
      
      
      
      