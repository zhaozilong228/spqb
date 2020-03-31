package spqbs.top.music.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spqbs.top.music.common.util.Page;
import spqbs.top.music.dao.MusicMapper;
import spqbs.top.music.model.MusicAttach;
import spqbs.top.music.model.PlayList;
import spqbs.top.music.service.IPlaylistService;

@Service(value="playlistServiceImpl")
public class PalylistServiceImpl implements IPlaylistService{
	@Autowired
	private MusicMapper musicMapper;
	public Page<PlayList> findPlayList(PlayList param) {
		List<PlayList> resultList =  musicMapper.findPlayList(param);
		param.setClosePage(true);
		List<PlayList> resulCount = musicMapper.findPlayList(param);
		return  new Page<PlayList>(resultList,resulCount.size());
	}
	public String addPlayList(PlayList saveParam) {
		// 暂时用时间戳作为code		
		String code = UUID.randomUUID().toString().replace("-", "");
		saveParam.setCode(code);
		musicMapper.addPlayList(saveParam);
		return code;
	}
	public List<MusicAttach> findMusicByCode(String code) {
		return musicMapper.findMusicByCode(code);
	}
	public Integer addPlayListAttach(List<MusicAttach> SaveparamList) {
		if(SaveparamList.size()>0){
			// 用删除的方法覆盖歌单，防止数据重复			
			musicMapper.delPlayListAttach(SaveparamList.get(0).getPlaylistCode());
			SaveparamList.forEach(item -> {
				item.setCode(UUID.randomUUID().toString().replace("-", ""));
			});
			PlayList play =new PlayList();
			play.setCode(SaveparamList.get(0).getPlaylistCode());
			play.setMusicCount(SaveparamList.size());
			updatePlayList(play);
		}
		musicMapper.addPlayListAttach(SaveparamList);
		return SaveparamList.size();
	}
	@Override
	public String updatePlayList(PlayList param) {
		PlayList play = musicMapper.findPlayListOne(param.getCode());
		if (play ==null){
			return "fail";
		}else{
			if(!"".equals(param.getName())){
				play.setName(param.getName());
			}
			if(!"".equals(param.getDescription())){
				play.setDescription(param.getDescription());
			}
			if(!"".equals(param.getCoverUrl())){
				play.setCoverUrl(param.getCoverUrl());
			}
			if(!"".equals(param.getTag())){
				play.setTag(param.getTag());
			}
			if(!"".equals(param.getPlayNum())){
				play.setPlayNum(param.getPlayNum());
			}
			if(!"".equals(param.getOrderValue())){
				play.setOrderValue(param.getOrderValue());
			}
			if(!"".equals(param.getMusicCount())){
				play.setMusicCount(param.getMusicCount());
			}
			musicMapper.updatePlayList(play);
		}
		return "success";
	}
	@Override
	public String statusOn(String code) {
		PlayList play = musicMapper.findPlayListOne(code);
		if (play ==null){
			return "fail";
		}else{
			play.setStatus("101");
			musicMapper.updatePlayList(play);
		}
		return "success";
	}
	@Override
	public String statusOff(String code) {
		PlayList play = musicMapper.findPlayListOne(code);
		if (play ==null){
			return "fail";
		}else{
			play.setStatus("102");
			musicMapper.updatePlayList(play);
		}
		return "success";
	}
	@Override
	public String recommendOn(String code) {
		PlayList play = musicMapper.findPlayListOne(code);
		if (play ==null){
			return "fail";
		}else{
			play.setRecommend(101);
			musicMapper.updatePlayList(play);
		}
		return "success";
	}
	@Override
	public String recommendOff(String code) {
		PlayList play = musicMapper.findPlayListOne(code);
		if (play ==null){
			return "fail";
		}else{
			play.setRecommend(102);
			musicMapper.updatePlayList(play);
		}
		return "success";
	}
	@Override
	public String updateOrderValue(PlayList param) {
		PlayList play = musicMapper.findPlayListOne(param.getCode());
		if (play ==null){
			return "fail";
		}else{
			play.setOrderValue(param.getOrderValue());
			musicMapper.updatePlayList(play);
		}
		return "success";
	}
	@Override
	public Integer delPlayList(String code) {
		return musicMapper.delPlayList(code);
	}
}
