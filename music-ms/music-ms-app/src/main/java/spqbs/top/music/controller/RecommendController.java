package spqbs.top.music.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spqbs.top.music.common.rest.RestResult;
import spqbs.top.music.model.Recommend;
import spqbs.top.music.model.RecommendAttach;
import spqbs.top.music.service.IRecommendService;

@RestController
@RequestMapping("recommend")
public class RecommendController {
	@Autowired
	private  IRecommendService recommendServiceImpl;
	
	@RequestMapping("/findRecommendAttachList")
	public RestResult findRecommendList(@RequestBody RecommendAttach param){
		return RestResult.success(recommendServiceImpl.findPageList(param));
	}
	
	@RequestMapping("/addRecommendtAttach")
	public RestResult findRecommendList(@RequestBody List<RecommendAttach> attachList){
		recommendServiceImpl.addRecommendAttach(attachList);
		return RestResult.success();
	}

	@RequestMapping("/delRecommendById")
	public RestResult delRecommendById(@RequestBody RecommendAttach param){
		recommendServiceImpl.delRecommendById(param);;
		return RestResult.success();
	}
	
	@RequestMapping("/findRecommendByDate")
	public RestResult findRecommendByDate(@RequestBody List<String> dates){
		return RestResult.success(recommendServiceImpl.findRecommendByDate(dates));
	}
	
	@RequestMapping("/updateRecommend")
	public RestResult updateRecommend(@RequestBody Recommend param){
		recommendServiceImpl.updateRecommend(param);
		return RestResult.success();
	}
	
	@RequestMapping("/findRecommendList")
	public RestResult findRecommendList(@RequestBody Recommend param){
		return RestResult.success(recommendServiceImpl.findRecommendList(param));
	}
}
