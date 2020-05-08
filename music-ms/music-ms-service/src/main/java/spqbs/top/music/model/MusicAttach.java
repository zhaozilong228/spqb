package spqbs.top.music.model;

import java.io.Serializable;

import spqbs.top.music.common.util.PageableParam;

public class MusicAttach extends PageableParam implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * ���
	 */
	  private String code;
	  /**
	   * �赥����
	   */
	  private String  playlistName ;
	  /**
	   * �赥����
	   */
	  private String  playlistCode ;
	  /**
	   * ��������
	   */
	  private String  musicName;
	  /**
	   * ��������
	   */
	  private String  musicCode ;
	  /**
	   * ר������
	   */
	  private String albumName ;
	  /**
	   * ��������
	   */
	  private String artistName ;
	  /**
	   * �û��赥����
	   */
	  private String palyCoverUrl;
	  /**
	   * �û��赥��������
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
