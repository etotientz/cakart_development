package com.suphalam.analytics;

public class TrafficBySourceBean {
	
	//this is the key which will hold the key
	private String hourlyIndex; 
	private int totalSessionCount = 0;
	private int totalQualitySessionCount = 0; // some segmentation has to be applied
	private int directSessionCount = 0;
	private int emailSessionCount = 0;
	private int organicSearchSessionCount = 0;
	private int referralSessionCount = 0;
	private int socialSessionCount = 0;
	private int notSetSessionCount = 0;
	private int annoucementSessionCount = 0;
	
	
	//default construct can not be called.
	private TrafficBySourceBean(){}
	
	//only this constructor need to be called
	public TrafficBySourceBean(String hourlyIndex){
		this.hourlyIndex= hourlyIndex; 
	}
	
	public String getHourlyIndex() {
		
		
		String hourPart = hourlyIndex.substring(8,hourlyIndex.length());
		int hourPartInt = Integer.parseInt(hourPart);
		if(hourPartInt<12){
			return hourPart+" Hr";
		}
		else{
			return hourPart+" Hr";
		}
		//return hourlyIndex;
	}
	public void setHourlyIndex(String hourlyIndex) {
		this.hourlyIndex = hourlyIndex;
	}
	
	
	public int getTotalSessionCount() {
		return directSessionCount+emailSessionCount+organicSearchSessionCount+referralSessionCount+socialSessionCount+notSetSessionCount;
	}
	
	public int getTotalSessionCounttPercentage() {
		return (totalSessionCount/totalSessionCount);
	}
	
	public void setTotalSessionCount(int totalSessionCount) {
		this.totalSessionCount = totalSessionCount;
	}
	public int getDirectSessionCount() {
		return directSessionCount;
	}
	public int getDirectSessionCountPercentage() {
		return (directSessionCount/totalSessionCount);
	}
	public void setDirectSessionCount(int directSessionCount) {
		this.directSessionCount = directSessionCount;
	}
	public int getEmailSessionCount() {
		return emailSessionCount;
	}
	public int getEmailSessionCountPercentage() {
		return (emailSessionCount/totalSessionCount);
	}
	public void setEmailSessionCount(int emailSessionCount) {
		this.emailSessionCount = emailSessionCount;
	}
	public int getOrganicSearchSessionCount() {
		return organicSearchSessionCount;
	}
	public int getOrganicSearchSessionCountPercentage() {
		return (organicSearchSessionCount/totalSessionCount);
	}
	public void setOrganicSearchSessionCount(int organicSearchSessionCount) {
		this.organicSearchSessionCount = organicSearchSessionCount;
	}
	public int getReferralSessionCount() {
		return referralSessionCount;
	}
	public int getReferralSessionCountPercentage() {
		return (referralSessionCount/totalSessionCount);
	}
	public void setReferralSessionCount(int referralSessionCount) {
		this.referralSessionCount = referralSessionCount;
	}
	public int getSocialSessionCount() {
		return socialSessionCount;
	}
	public int getSocialSessionCountPercentage() {
		return (socialSessionCount/totalSessionCount);
	}
	public void setSocialSessionCount(int socialSessionCount) {
		this.socialSessionCount = socialSessionCount;
	}
	public int getAnnoucementSessionCount() {
		return annoucementSessionCount;
	}
	public int getAnnoucementSessionCountPercentage() {
		return (annoucementSessionCount/totalSessionCount);
	}
	public void setAnnoucementSessionCount(int annoucementSessionCount) {
		this.annoucementSessionCount = annoucementSessionCount;
	}
	public int getTotalQualitySessionCount() {
		return totalQualitySessionCount;
	}
	public void setTotalQualitySessionCount(int totalQualitySessionCount) {
		this.totalQualitySessionCount = totalQualitySessionCount;
	}

	public int getNotSetSessionCount() {
		return notSetSessionCount;
	}
	
	public int getNotSetSessionCountPercentage() {
		return (notSetSessionCount/totalSessionCount);
	}

	public void setNotSetSessionCount(int notSetSessionCount) {
		this.notSetSessionCount = notSetSessionCount;
	}
	
	
	@Override
	public String toString(){
		
		return (
				hourlyIndex+"\t"+ 
				totalSessionCount+"\t"+
				directSessionCount+"\t"+
				emailSessionCount+"\t"+
				organicSearchSessionCount+"\t"+
				referralSessionCount+"\t"+
				socialSessionCount+"\t"+
				notSetSessionCount+"\t"+
				totalQualitySessionCount+"\t"+
				annoucementSessionCount);
	}

}
