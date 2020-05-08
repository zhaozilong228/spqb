package spqbs.top.music.model;

import java.io.Serializable;

public class UserPlayList implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String code;
	private String name ;
	private Integer playNum ;
	private Integer musicCount;
	private String openId;
	private String picUrl;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPlayNum() {
		return playNum;
	}
	public void setPlayNum(Integer playNum) {
		this.playNum = playNum;
	}
	public Integer getMusicCount() {
		return musicCount;
	}
	public void setMusicCount(Integer musicCount) {
		this.musicCount = musicCount;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	@Override
	public String toString() {
		return "UserPlayList [code=" + code + ", name=" + name + ", playNum=" + playNum + ", musicCount=" + musicCount
				+ ", openId=" + openId + ", picUrl=" + picUrl + "]";
	}
	
}
