package spqbs.top.music.model;

import java.io.Serializable;
import java.util.List;

import spqbs.top.music.common.util.PageableParam;

public class RankList extends PageableParam implements Serializable{
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
	private List<RankAttach> previewMusicList;
	private Integer musicCount;
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
	public List<RankAttach> getPreviewMusicList() {
		return previewMusicList;
	}
	public void setPreviewMusicList(List<RankAttach> previewMusicList) {
		this.previewMusicList = previewMusicList;
	}
	public Integer getMusicCount() {
		return musicCount;
	}
	public void setMusicCount(Integer musicCount) {
		this.musicCount = musicCount;
	}
	@Override
	public String toString() {
		return "RankList [code=" + code + ", name=" + name + ", description=" + description + ", tag=" + tag
				+ ", playNum=" + playNum + ", coverUrl=" + coverUrl + ", orderValue=" + orderValue + ", recommend="
				+ recommend + ", status=" + status + ", previewMusicList=" + previewMusicList + ", musicCount="
				+ musicCount + "]";
	}
	
	
}
