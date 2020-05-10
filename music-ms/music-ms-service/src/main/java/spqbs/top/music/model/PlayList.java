package spqbs.top.music.model;

import java.io.Serializable;

import spqbs.top.music.common.util.PageableParam;

public class PlayList extends PageableParam implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code ;
	private String name ;
	private String description ;
	private String tag ;
	private Integer playNum ;
	private String coverUrl;
	private Integer orderValue;
	private Integer recommend;
	private String status;
	private Integer musicCount;
	private String openId;
	private Boolean favoriteFlag;
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
	public Integer getPlayNum() {
		return playNum;
	}
	public void setPlayNum(Integer playNum) {
		this.playNum = playNum;
	}
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	public Integer getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(Integer orderValue) {
		this.orderValue = orderValue;
	}
	public Integer getRecommend() {
		return recommend;
	}
	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Boolean getFavoriteFlag() {
		return favoriteFlag;
	}
	public void setFavoriteFlag(Boolean favoriteFlag) {
		this.favoriteFlag = favoriteFlag;
	}
	@Override
	public String toString() {
		return "PlayList [code=" + code + ", name=" + name + ", description=" + description + ", tag=" + tag
				+ ", playNum=" + playNum + ", coverUrl=" + coverUrl + ", orderValue=" + orderValue + ", recommend="
				+ recommend + ", status=" + status + ", musicCount=" + musicCount + ", openId=" + openId
				+ ", favoriteFlag=" + favoriteFlag + "]";
	}
	
}
