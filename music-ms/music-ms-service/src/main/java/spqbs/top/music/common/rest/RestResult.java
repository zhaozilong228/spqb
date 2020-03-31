package spqbs.top.music.common.rest;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * rest接口的结果
 *
 */
public class RestResult implements Serializable{
	private static final long serialVersionUID = 1619207641955282314L;
	private static final Logger LOGGER = LoggerFactory.getLogger(RestResult.class);

	/**
	 * 返回的结果code
	 */
	private String resultCode;
	
	/**
	 * 返回的结果对象，结果失败时为空
	 */
	private Object resultData;
	
	/**
	 * 结果为失败时的返回消息，成功时没有内容
	 */
	private String message;
	
	/**
	 * errorStackTrack，异常栈，非自定义异常时需要输出此内容，方便排查
	 */
	private String errorStackTrack;
	
	
	/**
	 * 成功的返回值实例化方法
	 * @param object
	 * @return
	 */
	public static RestResult success(Object object){
		RestResult result = new RestResult();
		result.setResultCode(ResultCode.SUCCESS.getCode());
		result.setResultData(object);
		return result;
	}
	
	/**
	 * 成功的返回值实例化方法
	 * @param object
	 * @return
	 */
	public static RestResult success(){
		RestResult result = new RestResult();
		result.setResultCode(ResultCode.SUCCESS.getCode());
		result.setResultData(ResultCode.SUCCESS.getMessage());
		return result;
	}
	
	/**
	 * 失败的返回值构造方法
	 * @param code
	 * @param message
	 * @return
	 */
	public static RestResult fail(String code,String message){
		RestResult result = new RestResult();
		result.setResultCode(code);
		result.setMessage(message);
		return result;
	}
	
	
	/*==========getter and setter ==========*/
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public Object getResultData() {
		return resultData;
	}

	public void setResultData(Object resultData) {
		this.resultData = resultData;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorStackTrack() {
		return errorStackTrack;
	}

	public void setErrorStackTrack(String errorStackTrack) {
		this.errorStackTrack = errorStackTrack;
	}

	@Override
	public String toString() {
		return "RestResult [resultCode=" + resultCode + ", resultData=" + resultData + ", message=" + message
				+ ", errorStackTrack=" + errorStackTrack + "]";
	}
}
