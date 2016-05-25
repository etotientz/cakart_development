import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class InsertData{

   public static void main(String[] args) throws IOException {

      // Instantiating Configuration class
      Configuration config = HBaseConfiguration.create();

      // Instantiating HTable class
      HTable hTable = new HTable(config, "emp1");
      String ro="id1";
      // Instantiating Put class
      // accepts a row name.
      Put p = new Put(Bytes.toBytes(ro)); 
       String s="raju";
       
      // adding values using add() method
      // accepts column family name, qualifier/row name ,value
      p.add(Bytes.toBytes("info"),
      Bytes.toBytes("name"),Bytes.toBytes(s));
      String r="3";
      p.add(Bytes.toBytes("info"),
      Bytes.toBytes("roll"),Bytes.toBytes(r));

      /*p.add(Bytes.toBytes("professional"),Bytes.toBytes("designation"),
      Bytes.toBytes("manager"));

      p.add(Bytes.toBytes("professional"),Bytes.toBytes("salary"),
      Bytes.toBytes("50000"));*/
      
      // Saving the put Instance to the HTable.
      hTable.put(p);
      System.out.println("data inserted");
      
      // closing HTable
      hTable.close();
   }
}