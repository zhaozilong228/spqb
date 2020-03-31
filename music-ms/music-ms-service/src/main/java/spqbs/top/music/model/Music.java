package spqbs.top.music.model;

import java.io.Serializable;

import spqbs.top.music.common.util.PageableParam;

public class Music extends PageableParam implements Serializable
{
	private static final long serialVersionUID = 1L;
	private	String code ;
	private String name ;
	private String url ;
	private String coverUrl ;
	private String description ;
	private String tag ;
	private String type ;
	private Integer playNum ;
	private String picUrl ;
	private String albumName ;
	private String artistName ;
	private String status ;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getPlayNum() {
		return playNum;
	}
	public void setPlayNum(Integer playNum) {
		this.playNum = playNum;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Music [code=" + code + ", name=" + name + ", url=" + url + ", coverUrl=" + coverUrl + ", description="
				+ description + ", tag=" + tag + ", type=" + type + ", playNum=" + playNum + ", picUrl=" + picUrl
				+ ", albumName=" + albumName + ", artistName=" + artistName + ", status=" + status + "]";
	}
	
}