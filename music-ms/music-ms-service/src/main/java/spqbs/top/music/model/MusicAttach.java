package spqbs.top.music.model;

import java.io.Serializable;

import spqbs.top.music.common.util.PageableParam;

public class MusicAttach extends PageableParam implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * ±àºÅ
	 */
	  private String code;
	  /**
	   * ¸èµ¥Ãû³Æ
	   */
	  private String  playlistName ;
	  /**
	   * ¸èµ¥±àÂë
	   */
	  private String  playlistCode ;
	  /**
	   * ¸èÇúÃû³Æ
	   */
	  private String  musicName;
	  /**
	   * ¸èÇú±àÂë
	   */
	  private String  musicCode ;
	  /**
	   * ×¨¼­Ãû³Æ
	   */
	  private String albumName ;
	  /**
	   * ¸èÊÖÃû³Æ
	   */
	  private String artistName ;
	  
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

	@Override
	public String toString() {
		return "MusicAttach [code=" + code + ", playlistName=" + playlistName + ", playlistCode=" + playlistCode
				+ ", musicName=" + musicName + ", musicCode=" + musicCode + ", albumName=" + albumName + ", artistName="
				+ artistName + "]";
	}

	

}
