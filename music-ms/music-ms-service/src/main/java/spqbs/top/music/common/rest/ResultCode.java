package spqbs.top.music.common.rest;

/**
 * rest·µ»ØÖµµÄ±àºÅ
 * @author Liu Wenjie
 *
 */
public enum ResultCode {

	SUCCESS("200","success!!"),
	NOT_AUTHORIZATION("401", "NotAuthorization"),
	ERROR_TOKEN("410","incorrect token");
	
	private String code;
	private String message;
	
	private ResultCode(String code){
		this.code = code;
	}
	private ResultCode(String code,String message){
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
}