package spqbs.top.music.common.rest;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * rest�ӿڵĽ��
 *
 */
public class RestResult implements Serializable{
	private static final long serialVersionUID = 1619207641955282314L;
	private static final Logger LOGGER = LoggerFactory.getLogger(RestResult.class);

	/**
	 * ���صĽ��code
	 */
	private String resultCode;
	
	/**
	 * ���صĽ�����󣬽��ʧ��ʱΪ��
	 */
	private Object resultData;
	
	/**
	 * ���Ϊʧ��ʱ�ķ�����Ϣ���ɹ�ʱû������
	 */
	private String message;
	
	/**
	 * errorStackTrack���쳣ջ�����Զ����쳣ʱ��Ҫ��������ݣ������Ų�
	 */
	private String errorStackTrack;
	
	
	/**
	 * �ɹ��ķ���ֵʵ��������
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
	 * �ɹ��ķ���ֵʵ��������
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
	 * ʧ�ܵķ���ֵ���췽��
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
