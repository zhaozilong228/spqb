package spqbs.top;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spqbs.top.music.common.rest.RestResult;
import spqbs.top.music.model.Favorite;
import spqbs.top.music.model.PlayList;
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
		return RestResult.success(userServiceImpl.addMyPalyList(param));
	}
	
	@RequestMapping("/findMyPalyList/{opneId}")
	public RestResult findMyPalyList(@PathVariable String opneId){
		return RestResult.success(userServiceImpl.findMyPalyList(opneId));
	}
	
	@RequestMapping("/addMusicToPlayList")
	public RestResult addMusicToPlayList(@RequestBody PlayList param){
		return RestResult.success(userServiceImpl.addMusicToPlayList(param));
	}
	
	@RequestMapping("/findMyMusic/{playlistCode}")
	public RestResult findMyMusic(@PathVariable String playlistCode){
		return RestResult.success(userServiceImpl.findMyMusic(playlistCode));
	}
}
