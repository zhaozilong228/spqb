package spqbs.top.music.service;


import spqbs.top.music.common.util.Page;
import spqbs.top.music.model.Music;
import spqbs.top.music.model.QueryMusicModel;

public interface IMusicService {
	public Page<Music> findList(QueryMusicModel param);
	public String add (Music param);
	public String delete (String code);
	public Integer update (Music param);
    public String statusOn(String code);
    public String statusOff(String code);
    public Music findMusicOne(Music param);
}
