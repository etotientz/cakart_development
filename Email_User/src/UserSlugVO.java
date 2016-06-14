
public class UserSlugVO {
	
	private String id;
	private String slug;
	private String date;
	private String channel;
	private String browser;
	private String min;
	
	private String category;
	
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public UserSlugVO(String id, String slug, String date, String min,String channel,String browser, String category) {
		super();
		this.id = id;
		this.slug = slug;
		this.date = date;
		
		//this.hour = hour;
		this.min = min;
		this.channel=channel;
		this.browser=browser;
		this.category = category;
	}
	public String getChannel_id() {
		return channel;
	}
	public void setChannel_id(String channel) {
		this.channel = channel;
	}
	public String getBrowser_id() {
		return browser;
	}
	public void setBrowser_id(String browser) {
		this.browser = browser;
	}
	
	
	
	
	
	public String getUser_id() {
		return id;
	}
	public void setUser_id(String user_id) {
		this.id = id;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	
	

}