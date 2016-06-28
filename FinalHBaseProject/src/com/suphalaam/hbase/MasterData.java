package com.suphalaam.hbase;

import com.suphalaam.hbase.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class MasterData {
	
	public static List<Exam> getExamList(){
		String query = "select exam_id,exam_name from cakart.exam_detail order by exam_id";
		
		List<ArrayList> examList = DatabaseManager.executedQuery(query);
		List<Exam> returnList = new ArrayList<Exam>();
		for (ArrayList arrayList : examList) {
			int examId = Integer.parseInt((String)arrayList.get(0));
			String examName = (String)arrayList.get(1);
			Exam exm=new Exam(examId,examName);
			returnList.add(exm);
		}
		return returnList;
	}
	
	public static void main(String [] arg){
		System.out.println(MasterData.getExamList());
	}

}
