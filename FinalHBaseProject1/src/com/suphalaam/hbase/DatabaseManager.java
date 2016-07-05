package com.suphalaam.hbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DatabaseManager {
	
	
	//this is a master class to get the connection
	public static Connection getConnection(){
		
		//get all the environment variables in a map 
		Map<String, String> env = System.getenv(); 
		Connection conn = null;

		String ip = "localhost";
		String key = "spm";
		String user = "root";
		String schema  = "cakart";
		
		String DB_URL = "jdbc:mysql://"+ip+"/"+schema+"";
		
		//System.out.println(DB_URL);
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,user,key);
			//System.out.println("we got the connection...");
		}
		catch (Exception exp){
			exp.printStackTrace();
			//return null object if execption occured
			return null;
		}
		return conn;
	}
	
	//this is the single point where connection will be closed
	public static void closeDB(Connection conn){
		
		//close the connection
		try{
			conn.close();
		}
		catch (Exception exp){
			//nothing to be done here.
		}
		
	}

	
	public static int executeUpdate(String updateQuery){
		
		int updated = 0;
		Connection conn = null;
		Statement stmt = null;
				
		try{
			conn = getConnection();
			stmt = conn.createStatement();
			updated = stmt.executeUpdate(updateQuery);
		}
		catch(Exception exp){
			updated =  -1;
		}finally{
			try{
				stmt.close();
				closeDB(conn);
			}
			catch (Exception exp){
				exp.printStackTrace();
			}
		}
		
		return updated;
		
	}

	public static List<ArrayList> executedQuery(String selectQuery){
		//System.out.println(selectQuery);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData meta = null;
		List<ArrayList> list = new ArrayList();
				
		try{
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectQuery);
			meta = rs.getMetaData();
			int columnCt = meta.getColumnCount();
			
			//System.out.println(columnCt);
			while(rs.next()){
				ArrayList tmp = new ArrayList();
				for (int i = 1; i <= columnCt; i++) {
					Object rowObj = rs.getObject(i);
					tmp.add(rowObj);
				}
				list.add(tmp);
		      }
		}
		catch(Exception exp){
			exp.printStackTrace();
			list = null;
		}finally{
			try{
				stmt.close();
				conn.close();
			}
			catch (Exception exp){
				exp.printStackTrace();
				//nothing is required}
			}
		}
		
		return list;
	}

}
