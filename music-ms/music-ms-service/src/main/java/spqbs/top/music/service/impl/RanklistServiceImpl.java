package spqbs.top.music.service.impl;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spqbs.top.music.common.util.Page;
import spqbs.top.music.dao.MusicMapper;
import spqbs.top.music.model.RankAttach;
import spqbs.top.music.model.RankList;
import spqbs.top.music.service.IRanklistService;
@Service(value="ranklistServiceImpl")
public class RanklistServiceImpl implements IRanklistService{
	@Autowired
	private MusicMapper musicMapper;
	@Override
	public String addRankList(RankList param) {
		String code =UUID.randomUUID().toString().replace("-", "");
		param.setCode(code);
		musicMapper.addRankList(param);
		return code;
	}
	@Override
	public Page<RankList> findRankPageList(RankList param) {
		List<RankList> resultList = musicMapper.findRankPageList(param);
		if(resultList.size()>0){
			resultList.forEach((item)->{
				List<RankAttach> previewMusicList = musicMapper.findRankByCodeTop(item.getCode());
				item.setPreviewMusicList(previewMusicList);
			});
		}
		param.setClosePage(true);
		List<RankList> resulCount = musicMapper.findRankPageList(param);
		return new Page<RankList>(resultList,resulCount.size());
	}
	@Override
	public Integer addRankListAttach(List<RankAttach> attachList) {
		if(attachList.size()>0){
			// 用删除的方法覆盖歌单，防止数据重复			
			musicMapper.delRankAttachByCode(attachList.get(0).getRanklistCode());
			attachList.forEach(item -> {
				item.setCode(UUID.randomUUID().toString().replace("-", ""));
			});
		}
		return musicMapper.addRankListAttach(attachList);
	}
	@Override
	public List<RankAttach> findRankByCode(String rankCode) {
		return musicMapper.findRankByCode(rankCode);
	}
	@Override
	public String updateRankList(RankList param) {
		RankList rank = musicMapper.findRankListOne(param.getCode());
		if (rank ==null){
			return "fail";
		}else{
			if(!"".equals(param.getName())){
				rank.setName(param.getName());
			}
			if(!"".equals(param.getDescription())){
				rank.setDescription(param.getDescription());
			}
			if(!"".equals(param.getCoverUrl())){
				rank.setCoverUrl(param.getCoverUrl());
			}
			if(!"".equals(param.getTag())){
				rank.setTag(param.getTag());
			}
			if(!"".equals(param.getPlayNum())){
				rank.setPlayNum(param.getPlayNum());
			}
			if(!"".equals(param.getOrderValue())){
				rank.setOrderValue(param.getOrderValue());
			}
			musicMapper.updateRankList(rank);
		}
		return "success";
	}
	@Override
	public String statusOn(String code) {
		RankList rank = musicMapper.findRankListOne(code);
		if (rank ==null){
			return "fail";
		}else{
			rank.setStatus("101");
			musicMapper.updateRankList(rank);
		}
		return "success";
	}
	@Override
	public String statusOff(String code) {
		RankList rank = musicMapper.findRankListOne(code);
		if (rank ==null){
			return "fail";
		}else{
			rank.setStatus("102");
			musicMapper.updateRankList(rank);
		}
		return "success";
	}
	@Override
	public String recommendOn(String code) {
		RankList rank = musicMapper.findRankListOne(code);
		if (rank ==null){
			return "fail";
		}else{
			rank.setRecommend(101);
			musicMapper.updateRankList(rank);
		}
		return "success";
	}
	@Override
	public String recommendOff(String code) {
		RankList rank = musicMapper.findRankListOne(code);
		if (rank ==null){
			return "fail";
		}else{
			rank.setRecommend(102);
			musicMapper.updateRankList(rank);
		}
		return "success";
	}
	@Override
	public String updateOrderValue(RankList param) {
		RankList rank = musicMapper.findRankListOne(param.getCode());
		if (rank ==null){
			return "fail";
		}else{
			rank.setOrderValue(param.getOrderValue());
			musicMapper.updateRankList(rank);
		}
		return "success";
	}
	@Override
	public Integer delRankList(String code) {
		return musicMapper.delRankList(code);
	}
}
