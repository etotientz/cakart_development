package com.suphalam.analytics.bean;

public class PageBean {
	
	private String pagePath;
	private int hourIndex;
	private int minIndex;
	private int hit;
	
	
	public PageBean(String pagePath, 
					int hrIndex, 
					int minIndex,
					int hit){
		this.pagePath = pagePath;
		this.hourIndex=hrIndex;
		this.minIndex =minIndex;
		this.hit =hit; 
		
	}
	public String getPagePath() {
		return pagePath;
	}
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
	public int getHourIndex() {
		return hourIndex;
	}
	public void setHourIndex(int hourIndex) {
		this.hourIndex = hourIndex;
	}
	public int getMinIndex() {
		return minIndex;
	}
	public void setMinIndex(int minIndex) {
		this.minIndex = minIndex;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	
	@Override
	public String toString() {
		return "PageBean [pagePath=" + pagePath + ", hourIndex=" + hourIndex
				+ ", minIndex=" + minIndex + ", hit=" + hit + "]";
	}
	
}
