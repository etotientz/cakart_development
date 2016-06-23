package com.suphalam.analytics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.suphalam.analytics.TrafficBySourceBean;

public class ExcelReaderUtility {
	
	public static LinkedHashMap<String,TrafficBySourceBean> loadExcelFile (String xlFileName, LinkedHashMap<String,TrafficBySourceBean> list){
		
		try {
			
			String absolutePath = xlFileName;
			
			System.out.println("<ExcelReaderUtility.java> absolutePath of xl file:-"+absolutePath);
		     
			//load the file
		    FileInputStream file = new FileInputStream(new File(absolutePath));
		     
		    //Get the workbook instance for XLS file 
		    HSSFWorkbook workbook = new HSSFWorkbook(file);
		 
		    //Get first sheet from the workbook
		    HSSFSheet sheet = workbook.getSheetAt(0);
		    
		    String sheetName = sheet.getSheetName();
		    
		    System.out.println("<ExcelReaderUtility.java>The name of the worksheet :-"+sheetName);
		     
		    //Iterate through each rows from first sheet
		    Iterator<Row> rowIterator = sheet.iterator();
		    while(rowIterator.hasNext()) {
		    	
		    	//get the row in the row object
		    	 Row row = rowIterator.next();
		    	 
		    	 //if it is Annoucement sheet.
		    	 if(sheetName.equals("Annoucement")){
		    		 String hourKey = String.valueOf((int)row.getCell(0).getNumericCellValue());
		    		int sessions = (int) row.getCell(1).getNumericCellValue();
		    		
		    		TrafficBySourceBean bean = list.get(hourKey);
		    		//System.out.println(bean.getHourlyIndex() + "-" + sessions);
		    		if(bean==null){
		    			bean = new TrafficBySourceBean(hourKey);
		    		}
		    		bean.setAnnoucementSessionCount(sessions);
		    		if((int)row.getCell(0).getNumericCellValue() >0)
			    		
		    		{
	
		    			list.put(hourKey,bean);
		    		}
		    		
		    		
		    	 }
		    	 else if(sheetName.equals("AllSource")){
		    		 	String hourKey = String.valueOf((int)row.getCell(0).getNumericCellValue());
		    		 	
			    		String sourceValue = row.getCell(1).getStringCellValue();
			    		int sessions = (int) row.getCell(2).getNumericCellValue();
			    		TrafficBySourceBean bean = list.get(hourKey);
			    		if(bean==null){
			    			bean = new TrafficBySourceBean(hourKey);
			    		}
			    		
			    		if(sourceValue.equals("Direct")){bean.setDirectSessionCount(sessions);}
			    		else if (sourceValue.equals("Email")){bean.setEmailSessionCount(sessions);}
			    		else if (sourceValue.equals("Organic Search")){bean.setOrganicSearchSessionCount(sessions);}
			    		else if(sourceValue.equals("Referral")){bean.setReferralSessionCount(sessions);}
			    		else if(sourceValue.equals("Social")){bean.setSocialSessionCount(sessions);}
			    		else{int currentValue = bean.getOrganicSearchSessionCount();currentValue = currentValue+ sessions;bean.setNotSetSessionCount(currentValue); 
			    		}
			    		
			    		if((int)row.getCell(0).getNumericCellValue() >0)
			    		
			    		{
		
			    			list.put(hourKey,bean);
			    		}

			    }
		    	 else {
		    		 //other stuff
		    	 }
  
		    }//end-while
		    
		    file.close();
		   // workbook.close();

		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return list;
	}
	
	

}
