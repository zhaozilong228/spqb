package spqbs.top.music.service;

import java.util.List;
import spqbs.top.music.model.Favorite;
import spqbs.top.music.model.Music;
import spqbs.top.music.model.MusicAttach;
import spqbs.top.music.model.User;
import spqbs.top.music.model.UserLike;
import spqbs.top.music.model.UserPlayList;

public interface IUserService {

	public User findOpenid( String opneId);
	public List<User> findUserList(User param);
	public Integer saveUser(User param);
	public Integer addFavorite(Favorite param);
	public List<Favorite> findUserFavoriteList(Favorite param);
	public Integer addMyPalyList(UserPlayList param);
	public List<UserPlayList> findMyPalyList(String opneId);
	public Integer addMusicToPlayList(MusicAttach param);
	public List<MusicAttach> findMyMusic(String playlistCode);
	public Integer addLikeMusic(UserLike param);
	public List<UserLike> findLikeMusic(Music param);
	public Integer updateMyPalyList(UserPlayList param);
	public Integer findLikeMusicCount(String openId);
	public Integer delLike(UserLike param);
	public List<Favorite> findUserPalyByCodeAndOpenId(Favorite param);
	public Integer delFavorite(Favorite param);
}
