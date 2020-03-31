package spqbs.top.music.service.impl;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spqbs.top.music.common.util.Page;
import spqbs.top.music.dao.MusicMapper;
import spqbs.top.music.model.Music;
import spqbs.top.music.model.QueryMusicModel;
import spqbs.top.music.service.IMusicService;

@Service(value="musicServiceImpl")
public class MusicServiceImpl  implements IMusicService{
	@Autowired
	private MusicMapper musicMapper;
	public Page<Music> findList(QueryMusicModel param) {
		List<Music> resultList =  musicMapper.findList( param);
		param.setClosePage(true);
		List<Music> resulCount =  musicMapper.findList( param);
		return new Page<Music>(resultList,resulCount.size());
	}
	public String add(Music saveParam) {
		// 暂时用时间戳作为code		
		String code = UUID.randomUUID().toString().replace("-", "");
		saveParam.setCode(code);
		musicMapper.add(saveParam);
		return code;
	}
	public String delete(String code) {
		Integer num = musicMapper.delete(code);
		return num >0 ? "删除成功": "删除失败";
	}
	public Integer update(Music updateParam) {
		return musicMapper.update(updateParam);
	}
	@Override
	public String statusOn(String code) {
		Music updateParam =new Music();
		updateParam.setCode(code);
		updateParam.setStatus("101");
		musicMapper.update(updateParam);
		return null;
	}
	@Override
	public String statusOff(String code) {
		Music updateParam =new Music();
		updateParam.setCode(code);
		updateParam.setStatus("102");
		musicMapper.update(updateParam);
		return null;
	}
	

}
