package spqbs.top;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spqbs.top.music.common.rest.RestResult;
import spqbs.top.music.model.RankAttach;
import spqbs.top.music.model.RankList;
import spqbs.top.music.service.IRanklistService;

@RestController
@RequestMapping("rank")
public class RankController {
	@Autowired
	private  IRanklistService ranklistServiceImpl;
	
	@RequestMapping("/addRankList")
	public RestResult addRankList(@RequestBody RankList saveParam  ){
		saveParam.setStatus("102");
		saveParam.setRecommend(101);
		if(saveParam.getOrderValue() ==null){
			saveParam.setOrderValue(0);
		}
		return RestResult.success(ranklistServiceImpl.addRankList(saveParam));
	}
	
	@RequestMapping("/findRankPageList")
	public RestResult findRankPageList(@RequestBody RankList queryParam  ){
		return RestResult.success(ranklistServiceImpl.findRankPageList(queryParam));
	}
	
	@RequestMapping("/addRankListAttach")
	public RestResult addRankListAttach(@RequestBody List<RankAttach> attachList ){
		return RestResult.success(ranklistServiceImpl.addRankListAttach(attachList));
	}
	
	@RequestMapping("/findRankByCode/{code}")
	public RestResult findRankByCode(@PathVariable String code ){
		return RestResult.success(ranklistServiceImpl.findRankByCode(code));
	}
	
	@RequestMapping("/updateRankList")
	public RestResult updateRankList(@RequestBody RankList updParam  ){
		return RestResult.success(ranklistServiceImpl.updateRankList(updParam));
	}
	
	@RequestMapping("/statusOn/{code}")
	public RestResult statusOn(@PathVariable String code ){
		return RestResult.success(ranklistServiceImpl.statusOn(code));
	}
	
	@RequestMapping("/statusOff/{code}")
	public RestResult statusOff(@PathVariable String code ){
		return RestResult.success(ranklistServiceImpl.statusOff(code));
	}
	
	@RequestMapping("/recommendOn/{code}")
	public RestResult recommendOn(@PathVariable String code ){
		return RestResult.success(ranklistServiceImpl.recommendOn(code));
	}
	
	@RequestMapping("/recommendOff/{code}")
	public RestResult recommendOff(@PathVariable String code ){
		return RestResult.success(ranklistServiceImpl.recommendOff(code));
	}
	
	@RequestMapping("/updateOrderValue")
	public RestResult updateOrderValue(@RequestBody RankList updParam ){
		return RestResult.success(ranklistServiceImpl.updateOrderValue(updParam));
	}
	
	@RequestMapping("/delRankList/{code}")
	public RestResult delRankList(@PathVariable String code ){
		return RestResult.success(ranklistServiceImpl.delRankList(code));
	}
}
