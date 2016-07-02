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
	    
      // Instantiating Configuration class
      Configuration config = HBaseConfiguration.create();

      // Instantiating HTable class
      HTable table = new HTable(config, "cakart1");
    
     System.out.println("examID: "+ examID +"\n");
     System.out.println("groupID: "+groupID+ "\n");
     System.out.println("subID: "+subID+"\n");
      //Get g = new Get(Bytes.toBytes("User"));
      Scan s = new Scan();
     //s.addFami
      
   
      s.addColumn(Bytes.toBytes("asset_type"), Bytes.toBytes("DOWNLOAD"));
      
    

     
//     s.addFamily(Bytes.toBytes("exam"));
   
   // s.addColumn(Bytes.toBytes("user"), Bytes.toBytes("URL"));
      // s.addColumn(Bytes.toBytes("user"), Bytes.toBytes("DATE"));
     // s.addColumn(Bytes.toBytes("user"), Bytes.toBytes("COOKIEID"));
     
    
      FilterList flist = new FilterList(FilterList.Operator.MUST_PASS_ALL);
      Filter  courseFilter = new SingleColumnValueFilter(Bytes.toBytes("asset_type"),Bytes.toBytes("DOWNLOAD"),
    		  CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("1")));
      flist.addFilter(courseFilter);
    
       s.setFilter(flist);
     
      ResultScanner scanner = table.getScanner(s);
      try {int i1=0;
      
      LinkedHashMap<String, Integer> lhmUrls = new LinkedHashMap<String,Integer>();
    	
    	  int i=0;int count=0;
    	for (Result rr = scanner.next(); rr != null; rr = scanner.next()) {a=b=c=d=e=f=g=h=0;
    	
    	String rowid=Bytes.toString(rr.getRow());
    	System.out.println(rowid+"----------");
    	Cell kv=rr.getColumnCells(arg0, arg1);
    	//System.out.println(">>"+rr);
    	 // Instantiating Get class
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
        
        
        
    		
    	
     //   String city = Bytes.toString(value1);
      //  System.out.println(+ ""); 
       // System.out.println( "////////");
      // if(subids.equals(Integer.toString(6))==true)
        if(url.indexOf("downloads") != -1  && subids!=null &&
        		( subids.equals(Integer.toString(12)) || subids.equals(Integer.toString(8)) || subids.equals(Integer.toString(53)))){
        	
	      //  System.out.println((i++)+"date: " + dated+ " "+"cookieid: " + cookie+" "+"url: " + url+"  asset_type: "         		+typ+ " subid: "+subids);

//    		String query = "select user_id from cakart.u_c_map where cookie_id LIKE '"+cookie+"' limit 1";
//    		
//    		List<ArrayList> userID = DatabaseManager.executedQuery(query);
//    		if (userID != null && userID.size() > 0){
//    			System.out.println("user id \t"+userID.get(0).get(0));
//    		}
	        
	        if (lhmUrls.get(url) ==null){
	        	lhmUrls.put(url, 1);
	        }
	        else{
	        	int urlCount = lhmUrls.get(url);
	        	lhmUrls.put(url, (urlCount+1));
	        }
        }

    	//rr.getValue(family, qualifier)
    		
    		//for (KeyValue kv : rr.raw())
    		//{ 
    			//System.out.println((i++)+">>"+kv);
    			//System.out.println((i++)+">>"+Bytes.toString(kv.getQualifier())+" "+ Bytes.toString(kv.getValue()));
    			//System.out.println("------------------");
    			
    			
    			/*if(Bytes.toString(kv.getQualifier()).equals("COURSE")){
    				
    					//System.out.println(Bytes.toString(kv.getValue())+ " "+ Bytes.toString(kv.getQualifier()));
    				a=1;
    				
    			}*/
    			//else{a=2;}
    	
    			/*if(Bytes.toString(kv.getFamily()).equals("exam") &&a==1)
       			 	  			
    			{   String pattern1 = "^(EXAMID).*";
    			 Pattern r1 = Pattern.compile(pattern1);

         	      // Now create matcher object.
         	      Matcher m1 = r1.matcher(Bytes.toString(kv.getQualifier()));
         	    /// System.out.println("Check 110 \n");
         	     if (m1.find( )){examdetail=Bytes.toString(kv.getValue());if(examdetail.equals(examID))b=1;}}*/
    				
    				
      	    	//if(a==1){System.out.println("----------------------");
      	    	/*if(Bytes.toString(kv.getQualifier()).equals("COOKIEID")==true && a==1){
      	    		//System.out.println("----------------------");
      	    		
        			  detail =  Bytes.toString(kv.getValue()) ;
      	    	
      	    	
      	  
      	    		ResultRow rr1 = new ResultRow(detail,detaildate,detailurl);
      	    		//holdvalue);
      	    		results.add(rr1);
      	    		//System.out.println("Add result object in chek 148");
    	        //  if(Integer.parseInt(holdvalue)< 5){p=1;i1++;
    	          System.out.println(detail+ " :ID"+"\n");
    	          System.out.println(detaildate+ " :DATE"+"\n");
    	          System.out.println(detailurl+ " :URL"+"\n");
    	          
    	          
    	         // System.out.println(examdetail +" exam"+"\n");
    	         // System.out.println(Bytes.toString(kv.getQualifier())+ "     Qualifier");
    	         // System.out.println(holdvalue +" value"+"\n");
    	          
    	          System.out.println(i +"  i value"+"\n");i++;
    	          break;}*/
    	        	
    	         
    	        
    			}
    	System.out.println("------------------------");
    	Iterator it = lhmUrls.entrySet().iterator();
    	int x = 0;
    	while(it.hasNext()){
    		Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)it.next(); 
    		//System.out.println((x++)+ ":"+pair.getKey() + "<>" +  pair.getValue());	
    	}
    	//System.out.println(lhmUrls);
    	System.out.println("END OF SERACH");}
    		
    	//System.out.println("Found row: " + i);
    	
    	 
    	// The other approach is to use a foreach loop. Scanners are
    	// iterable!
    	// for (Result rr : scanner) {
    	// System.out.println("Found row: " + i);
    		
      
    	 finally {
    	// Make sure you close your scanners when you are done!
    	// Thats why we have it inside a try/finally clause
    	scanner.close();}
      
      return results;
    	
    	}
      }

    	
      
      
      
      