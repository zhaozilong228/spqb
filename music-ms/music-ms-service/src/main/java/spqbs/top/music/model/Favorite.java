package spqbs.top.music.model;

import java.io.Serializable;

public class Favorite implements  Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String favoriteType;
	private String playlistCode ;
	private String playlistName;
	private String playNum;
	private String musicCount;
	private String openId;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFavoriteType() {
		return favoriteType;
	}
	public void setFavoriteType(String favoriteType) {
		this.favoriteType = favoriteType;
	}
	public String getPlaylistCode() {
		return playlistCode;
	}
	public void setPlaylistCode(String playlistCode) {
		this.playlistCode = playlistCode;
	}
	public String getPlaylistName() {
		return playlistName;
	}
	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}
	public String getPlayNum() {
		return playNum;
	}
	public void setPlayNum(String playNum) {
		this.playNum = playNum;
	}
	public String getMusicCount() {
		return musicCount;
	}
	public void setMusicCount(String musicCount) {
		this.musicCount = musicCount;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	@Override
	public String toString() {
		return "Favorite [code=" + code + ", favoriteType=" + favoriteType + ", playlistCode=" + playlistCode
				+ ", playlistName=" + playlistName + ", playNum=" + playNum + ", musicCount=" + musicCount + ", openId="
				+ openId + "]";
	}
	
}
