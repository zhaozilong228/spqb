package spqbs.top.music.service;

import java.util.List;

import spqbs.top.music.common.util.Page;
import spqbs.top.music.model.RankAttach;
import spqbs.top.music.model.RankList;

public interface IRanklistService {
	  
    public String addRankList(RankList param );
    public Page<RankList> findRankPageList(RankList param );
    public Integer addRankListAttach(List<RankAttach> attachList );
    public List<RankAttach> findRankByCode(String rankCode );
    public String updateRankList(RankList param );
    public String statusOn(String code);
    public String statusOff(String code);
    public String recommendOn(String code );
    public String recommendOff(String code);
    public String updateOrderValue(RankList param );
    public Integer delRankList( String code );
}
