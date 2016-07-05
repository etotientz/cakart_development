package com.suphalaam.hbase;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
//import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

import java.util.*;
import java.sql.Connection;
import java.util.regex.Pattern;
import java.util.regex.*
;
import org.apache.hadoop.hbase.client.Get;
public class Check{
	
	static String detail;
	static String subjectdetail,examdetail,groupdetail,detaildate,detailurl;
    static int a,b,c,d,e,f,g,h;
	
	

   public static List<ResultRow> getCourseDetails( String examID,String groupID,String subID ) throws IOException, Exception{
	   
	   List<ResultRow> results= new ArrayList<ResultRow>();
	   Set<String> linkedHashSet = new LinkedHashSet<>();
	    
      // Instantiating Configuration class
      Configuration config = HBaseConfiguration.create();

      // Instantiating HTable class
      HTable table = new HTable(config, "cakart1");
    
     System.out.println("examID: "+ examID +"\n");
     System.out.println("groupID: "+groupID+ "\n");
     System.out.println("subID: "+subID);
      //Get g = new Get(Bytes.toBytes("User"));
      Scan s = new Scan();
     //s.addFami
      FilterList flist = new FilterList(FilterList.Operator.MUST_PASS_ONE);
   
    //  s.addColumn(Bytes.toBytes("asset_type"), Bytes.toBytes("DOWNLOAD"));
     // s.addFamily(Bytes.toBytes("group"));
      if(!examID.equals("")){
      s.addFamily(Bytes.toBytes("exam"));
      FamilyFilter examfilter= 
    		    new FamilyFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes("exam")));
     
      flist.addFilter(examfilter);}
      if(!groupID.equals("")){
    	  s.addFamily(Bytes.toBytes("group")); 
    	  FamilyFilter groupfilter= 
    	  		    new FamilyFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes("group")));
    	  
    	  flist.addFilter(groupfilter);}
      if(!subID.equals(""))
    	  {
    	  s.addFamily(Bytes.toBytes("subject"));
    	  FamilyFilter subfilter= 
  	  		    new FamilyFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator(Bytes.toBytes("subject")));  
    	  flist.addFilter(subfilter);}
      
    
     
      
  
    
      Filter  downloadFilter = new SingleColumnValueFilter(Bytes.toBytes("asset_type"),Bytes.toBytes("DOWNLOAD"),
    		  CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("1")));
      flist.addFilter(downloadFilter);
    
       s.setFilter(flist);
     
      ResultScanner scanner = table.getScanner(s);
      try {int i1=0;
      
      LinkedHashMap<String, Integer> lhmUrls = new LinkedHashMap<String,Integer>();
    	
    	  int i=0;int count=0;
    	for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {
    	//i++;System.out.println(i+ '\n');
    	String rowid=Bytes.toString(rr.getRow());
    	//System.out.println(rowid+"----------");
    	
        Get g = new Get(Bytes.toBytes(rowid));

        // Reading the data
        Result result = table.get(g);
    
    		  
    		  // Reading values from Result class object
        byte [] urldata = result.getValue(Bytes.toBytes("user"),Bytes.toBytes("URL"));
        byte [] asset_typ = result.getValue(Bytes.toBytes("asset_type"),Bytes.toBytes("DOWNLOAD"));
        
        byte [] cookieid = result.getValue(Bytes.toBytes("user"),Bytes.toBytes("COOKIEID"));
        byte [] date = result.getValue(Bytes.toBytes("user"),Bytes.toBytes("DATE"));
        byte [] examid = result.getValue(Bytes.toBytes("exam"),Bytes.toBytes("EXAMID A"));
       byte [] groupid = result.getValue(Bytes.toBytes("group"),Bytes.toBytes("GROUPID A"));
       byte [] subjectid = result.getValue(Bytes.toBytes("subject"),Bytes.toBytes("SUBID A"));

      //  byte [] value1 = result.getValue(Bytes.toBytes(""),Bytes.toBytes("city"));

        // Printing the values
        String url = Bytes.toString(urldata);
        String typ=Bytes.toString(asset_typ);
        String cookie = Bytes.toString(cookieid);
        String dated = Bytes.toString(date);
        String exmid = Bytes.toString(examid);
        String grpid = Bytes.toString(groupid);
        String subids = Bytes.toString(subjectid);
        
       // System.out.println(grpid+ " :");
        if(exmid.equals(examID)&&url.indexOf("downloads") != -1){if(subids.equals(subID))
        	{
        System.out.println(exmid+ " exm:");
        System.out.println(exmid+ " grpid:");
        System.out.println(subids+ " sub:");
        System.out.println(url+ " url:");
       // System.out.println( '\n');
        System.out.println(cookie+ " :");
        i++;System.out.println(i+ '\n');
        ResultRow a=new ResultRow(cookie,url,dated);
        results.add(a);}
       //linkedHashSet.add(cookie);
        
        
        
    		
    	
     //   String city = Bytes.toString(value1);
      //  System.out.println(+ ""); 
       // System.out.println( "////////");
      // if(subids.equals(Integer.toString(6))==true)
        if(url.indexOf("downloads") != -1  && subids!=null &&
        		( subids.equals(Integer.toString(12)) || subids.equals(Integer.toString(8)) || subids.equals(Integer.toString(53)))){
        
	        
	        if (lhmUrls.get(url) ==null){
	        	lhmUrls.put(url, 1);
	        }
	        else{
	        	int urlCount = lhmUrls.get(url);
	        	lhmUrls.put(url, (urlCount+1));
	        }
        }

    	
    	        	
    	         
    	        
        }}
    	System.out.println("------------------------");
    	Iterator it = lhmUrls.entrySet().iterator();
    	int x = 0;
    	while(it.hasNext()){
    		Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)it.next(); 
    		System.out.println((x++)+ ":"+pair.getKey() + "<>" +  pair.getValue());	
    	}
    	//for(String c:linkedHashSet)System.out.println(c +'\n');
    	//System.out.println(lhmUrls);
    	System.out.println("END OF SERACH");}
    		
    	
    		
      
    	 finally {
    	// Make sure you close your scanners when you are done!
    	// Thats why we have it inside a try/finally clause
    	scanner.close();}
      
      return results;
    	
    	}
      }

    	
      
      
      
      