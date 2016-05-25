import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import java.util.*;

public class RData{

   public static void main(String[] args) throws IOException, Exception{
   
      // Instantiating Configuration class
      Configuration config = HBaseConfiguration.create();

      // Instantiating HTable class
      HTable table = new HTable(config, "H4");

    /*  // Instantiating Get class
      Get g = new Get(Bytes.toBytes("row1"));

      // Reading the data
      Result result = table.get(g);

      // Reading values from Result class object
      byte [] value = result.getValue(Bytes.toBytes("personal"),Bytes.toBytes("name"));

      byte [] value1 = result.getValue(Bytes.toBytes("personal"),Bytes.toBytes("city"));

      // Printing the values
      String name = Bytes.toString(value);
      String city = Bytes.toString(value1);
      
      System.out.println("name: " + name + " city: " + city);*/
      
      //Get
      Scan s = new Scan();
      s.addColumn(Bytes.toBytes("book_id"), Bytes.toBytes("B_ID"));
      ResultScanner scanner = table.getScanner(s);
      try {
        // Scanners return Result instances.
        // Now, for the actual iteration. One way is to use a while loop like so:
        for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
        	
        	 byte [] value = rr.getValue(Bytes.toBytes("book_id"),Bytes.toBytes("B_ID"));
        	 String name = Bytes.toString(value);
        	 System.out.println("book_id: " + name);
          // print out the row we found and the columns we were looking for
          System.out.println("Found row: " + rr);
        }
        
        
        // Instantiating Get class
        Get g = new Get(Bytes.toBytes("CA Final"));

        // Reading the data
        Result result = table.get(g);

        // Reading values from Result class object
        byte [] value = result.getValue(Bytes.toBytes("book_id"),Bytes.toBytes("B_ID"));

       // byte [] value1 = result.getValue(Bytes.toBytes("personal"),Bytes.toBytes("city"));

        // Printing the values
        String name = Bytes.toString(value);
       // String city = Bytes.toString(value1);
        
        System.out.println("name: " + name );
        
        System.out.println("   "+" Hey");
       // HTable table = new HTable(conf, "tablename");
        Get get = new Get("CA Final".getBytes());     
        get.addFamily("author".getBytes());    // <-----------------       
        Result rs = table.get(get);
        for (KeyValue kv : rs.raw()) {
           String holdvalue = new String(kv.getValue());
           System.out.println(" " +holdvalue);
        }
     
  }

        // The other approach is to use a foreach loop. Scanners are iterable!
        // for (Result rr : scanner) {
        //   System.out.println("Found row: " + rr);
        // }
       finally {
        // Make sure you close your scanners when you are done!
        // Thats why we have it inside a try/finally clause
        scanner.close();
      }}}