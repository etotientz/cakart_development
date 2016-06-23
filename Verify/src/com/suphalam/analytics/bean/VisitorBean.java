package com.suphalam.analytics.bean;

import java.util.List;

public class VisitorBean {
	
	private String visitorID;
	private String city;
	private String device;
	private String browser;
	private String source;
	private int totalHit;
	private String lastTime;
	
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	private List<PageBean> pageList;
	
	//default constructor
	public VisitorBean(){}
	public VisitorBean(String visitorID, String city, String device,
			String browser, String source, int totalHit) {
		this.visitorID = visitorID;
		this.city = city;
		this.device = device;
		this.browser = browser;
		this.source = source;
		this.totalHit = totalHit;
	}

	public String getVisitorID() {
		return visitorID;
	}
	public void setVisitorID(String visitorID) {
		this.visitorID = visitorID;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getTotalHit() {
		return totalHit;
	}
	public void setTotalHit(int totalHit) {
		this.totalHit = totalHit;
	}

	@Override
	public String toString() {
		return "VisitorBean [visitorID=" + visitorID + ", city=" + city
				+ ", device=" + device + ", browser=" + browser + ", source="
				+ source + ", totalHit=" + totalHit
				+ ", lastTime=" + lastTime
				+ "\nURLs={"+pageList+"}\n]";
	}

	public List<PageBean> getPageList() {
		return pageList;
	}

	public void setPageList(List<PageBean> pageList) {
		this.pageList = pageList;
	}
}
