package com.suphalaam.hbase;

import com.suphalaam.hbase.DatabaseManager;

import java.io.IOException;
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
	public static List<Group> getGrpList(){
		String query = "select group_id,group_name from cakart.group_details order by group_id";
		
		List<ArrayList> groupList = DatabaseManager.executedQuery(query);
		List<Group> returnList = new ArrayList<Group>();
		for (ArrayList arrayList : groupList) {
			int groupId = Integer.parseInt((String)arrayList.get(0));
			String groupName = (String)arrayList.get(1);
			Group grp=new Group(groupId,groupName);
			returnList.add(grp);
		}
		return returnList;
	}
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
	
	public static List<ResultRow> fetchResult(String book,String course,String download,
			String blog,String qna,String checkExam,String checkGroup,String checkSub ) throws IOException, Exception{
		//System.out.println("MasterData Line 53 \n");
		System.out.println(book+" "+course+" "+download+" "+blog+" "+qna+" "+checkExam+" "+
		checkGroup+" "+checkSub);
		
		System.out.println(checkExam+" "+checkGroup+" "+ checkSub);
		List<ResultRow> lst = Check.getCourseDetails(checkExam, checkGroup, checkSub);
//				new ArrayList<ResultRow>();
//		
		
		//System.out.println("MasterData Line 66 \n");
		return lst;
	}
	
	public static void main(String [] arg) throws IOException, Exception{
		System.out.println(MasterData.getExamList());
		System.out.println("MasterData Main Method \n");
		System.out.println(MasterData.fetchResult("a","b","c","d","e","2","2","4"));
		
	}

}
