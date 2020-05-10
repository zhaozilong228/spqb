package spqbs.top.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spqbs.top.music.common.rest.RestResult;
import spqbs.top.music.model.Favorite;
import spqbs.top.music.model.Music;
import spqbs.top.music.model.MusicAttach;
import spqbs.top.music.model.PlayList;
import spqbs.top.music.model.UserLike;
import spqbs.top.music.model.UserPlayList;
import spqbs.top.music.service.IUserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private  IUserService userServiceImpl;
	
	@RequestMapping("/addFavorite")
	public RestResult addFavorite(@RequestBody Favorite param){
		return RestResult.success(userServiceImpl.addFavorite(param));
	}
	
	@RequestMapping("/findUserFavoriteList")
	public RestResult findUserFavoriteList(@RequestBody Favorite param){
		return RestResult.success(userServiceImpl.findUserFavoriteList(param));
	}
	
	/**
	 * @Description: 用户创建歌单
	 * @author zhao zilong
	 */
	@RequestMapping("/addMyPalyList")
	public RestResult addMyPalyList(@RequestBody UserPlayList param){
		//	初始化封面	
		param.setPicUrl("http://p1.music.126.net/tGHU62DTszbFQ37W9qPHcg==/2002210674180197.jpg");
		param.setMusicCount(0);
		return RestResult.success(userServiceImpl.addMyPalyList(param));
	}
	
	@RequestMapping("/findMyPalyList/{opneId}")
	public RestResult findMyPalyList(@PathVariable String opneId){
		return RestResult.success(userServiceImpl.findMyPalyList(opneId));
	}
	
	@RequestMapping("/addMusicToPlayList")
	public RestResult addMusicToPlayList(@RequestBody MusicAttach param){
		Integer result = userServiceImpl.addMusicToPlayList(param);
		if(result == 0){
			return RestResult.fail("502", "歌曲已存在");
		}
		return 	RestResult.success(result);
	}
	
	@RequestMapping("/findMyMusic/{playlistCode}")
	public RestResult findMyMusic(@PathVariable String playlistCode){
		return RestResult.success(userServiceImpl.findMyMusic(playlistCode));
	}
	
	@RequestMapping("/addLikeMusic")
	public RestResult addLikeMusic(@RequestBody UserLike param){
		return RestResult.success(userServiceImpl.addLikeMusic(param));
	}
	
	@RequestMapping("/findLikeMusic")
	public RestResult findLikeMusic(@RequestBody Music param){
		return RestResult.success(userServiceImpl.findLikeMusic(param));
	}
	
	@RequestMapping("/delLike")
	public RestResult delLike(@RequestBody UserLike param){
		return RestResult.success(userServiceImpl.delLike(param));
	}
	
	@RequestMapping("/findUserPalyFavorite")
	public RestResult findUserPalyFavorite(@RequestBody Favorite param){
		return RestResult.success(userServiceImpl.findUserPalyByCodeAndOpenId(param));
	}
	
	@RequestMapping("/delFavorite")
	public RestResult delFavorite(@RequestBody Favorite param){
		return RestResult.success(userServiceImpl.delFavorite(param));
	}
}
