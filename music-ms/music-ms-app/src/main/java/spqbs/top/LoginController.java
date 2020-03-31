package spqbs.top;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import spqbs.top.music.common.rest.RestResult;
import spqbs.top.music.dao.UserMapper;
import spqbs.top.music.model.LoginModel;
import spqbs.top.music.model.User;

@RestController
public class LoginController {
	
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping("/auth")
	public RestResult auth(@RequestBody LoginModel param) {
		String appId = "wx6f31993fd301ba67";
		String AppSecret = "ee96fcbd6dec72d71584eb81954568d1"; //AppSecret(小程序密钥)
		String url ="https://api.weixin.qq.com/sns/jscode2session?";
		url+="appid="+appId;
		url+= "&secret="+AppSecret;
		url+="&js_code=" + param.getCode();
		url+= "&grant_type=authorization_code";
		System.out.println("get:"+ url);
//		HttpPost httpPost = new HttpPost(url+"/shortlinks/findStatList");
		HttpGet httpGet = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(10000).setConnectTimeout(10000).build();
		httpGet.setConfig(requestConfig);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		httpGet.setHeader("Content-Type", "application/json");
		HttpResponse rese;
		String redsa ="";
		try {
			rese = httpClient.execute(httpGet);
			 redsa = EntityUtils.toString(rese.getEntity());
			 JSONObject respObj = JSONObject.parseObject(redsa);
			 if(!"".equals(respObj.getString("openid"))&&!"".equals(respObj.getString("session_key"))){
				 User result = new User();
				 result.setOpenId(respObj.getString("openid"));
				 result.setSessionKey(respObj.getString("session_key"));
				 User user = userMapper.findOpenid(respObj.getString("openid"));
				 //第一次登陆	先保存			 
				 if(user ==null){
					 User saveUser = new User();
					 saveUser.setOpenId(respObj.getString("openid"));
					 userMapper.saveUser(saveUser);
				 }
				 return RestResult.success(result);
			 }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return RestResult.success(redsa) ;
	}
}
