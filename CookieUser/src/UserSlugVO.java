
public class UserSlugVO {

	private String id;
	private String user_id;
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

	public UserSlugVO(String id, String user_id, String date, String min, String channel, String browser,
			String category) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.date = date;

		// this.hour = hour;
		this.min = min;
		this.channel = channel;
		this.browser = browser;
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

	public String getuser_id() {
		return user_id;
	}

	public void setSlug(String user_id) {
		this.user_id = user_id;
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