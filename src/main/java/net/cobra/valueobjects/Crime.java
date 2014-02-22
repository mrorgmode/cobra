package net.cobra.valueobjects;

import java.util.Date;

public class Crime {
	private long id;
	private Date date;
	private String type;
	private String location;
	private String desc;
	private String moreDesc;
	
	public Crime() { }

	
	public Crime(long id, Date date, String type, String location, String desc,
			String moreDesc) {
		this.id = id;
		this.date = date;
		this.type = type;
		this.location = location;
		this.desc = desc;
		this.moreDesc = moreDesc;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMoreDesc() {
		return moreDesc;
	}

	public void setMoreDesc(String moreDesc) {
		this.moreDesc = moreDesc;
	}


	@Override
	public String toString() {
		return "Crime [id=" + id + ", date=" + date + ", type=" + type
				+ ", location=" + location + ", desc=" + desc + ", moreDesc="
				+ moreDesc + "]";
	}
}
