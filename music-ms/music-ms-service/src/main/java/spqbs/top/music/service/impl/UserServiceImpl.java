package spqbs.top.music.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spqbs.top.music.dao.UserMapper;
import spqbs.top.music.model.Favorite;
import spqbs.top.music.model.MusicAttach;
import spqbs.top.music.model.PlayList;
import spqbs.top.music.model.User;
import spqbs.top.music.model.UserLike;
import spqbs.top.music.model.UserPlayList;
import spqbs.top.music.service.IUserService;

@Service(value="userServiceImpl")
public class UserServiceImpl implements IUserService{
//	http://p1.music.126.net/EWC8bPR8WW9KvhaftdmsXQ==/3397490930543093.jpg œ≤ª∂µƒ“Ù¿÷±≥æ∞Õºµÿ÷∑
	
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
	public Integer addMusicToPlayList(PlayList param) {
		String code =UUID.randomUUID().toString().replace("-", "");
		param.setCode(code);
		return userMapper.addMusicToPlayList(param);
	}

	@Override
	public MusicAttach findMyMusic(String playlistCode) {
		return userMapper.findMyMusic(playlistCode);
	}

	@Override
	public Integer addLikeMusic(UserLike param) {
		return userMapper.addLikeMusic(param);
	}

	@Override
	public UserLike findLikeMusic(String opneId) {
		return userMapper.findLikeMusic(opneId);
	}

	@Override
	public List<UserPlayList> findMyPalyList(String opneId) {
		return userMapper.findMyPalyList(opneId);
	}

}
