package com.suphalaam.hbase;
import com.suphalaam.hbase.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class MasterDatagrp {
	
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
	
	public static void main(String [] arg){
		System.out.println(MasterDatagrp.getGrpList());
	}

}
