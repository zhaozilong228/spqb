package spqbs.top.music.common.util;

import java.util.Random;
//import org.bson.types.ObjectId;
public class UUIDTool {
	/**
	 * Description: ��ȡһ��mongoDB���͵�id�ַ���
	 * @author Liu Wenjie
	 * @date 2018��1��17�� ����3:04:32
	 * @return
	 */
//	public static String getObjectId(){
//		return ObjectId.get().toHexString();
//	}
	/**
	 * Description: �������������
	 * @author Chu Linpeng
	 * @date 2018��2��12�� ����11:45:32
	 * @param num ���������λ��
	 * @return
	 */
	public static String getRandomNumber(int num){
		String sources = "0123456789";
	    Random rand = new Random();
	    StringBuffer flag = new StringBuffer();
	    for (int j = 0; j < num; j++){  
	        flag.append(sources.charAt(rand.nextInt(9)) + "");
	    }
	    return flag.toString();
	}
}
