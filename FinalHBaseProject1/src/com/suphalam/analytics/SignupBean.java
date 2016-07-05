package com.suphalam.analytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;

import org.joda.time.DateTime;


public class SignupBean {
	
	//this list will have 0-7 count and this will store the sign-up count data for last 7 days
	//if 0 is checked means today()-0 and it would show the sign-up for today
	private List<Integer> signupByDayCountList = new ArrayList<Integer>(7);
	private List<String> signupByDay = new ArrayList<String>(7);
	
	private List<Integer> maximumSignupID = new ArrayList<Integer>(8);
	private List<LinkedHashMap<String,Integer>> signupByDayNameList = new ArrayList<LinkedHashMap<String,Integer>>();
	private List<LinkedHashMap<String,Integer>> signupByDayCityCountList = new ArrayList<LinkedHashMap<String,Integer>>();
	private List<LinkedHashMap<String,Integer>> signupByDayDeviceCountList = new ArrayList<LinkedHashMap<String,Integer>>();
	
	
	/*
	 * Default constructor
	 */
	public SignupBean(){
		DateTime today = new DateTime();
		HashMap<Integer,String> dayMap = new HashMap<Integer,String>();
		dayMap.put(1,"Jan");
		dayMap.put(2,"Feb");
		dayMap.put(3,"Mar");
		dayMap.put(4,"Apr");
		dayMap.put(5,"May");
		dayMap.put(6,"Jun");
		dayMap.put(7,"Jul");
		dayMap.put(8,"Aug");
		
		
		signupByDayCountList = new ArrayList<Integer>(Arrays.asList(-1,-1,-1,-1,-1,-1,-1));
		maximumSignupID = new ArrayList<Integer>(Arrays.asList(-1,-1,-1,-1,-1,-1,-1,-1));
		//System.out.println("signupByDayNameList.size() = "+signupByDayNameList.size());
		for(int i=0;i<7;i++){
			String todayString = String.valueOf(today.minusDays(i).getDayOfMonth())+"-"+dayMap.get(today.minusDays(i).getMonthOfYear());
			signupByDay.add(todayString);
			signupByDayNameList.add(new LinkedHashMap<String,Integer>());
			signupByDayCityCountList.add(new LinkedHashMap<String,Integer>());
			signupByDayDeviceCountList.add(new LinkedHashMap<String,Integer>());
		}
		//System.out.println("signupByDayNameList.size() = "+signupByDayNameList.size());
		/*
		signupByDayNameList.set(0, null);
		signupByDayNameList.set(1, null);
		signupByDayNameList.set(2, null);
		signupByDayNameList.set(3, null);
		signupByDayNameList.set(4, null);
		signupByDayNameList.set(5, null);
		signupByDayNameList.set(6, null);
		*/
		
	}
	
	//setting and getting the data for count
	public int getSignupByDayCount(int day) {
		return signupByDayCountList.get(day);

	}
	public int setSignupByDayCount(int day, int count) {
		return signupByDayCountList.set(day, count);

	}
	
	//setting and getting the data for max id from 8th day till today
	public int getMaximumSignupID(int day) {
		return maximumSignupID.get(day);

	}
	public int setMaximumSignupID(int day, int count) {
		return maximumSignupID.set(day, count);

	}
	
	//setting and getting the data for sessions
	public LinkedHashMap<String,Integer> getSignupByDayNameList(int day) {
		return signupByDayNameList.get(day);

	}
	public void setSignupByDayNameList(int day, LinkedHashMap<String,Integer> map) {
		signupByDayNameList.set(day, map);

	}

	//setting and getting the data for sessions
	public LinkedHashMap<String,Integer> getSignupByCity(int day) {
		return signupByDayCityCountList.get(day);
	
	}
	public void setSignupByCity(int day, LinkedHashMap<String,Integer> map) {
		signupByDayCityCountList.set(day, map);
	
	}
		
	//setting and getting the data for sessions
	public LinkedHashMap<String,Integer> getSignupByDevice(int day) {
		return signupByDayDeviceCountList.get(day);
	
	}
	public void setSignupByDevice(int day, LinkedHashMap<String,Integer> map) {
		signupByDayDeviceCountList.set(day, map);
	
	}
	
	
	//setting and getting the data for sessions
		public String getSignupDay(int day) {
			return signupByDay.get(day);
		
		}
	

	
	//--------------------------------
	@Override
	public String toString(){
		String returnString = "";
		returnString = returnString + " The Maximum IDs Daywise = \t" + maximumSignupID; 
		returnString = returnString +"\n The Signup Count Daywise = \t" + signupByDayCountList;
		//returnString = returnString + "\n The Signup List Daywise = " + signupByDayNameList; 
		//returnString = returnString + "\n The City List Daywise = " + signupByDayCityCountList;
		//returnString = returnString + "\n The Device List Daywise = " + signupByDayDeviceCountList;
		return returnString;
	}
}
