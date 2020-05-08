package spqbs.top.music.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spqbs.top.music.dao.UserMapper;
import spqbs.top.music.model.Favorite;
import spqbs.top.music.model.MusicAttach;
import spqbs.top.music.model.User;
import spqbs.top.music.model.UserLike;
import spqbs.top.music.model.UserPlayList;
import spqbs.top.music.service.IUserService;

@Service(value="userServiceImpl")
public class UserServiceImpl implements IUserService{
//	http://p1.music.126.net/EWC8bPR8WW9KvhaftdmsXQ==/3397490930543093.jpg 喜欢的音乐背景图地址
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User findOpenid(String opneId) {
		return userMapper.findOpenid(opneId);
	}

	@Override
	public List<User> findUserList(User param) {
		return userMapper.findUserList(param);
	}

	@Override
	public Integer saveUser(User param) {
		return userMapper.saveUser(param);
	}

	@Override
	public Integer addFavorite(Favorite param) {
		String code =UUID.randomUUID().toString().replace("-", "");
		param.setCode(code);
		return userMapper.addFavorite(param);
	}

	@Override
	public List<Favorite> findUserFavoriteList(Favorite param) {
		return userMapper.findUserFavoriteList(param);
	}

	@Override
	public Integer addMyPalyList(UserPlayList param) {
		String code =UUID.randomUUID().toString().replace("-", "");
		param.setCode(code);
		return userMapper.addMyPalyList(param);
	}

	@Override
	public Integer addMusicToPlayList(MusicAttach param) {
		MusicAttach existMusic = userMapper.findMusicByPlayCodeAndMusicCode(param);
		//歌单中不存在		
		if(existMusic == null){
			String code =UUID.randomUUID().toString().replace("-", "");
			param.setCode(code);
			// 更新用户歌单的封面		
			UserPlayList userPlayList =new UserPlayList();
			if(StringUtils.isNotBlank(param.getPlaylistCode())){
				userPlayList.setCode(param.getPlaylistCode());
				if(StringUtils.isNotBlank(param.getPalyCoverUrl())){
					userPlayList.setPicUrl(param.getPalyCoverUrl());
					userMapper.updateMyPalyList(userPlayList);
				}
			}
			return userMapper.addMusicToPlayList(param);
		}
		return 0;
	}

	@Override
	public List<MusicAttach> findMyMusic(String playlistCode) {
		return userMapper.findMyMusic(playlistCode);
	}

	@Override
	public Integer addLikeMusic(UserLike param) {
		String code =UUID.randomUUID().toString().replace("-", "");
		param.setCode(code);
		return userMapper.addLikeMusic(param);
	}

	@Override
	public List<UserLike> findLikeMusic(String openId) {
		return userMapper.findLikeMusic(openId);
	}

	@Override
	public List<UserPlayList> findMyPalyList(String openId) {
		//先查询用户喜欢的音乐		
		Integer likeCount = userMapper.findLikeMusicCount(openId);
		List<UserPlayList> userPlayList = new ArrayList<UserPlayList>();
		UserPlayList userLike = new UserPlayList();
		userLike.setOpenId(openId);
		userLike.setMusicCount(likeCount);
		userLike.setPicUrl("http://p1.music.126.net/EWC8bPR8WW9KvhaftdmsXQ==/3397490930543093.jpg");
		userPlayList.add(userLike);
		List<UserPlayList> playList = userMapper.findMyPalyList(openId);
		if(playList.size()>0){
			playList.forEach((item)->{
				userPlayList.add(item);
			});
		}
		return userPlayList;
	}

	@Override
	public Integer updateMyPalyList(UserPlayList param) {
		return userMapper.updateMyPalyList(param);
	}

	@Override
	public Integer findLikeMusicCount(String openId) {
		return userMapper.findLikeMusicCount(openId);
	}

}
