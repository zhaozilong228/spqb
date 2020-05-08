package spqbs.top.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spqbs.top.music.common.rest.RestResult;
import spqbs.top.music.model.Music;
import spqbs.top.music.model.QueryMusicModel;
import spqbs.top.music.service.IMusicService;
@RestController
@RequestMapping("music")
public class MusicController {
	@Autowired
	private  IMusicService musicServiceImpl;
	
	@RequestMapping("/findPageList")
	public RestResult helloworld(@RequestBody QueryMusicModel param){
		return RestResult.success(musicServiceImpl.findList(param)) ;
	}
	/**
	 * @Description: 添加音乐
	 * @author zhao zilong
	 * @date:   2020年3月1日 下午3:36:49
	 */
	@RequestMapping("/add")
	public RestResult add(@RequestBody Music saveParam){
		return RestResult.success(musicServiceImpl.add(saveParam));
	}
	@RequestMapping("/update")
	public RestResult update(@RequestBody Music updateParam){
		return RestResult.success(musicServiceImpl.update(updateParam));
	}
	@RequestMapping("/del/{code}")
	public RestResult delete(@PathVariable String code){
		return RestResult.success(musicServiceImpl.delete(code));
	}

	@RequestMapping("/statusOn/{code}")
	public RestResult statusOn(@PathVariable String code){
		return RestResult.success(musicServiceImpl.statusOn(code));
	}
	@RequestMapping("/statusOff/{code}")
	public RestResult statusOff(@PathVariable String code){
		return RestResult.success(musicServiceImpl.statusOff(code));
	}
	
	@RequestMapping("/findMusicOne")
	public RestResult findMusicOne(@RequestBody Music param){
		return RestResult.success(musicServiceImpl.findMusicOne(param));
	}
}
