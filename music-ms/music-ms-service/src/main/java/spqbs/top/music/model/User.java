package spqbs.top.music.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 用户唯一标识
	 */
	  private String openId ;
	  /**
	   * 用户昵称
	   */
	  private String nickName ;
	  /**
	   * 密码
	   */
	  private String password ;
	  /**
	   * 性别
	   */
	  private String gender;
	  /**
	   * 生日
	   */
	  private Date birthDate  ;
	  /**
	   * 住址
	   */
	  private String address ;
	  /**
	   * 头像
	   */
	  private String image ;
	  /**
	   * 个人介绍
	   */
	  private String introduction ;
	  /**
	   * session_key
	   */
	  private String  sessionKey ;
	  private Date lastTime;
	
	  
	 
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	@Override
	public String toString() {
		return "User [openId=" + openId + ", nickName=" + nickName + ", password=" + password + ", gender=" + gender
				+ ", birthDate=" + birthDate + ", address=" + address + ", image=" + image + ", introduction="
				+ introduction + ", sessionKey=" + sessionKey + ", lastTime=" + lastTime + "]";
	}
	 
	
}
