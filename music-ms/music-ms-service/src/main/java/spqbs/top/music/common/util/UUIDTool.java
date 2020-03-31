package spqbs.top.music.common.util;

import java.util.Random;
//import org.bson.types.ObjectId;
public class UUIDTool {
	/**
	 * Description: 获取一个mongoDB类型的id字符串
	 * @author Liu Wenjie
	 * @date 2018年1月17日 下午3:04:32
	 * @return
	 */
//	public static String getObjectId(){
//		return ObjectId.get().toHexString();
//	}
	/**
	 * Description: 生成随机纯数字
	 * @author Chu Linpeng
	 * @date 2018年2月12日 上午11:45:32
	 * @param num 生成随机数位数
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
