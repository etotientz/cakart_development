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
public class Check{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/cakart";
	   

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "spm";

	
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
     // s.addColumn(Bytes.toBytes("user"), Bytes.toBytes("COOKIEID"));
     s.addColumn(Bytes.toBytes("asset_type"), Bytes.toBytes("COURSE"));
     // s.addFamily(Bytes.toBytes("subject"));
     // s.addColumn(Bytes.toBytes("subject"), Bytes.toBytes("SUBID A"));
     // s.addColumn(Bytes.toBytes("subject"), Bytes.toBytes("SUBID B"));
      //s.addColumn(Bytes.toBytes("subject"), Bytes.toBytes("SUBID C"));
      //s.addColumn(Bytes.toBytes("subject"), Bytes.toBytes("SUBID D"));
     // String mat=new RegexStringComparator("^SUBID.*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
      FilterList list = new FilterList(FilterList.Operator.MUST_PASS_ALL);
      Filter  filter1 = new SingleColumnValueFilter(Bytes.toBytes("asset_type"),Bytes.toBytes("COURSE"),CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("1")));
      list.addFilter(filter1);
     Filter filter2 = new SingleColumnValueFilter(Bytes.toBytes("subject"),Bytes.toBytes("SUBID A"),CompareFilter.CompareOp.LESS,
    		 new BinaryComparator(Bytes.toBytes("5")));
      list.addFilter(filter2);
     /* SingleColumnValueFilter filter2 = new SingleColumnValueFilter(
    	      	Bytes.toBytes("subject"),Bytes.toBytes(CompareFilter.CompareOp.EQUAL,new RegexStringComparator("^SUBID.*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL)),CompareFilter.CompareOp.LESS, 
    			new BinaryComparator(Bytes.toBytes("5")));*/
    //  FamilyFilter filterfam=new FamilyFilter(CompareFilter.CompareOp.EQUAL, 
	    		//new BinaryComparator(Bytes.toBytes("subject")),ByteArrayComparable(Bytes.toBytes("subject")));
      	
      
    s.setFilter(list);
     // s.addColumn(Bytes.toBytes("user"), Bytes.toBytes("COOKIEID"));
     // s.setFilter(list);
      
     // Scan s1=new Scan();
     // s.setFilter(filter); 
      //s.setFilter(filter1);
     
     // s.addColumn(Bytes.toBytes("channel"), Bytes.toBytes("TYPE"));
      ResultScanner scanner = table.getScanner(s);
      try {
    	// Scanners return Result instances.
    	// Now, for the actual iteration. One way is to use a while loop
    	// like so:
    	  int i=0;
    	for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
    		i++;
    	// print out the row we found and the columns we were looking
    		for (KeyValue kv : rr.raw()) {
    	           String holdvalue = new String(kv.getValue());
    	          
    	           System.out.println(" " +holdvalue);
    	          /* Filter filter2 = new SingleColumnValueFilter(Bytes.toBytes("user"),Bytes.toBytes("COOKIEID"),CompareFilter.CompareOp.EQUAL, 
    	           		new BinaryComparator(Bytes.toBytes(holdvalue)));
    	           s1.setFilter(filter2);
    	           s1.addColumn(Bytes.toBytes("user"), Bytes.toBytes("COOKIEID"));
    	           s1.addColumn(Bytes.toBytes("user"), Bytes.toBytes("USERID"));
    	           ResultScanner scanner1 = table1.getScanner(s1);for (Result rr1 = scanner1.next(); rr1 != null; rr1 = scanner1.next()) {
    	           	// print out the row we found and the columns we were looking
    	       		for (KeyValue kv1 : rr1.raw()) {i++;
    	       	           String holdvalue1 = new String(kv1.getValue());
    	       	           System.out.println(" " +holdvalue1);
    	       	        }*/
    	           //System.out.println("b");
    	        }}
    	System.out.println("Found row: " + i);
    	
    	 
    	// The other approach is to use a foreach loop. Scanners are
    	// iterable!
    	// for (Result rr : scanner) {
    	// System.out.println("Found row: " + rr);
    	// }
    	} finally {
    	// Make sure you close your scanners when you are done!
    	// Thats why we have it inside a try/finally clause
    	scanner.close();}
    	
    	}
    	}

    	
      
      
      
      