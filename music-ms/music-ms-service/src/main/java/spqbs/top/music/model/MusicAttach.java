package spqbs.top.music.model;

import java.io.Serializable;

import spqbs.top.music.common.util.PageableParam;

public class MusicAttach extends PageableParam implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 编号
	 */
	  private String code;
	  /**
	   * 歌单名称
	   */
	  private String  playlistName ;
	  /**
	   * 歌单编码
	   */
	  private String  playlistCode ;
	  /**
	   * 歌曲名称
	   */
	  private String  musicName;
	  /**
	   * 歌曲编码
	   */
	  private String  musicCode ;
	  /**
	   * 专辑名称
	   */
	  private String albumName ;
	  /**
	   * 歌手名称
	   */
	  private String artistName ;
	  /**
	   * 用户歌单封面
	   */
	  private String palyCoverUrl;
	  /**
	   * 用户歌单歌曲数量
	   */
	  private Integer musicCount;
	  
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public String getPlaylistCode() {
		return playlistCode;
	}

	public void setPlaylistCode(String playlistCode) {
		this.playlistCode = playlistCode;
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

	public String getPalyCoverUrl() {
		return palyCoverUrl;
	}

	public void setPalyCoverUrl(String palyCoverUrl) {
		this.palyCoverUrl = palyCoverUrl;
	}

	public Integer getMusicCount() {
		return musicCount;
	}

	public void setMusicCount(Integer musicCount) {
		this.musicCount = musicCount;
	}

	@Override
	public String toString() {
		return "MusicAttach [code=" + code + ", playlistName=" + playlistName + ", playlistCode=" + playlistCode
				+ ", musicName=" + musicName + ", musicCode=" + musicCode + ", albumName=" + albumName + ", artistName="
				+ artistName + ", palyCoverUrl=" + palyCoverUrl + ", musicCount=" + musicCount + "]";
	}
	
}
