package spqbs.top.music.model;

import java.io.Serializable;

import spqbs.top.music.common.util.PageableParam;

public class Recommend  extends PageableParam implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id ;
	private String theme ;
	private String coverUrl ;
	private String date ;
	private String startTime;
	private String endTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Override
	public String toString() {
		return "Recommend [id=" + id + ", theme=" + theme + ", coverUrl=" + coverUrl + ", date=" + date + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}
	
	 
}
