package com.suphalaam.hbase;
import com.suphalaam.hbase.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class MasterDatasub {
	
	public static List<Subject> getSubList(){
		String query = "select sub_id,sub_name from cakart.subject_detail order by sub_id";
		
		List<ArrayList> subList = DatabaseManager.executedQuery(query);
		List<Subject> returnList = new ArrayList<Subject>();
		for (ArrayList arrayList : subList) {
			int subId = Integer.parseInt((String)arrayList.get(0));
			String subName = (String)arrayList.get(1);
			Subject sub=new Subject(subId,subName);
			returnList.add(sub);
		}
		return returnList;
	}
	
	public static void main(String [] arg){
		System.out.println(MasterDatasub.getSubList());
	}

}
