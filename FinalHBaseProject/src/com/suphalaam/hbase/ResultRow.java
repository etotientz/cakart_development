package com.suphalaam.hbase;

public  class ResultRow {
	
	private String cookieID;
	private String date;
	private String url;
	
	

	public ResultRow(String cookieID, String date, String url) {
		super();
		this.cookieID = cookieID;
		this.date = date;
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public ResultRow(String cookieID, String date) {
		super();
		this.cookieID = cookieID;
		this.date = date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ResultRow(String cookieID) {
		super();
		this.cookieID = cookieID;
	}

	public String getCookieID() {
		return cookieID;
	}

	public void setCookieID(String cookieID) {
		this.cookieID = cookieID;
	}

	@Override
	public String toString() {
		return "ResultRow [cookieID=" + cookieID + ", date=" + date + ", url=" + url + "]";
	}
	

}
