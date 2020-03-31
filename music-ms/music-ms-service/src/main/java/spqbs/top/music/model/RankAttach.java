package spqbs.top.music.model;

import java.io.Serializable;
import spqbs.top.music.common.util.PageableParam;

public class RankAttach extends PageableParam implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * ���
	 */
	  private String code;
	  /**
	   * ������
	   */
	  private String  ranklistName ;
	  /**
	   * �񵥱���
	   */
	  private String  ranklistCode ;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRanklistName() {
		return ranklistName;
	}
	public void setRanklistName(String ranklistName) {
		this.ranklistName = ranklistName;
	}
	public String getRanklistCode() {
		return ranklistCode;
	}
	public void setRanklistCode(String ranklistCode) {
		this.ranklistCode = ranklistCode;
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
		return "RankAttach [code=" + code + ", ranklistName=" + ranklistName + ", ranklistCode=" + ranklistCode
				+ ", musicName=" + musicName + ", musicCode=" + musicCode + ", albumName=" + albumName + ", artistName="
				+ artistName + "]";
	}
	  
}
