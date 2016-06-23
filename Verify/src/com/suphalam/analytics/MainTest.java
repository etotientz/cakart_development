package com.suphalam.analytics;

import java.util.LinkedHashMap;

import java.util.Iterator;

import com.suphalam.analytics.ExcelReaderUtility;
import com.suphalam.analytics.TrafficBySourceBean;

public class MainTest {

	
	public static void main(String[] args) {
//		//System.out.println(HourlySessionCountByTrafficSource.buildData());
//		
//		String hourlyIndex = "2015072220";
//		String hourPart = hourlyIndex.substring(8,hourlyIndex.length());
//		int hourPartInt = Integer.parseInt(hourPart);
//		if(hourPartInt<12){
//			System.out.println(hourPart+":00 AM");
//		}
//		else{
//			System.out.println(hourPart+":00 PM");
//		}
//		
		
		
		/*
		LinkedHashMap<String,TrafficBySourceBean> list = new LinkedHashMap<String,TrafficBySourceBean>();
		ExcelReaderUtility.loadExcelFile(System.getProperty("user.dir")+"/bin/resources/"+"AllSource.xls", list);
		ExcelReaderUtility.loadExcelFile(System.getProperty("user.dir")+"/bin/resources/"+"Annoucement.xls", list);
		//System.out.println("list values in main method \n" + list);
		
		Iterator iter = list.keySet().iterator();
		String dataStr = "";
		while(iter.hasNext()){
			
			
			String hourKey = (String)iter.next();
			TrafficBySourceBean bean = list.get(hourKey);
			dataStr=dataStr+
							"[" + 
							"'"+bean.getHourlyIndex()+ "',"+
							bean.getDirectSessionCount()+ ","+
							bean.getEmailSessionCount()+ ","+
							bean.getReferralSessionCount()+ ","+
							bean.getReferralSessionCount()+ ","+
							bean.getSocialSessionCount()+ ","+
							bean.getNotSetSessionCount()+ ","+
							"]";
			
			
		}
		dataStr = dataStr.substring(0, dataStr.length()-1);
		*/
		
	}
}
