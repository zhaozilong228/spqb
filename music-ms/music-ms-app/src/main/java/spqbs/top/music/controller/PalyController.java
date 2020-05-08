package spqbs.top.music.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spqbs.top.music.common.rest.RestResult;
import spqbs.top.music.model.MusicAttach;
import spqbs.top.music.model.PlayList;
import spqbs.top.music.service.IPlaylistService;

@RestController
@RequestMapping("play")
public class PalyController {
	@Autowired
	private  IPlaylistService playlistServiceImpl;
	
	@RequestMapping("/findPlayPageList")
	public RestResult findPlayList(@RequestBody PlayList param){
		return RestResult.success(playlistServiceImpl.findPlayList(param)) ;
	}
	/**
	 * @Description: 添加歌单
	 * @author zhao zilong
	 * @date:   2020年3月1日 下午3:36:49
	 */
	@RequestMapping("/addPlayList")
	public RestResult addPlayList(@RequestBody PlayList saveParam){
		saveParam.setStatus("102");
		saveParam.setRecommend(102);
		saveParam.setMusicCount(0);
		if("".equals(saveParam.getOrderValue()) && saveParam.getOrderValue() ==null){
			saveParam.setOrderValue(0);
		}
		return RestResult.success(playlistServiceImpl.addPlayList(saveParam));
	}
	
	@RequestMapping("/addPlayListAttach")
	public RestResult addPlayListAttach(@RequestBody List<MusicAttach> SaveparamList){
		return RestResult.success(playlistServiceImpl.addPlayListAttach(SaveparamList));
	}
	
	@RequestMapping("/findMusicByCode/{code}")
	public RestResult findMusicByCode(@PathVariable String code ){
		return RestResult.success(playlistServiceImpl.findMusicByCode(code));
	}
	
	@RequestMapping("/updatePlayList")
	public RestResult updatePlayList(@RequestBody PlayList updParam ){
		return RestResult.success(playlistServiceImpl.updatePlayList(updParam));
	}
	
	@RequestMapping("/statusOn/{code}")
	public RestResult statusOn(@PathVariable String code ){
		return RestResult.success(playlistServiceImpl.statusOn(code));
	}
	
	@RequestMapping("/statusOff/{code}")
	public RestResult statusOff(@PathVariable String code ){
		return RestResult.success(playlistServiceImpl.statusOff(code));
	}
	
	@RequestMapping("/recommendOn/{code}")
	public RestResult recommendOn(@PathVariable String code ){
		return RestResult.success(playlistServiceImpl.recommendOn(code));
	}
	
	@RequestMapping("/recommendOff/{code}")
	public RestResult recommendOff(@PathVariable String code ){
		return RestResult.success(playlistServiceImpl.recommendOff(code));
	}
	
	@RequestMapping("/updateOrderValue")
	public RestResult updateOrderValue(@RequestBody PlayList updParam ){
		return RestResult.success(playlistServiceImpl.updateOrderValue(updParam));
	}
	
	@RequestMapping("/delPlayList/{code}")
	public RestResult delPlayList(@PathVariable String code ){
		return RestResult.success(playlistServiceImpl.delPlayList(code));
	}
	
	@RequestMapping("/delPlayListAttachByCode")
	public RestResult delPlayListAttachByCode(@RequestBody MusicAttach delParam ){
		return RestResult.success(playlistServiceImpl.delPlayListAttachByCode(delParam.getCode(),delParam.getPlaylistCode()));
	}
}
