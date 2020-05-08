package spqbs.top.music.model;

import java.io.Serializable;

import spqbs.top.music.common.util.PageableParam;

public class RecommendAttach  extends PageableParam  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id  ;
	 private String recommendDate ;
	 private String musicName ;
	 private String musicCode ;
	 private String  albumName ;
	 private String artistName ;
	 private String recommendTitle ;
	 private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRecommendDate() {
		return recommendDate;
	}
	public void setRecommendDate(String recommendDate) {
		this.recommendDate = recommendDate;
	}
	public String getMusicName() {
		return musicName;
	}
	public void setMusicName(String musicName) {
		this.musicName = musicName;
	}
	public String getMusicCode() {
		return musicCode;
	}
	public void setMusicCode(String musicCode) {
		this.musicCode = musicCode;
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
	public String getRecommendTitle() {
		return recommendTitle;
	}
	public void setRecommendTitle(String recommendTitle) {
		this.recommendTitle = recommendTitle;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Recommend [id=" + id + ", recommendDate=" + recommendDate + ", musicName=" + musicName + ", musicCode="
				+ musicCode + ", albumName=" + albumName + ", artistName=" + artistName + ", recommendTitle="
				+ recommendTitle + ", remark=" + remark + "]";
	}
	
}
