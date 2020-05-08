package spqbs.top.music.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import spqbs.top.music.model.Music;
import spqbs.top.music.model.MusicAttach;
import spqbs.top.music.model.PlayList;
import spqbs.top.music.model.QueryMusicModel;
import spqbs.top.music.model.RankAttach;
import spqbs.top.music.model.RankList;

@Mapper
public interface  MusicMapper {
	/**
	 * 
	 * @Description:“Ù¿÷«˙ø‚
	 * @author zhao zilong
	 */
    public List<Music> findList(@Param("param") QueryMusicModel param );
    public Integer add(@Param("param") Music param );
    public Integer delete(@Param("code") String code );
    public Integer update(@Param("param") Music updateParam );
    public Music findMusicOne(@Param("param") Music param );
    
    /**
     * 
     * @Description: ∏Ëµ•œ‡πÿ
     * @author zhao zilong
     */
    public List<PlayList> findPlayList(@Param("param") PlayList param );
    public Integer addPlayList(@Param("param") PlayList param );
    public List<MusicAttach> findMusicByCode(@Param("code") String code );
    public Integer addPlayListAttach(@Param("attachList") List<MusicAttach> param );
    public Integer delPlayListAttach(@Param("playlistCode") String playlistCode);
    public Integer updatePlayList(@Param("param") PlayList param );
    public PlayList findPlayListOne(@Param("playlistCode") String playlistCode );
    public Integer delPlayList(@Param("code") String code );
    public Integer delPlayListAttachByCode(@Param("code") String code);
    public Integer playCountSub(@Param("code") String code );
    /**
     * @Description: TODO(√Ë ˆ)
     * @author zhao zilong
     */
    public Integer addRankList(@Param("param") RankList param );
    public List<RankList> findRankPageList(@Param("param") RankList param );
    public Integer addRankListAttach(@Param("attachList") List<RankAttach> attachList );
    public List<RankAttach> findRankByCode(@Param("rankCode") String rankCode );
    public Integer delRankAttachByCode(@Param("rankCode") String rankCode);
    public Integer updateRankList(@Param("param") RankList param );
    public RankList findRankListOne(@Param("code") String code );
    public Integer delRankList(@Param("code") String code );
    public List<RankAttach> findRankByCodeTop(@Param("rankCode") String rankCode );
    public Integer delRankMusichByCode(@Param("code") String code);
    public Integer rankCountSub(@Param("code") String code);
    
}
