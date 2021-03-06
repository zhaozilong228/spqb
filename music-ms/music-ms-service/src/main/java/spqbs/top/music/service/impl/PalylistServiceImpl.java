package spqbs.top.music.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spqbs.top.music.common.util.Page;
import spqbs.top.music.dao.MusicMapper;
import spqbs.top.music.dao.UserMapper;
import spqbs.top.music.model.Favorite;
import spqbs.top.music.model.MusicAttach;
import spqbs.top.music.model.PlayList;
import spqbs.top.music.service.IPlaylistService;

@Service(value="playlistServiceImpl")
public class PalylistServiceImpl implements IPlaylistService{
	@Autowired
	private MusicMapper musicMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	public Page<PlayList> findPlayList(PlayList param) {
		List<PlayList> resultList =  musicMapper.findPlayList(param);
		param.setClosePage(true);
		List<PlayList> resulCount = musicMapper.findPlayList(param);
		if(resultList.size()>0){
			resultList.forEach((item)->{
				Favorite favorite =new Favorite();
				favorite.setPlaylistCode(item.getCode());
				favorite.setOpenId(param.getOpenId());
				List<Favorite> result = userMapper.findUserPalyByCodeAndOpenId(favorite);
				item.setFavoriteFlag(result.size()>0);
			});
		}
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
		musicMapper.updatePlayNum(code);
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
			
			if(StringUtils.isNotBlank(param.getName())){
				play.setName(param.getName());
			}
			if(StringUtils.isNotBlank(param.getDescription())){
				play.setDescription(param.getDescription());
			}
			if(StringUtils.isNotBlank(param.getCoverUrl())){
				play.setCoverUrl(param.getCoverUrl());
			}
			if(StringUtils.isNotBlank(param.getTag())){
				play.setTag(param.getTag());
			}
			if(!"".equals(param.getPlayNum())&& param.getPlayNum() !=null){
				play.setPlayNum(param.getPlayNum());
			}
			if(!"".equals(param.getOrderValue())&& param.getOrderValue()!=null){
				play.setOrderValue(param.getOrderValue());
			}
			if(!"".equals(param.getMusicCount())&& param.getMusicCount()!=null){
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
	@Override
	public Integer delPlayListAttachByCode(String code,String palyCode) {
			musicMapper.playCountSub(code);
		return musicMapper.delPlayListAttachByCode(code);
	}
}
