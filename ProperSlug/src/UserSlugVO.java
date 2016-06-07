
public class UserSlugVO {
	
	private String user_id;
	private String slug;
	private String date;
	private String hour;
	private String min;
	
	private String category;
	
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public UserSlugVO(String user_id, String slug, String date, String hour, String min, String category) {
		super();
		this.user_id = user_id;
		this.slug = slug;
		this.date = date;
		this.hour = hour;
		this.min = min;
		this.category = category;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	
	

}
