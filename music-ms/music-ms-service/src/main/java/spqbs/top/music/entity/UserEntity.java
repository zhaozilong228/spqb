package spqbs.top.music.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_user" )
public class UserEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * �û�Ψһ��ʶ
	 */
	 @Id
	 @Column(name="openId" ,length=191)
	  private String openId ;
	  /**
	   * �û��ǳ�
	   */
	 @Column(name="nickName" ,length=191)
	  private String nickName ;
	  /**
	   * ����
	   */
	 @Column(name="password" ,length=191)
	  private String password ;
	  /**
	   * �Ա�
	   */
	 @Column(name="gender" ,length=191)
	  private String gender;
	  /**
	   * ����
	   */
	 @Column(name="birthDate" ,length=191)
	  private Date birthDate  ;
	  /**
	   * סַ
	   */
	 @Column(name="address" ,length=191)
	  private String address ;
	  /**
	   * ͷ��
	   */
	 @Column(name="image" ,length=191)
	  private String image ;
	  /**
	   * ���˽���
	   */
	 @Column(name="introduction" ,length=191)
	  private String introduction ;
	  /**
	   * session_key
	   */
	  private String  sessionKey ;
	  @Column(name="lastTime" ,length=191)
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
