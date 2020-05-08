package spqbs.top.music.service;

import java.util.List;
import spqbs.top.music.common.util.Page;
import spqbs.top.music.model.MusicAttach;
import spqbs.top.music.model.PlayList;

public interface IPlaylistService {
	public  Page<PlayList> findPlayList(PlayList param);
	public String addPlayList (PlayList param);
	public List<MusicAttach> findMusicByCode(String code);
    public Integer addPlayListAttach(List<MusicAttach> SaveparamList );
    public String updatePlayList (PlayList param);
    public String statusOn(String code);
    public String statusOff(String code);
    public String recommendOn(String code );
    public String recommendOff(String code);
    public String updateOrderValue(PlayList param);
    public Integer delPlayList(String code );
    public Integer delPlayListAttachByCode(String code,String palyCode);
}
