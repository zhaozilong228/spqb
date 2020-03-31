package spqbs.top.music.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import spqbs.top.music.model.Favorite;
import spqbs.top.music.model.MusicAttach;
import spqbs.top.music.model.PlayList;
import spqbs.top.music.model.User;
import spqbs.top.music.model.UserLike;
import spqbs.top.music.model.UserPlayList;

@Mapper
public interface UserMapper {
	
	public User findOpenid(@Param("openId") String openId);
	public List<User> findUserList(@Param("param") User param);
	public Integer saveUser(@Param("param") User param);
	public Integer addFavorite(@Param("param") Favorite param);
	public List<Favorite> findUserFavoriteList(@Param("param") Favorite param);
	public Integer addMyPalyList(@Param("param") UserPlayList param);
	public List<UserPlayList> findMyPalyList(@Param("openId") String openId);
	public Integer addMusicToPlayList(@Param("param")PlayList param);
	public MusicAttach findMyMusic(@Param("playlistCode") String playlistCode);
	public Integer addLikeMusic(@Param("param") UserLike param);
	public UserLike findLikeMusic(@Param("openId") String openId);
	
}
