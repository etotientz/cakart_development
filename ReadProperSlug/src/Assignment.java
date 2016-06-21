import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.filter.*;


import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.ValueFilter;


import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

import java.util.*;

public class Assignment{

	
	//cldrdata.jar  dnsns.jar  icedtea-sound.jar  jaccess.jar  localedata.jar  nashorn.jar  sunec.jar  sunjce_provider.jar  sunpkcs11.jar  zipfs.jar

   public static void main(String[] args) throws IOException, Exception{
   
      // Instantiating Configuration class
      Configuration config = HBaseConfiguration.create();

      // Instantiating HTable class
      HTable table = new HTable(config, "cakart");
      HTable table1 = new HTable(config, "cookie");
      
    Filter filter = new SingleColumnValueFilter(Bytes.toBytes("exam"),Bytes.toBytes("EXAMID"),CompareFilter.CompareOp.EQUAL, 
    		new BinaryComparator(Bytes.toBytes("1")));
    Filter filter1 = new SingleColumnValueFilter(Bytes.toBytes("subject"),Bytes.toBytes("SUBID A"),CompareFilter.CompareOp.EQUAL, 
    		new BinaryComparator(Bytes.toBytes("165")));

      //Get g = new Get(Bytes.toBytes("User"));
      Scan s = new Scan();
      Scan s1=new Scan();
      s.setFilter(filter); 
      s.setFilter(filter1);
      s.addColumn(Bytes.toBytes("user"), Bytes.toBytes("COOKIEID"));
      s.addColumn(Bytes.toBytes("channel"), Bytes.toBytes("TYPE"));
      ResultScanner scanner = table.getScanner(s);
      try {
    	// Scanners return Result instances.
    	// Now, for the actual iteration. One way is to use a while loop
    	// like so:
    	  int i=0;
    	for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
    	// print out the row we found and the columns we were looking
    		for (KeyValue kv : rr.raw()) {i++;
    	           String holdvalue = new String(kv.getValue());
    	           System.out.println(" " +holdvalue);
    	           Filter filter2 = new SingleColumnValueFilter(Bytes.toBytes("user"),Bytes.toBytes("COOKIEID"),CompareFilter.CompareOp.EQUAL, 
    	           		new BinaryComparator(Bytes.toBytes(holdvalue)));
    	           s1.setFilter(filter2);
    	           s1.addColumn(Bytes.toBytes("user"), Bytes.toBytes("COOKIEID"));
    	           s1.addColumn(Bytes.toBytes("user"), Bytes.toBytes("USERID"));
    	           ResultScanner scanner1 = table1.getScanner(s1);for (Result rr1 = scanner1.next(); rr1 != null; rr1 = scanner1.next()) {
    	           	// print out the row we found and the columns we were looking
    	       		for (KeyValue kv1 : rr1.raw()) {i++;
    	       	           String holdvalue1 = new String(kv1.getValue());
    	       	           System.out.println(" " +holdvalue1);
    	       	        }
    	           System.out.println("b");
    	        }}}
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

    	
      
      
      
      