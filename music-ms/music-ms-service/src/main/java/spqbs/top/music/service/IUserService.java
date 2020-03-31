package spqbs.top.music.service;

import java.util.List;
import spqbs.top.music.model.Favorite;
import spqbs.top.music.model.MusicAttach;
import spqbs.top.music.model.PlayList;
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
	public Integer addMusicToPlayList(PlayList param);
	public MusicAttach findMyMusic(String playlistCode);
	public Integer addLikeMusic(UserLike param);
	public UserLike findLikeMusic(String opneId);
}
