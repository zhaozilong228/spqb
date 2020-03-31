package spqbs.top.music.model;

import java.io.Serializable;

public class UserLike implements Serializable{

	private static final long serialVersionUID = 1L;
	private String code;
	private String musicName;
	private String musicCode;
	private String albumName;
	private String artistName;
	private Integer playNum;
	private String openId;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public Integer getPlayNum() {
		return playNum;
	}
	public void setPlayNum(Integer playNum) {
		this.playNum = playNum;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	@Override
	public String toString() {
		return "UserLike [code=" + code + ", musicName=" + musicName + ", musicCode=" + musicCode + ", albumName="
				+ albumName + ", artistName=" + artistName + ", playNum=" + playNum + ", openId=" + openId + "]";
	}
	
	
}
