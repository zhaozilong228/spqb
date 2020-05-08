package spqbs.top.music.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spqbs.top.music.common.util.Page;
import spqbs.top.music.dao.RecommendMapper;
import spqbs.top.music.model.Recommend;
import spqbs.top.music.model.RecommendAttach;
import spqbs.top.music.service.IRecommendService;
@Service(value="recommendServiceImpl")
public class RecommendServiceImpl implements IRecommendService{
	
	@Autowired
	private RecommendMapper recommendMapper ;

	@Override
	public void addRecommendAttach(List<RecommendAttach> attachList) {
		if(attachList.size()>0){
			// 用删除的方法覆盖歌单，防止数据重复			
			recommendMapper.delRecommendListAttach(attachList.get(0));
			recommendMapper.addRecommendAttach(attachList);
		}
	}

	@Override
	public List<RecommendAttach> findPageList(RecommendAttach param) {
		return recommendMapper.findPageList(param);
	}

	@Override
	public List<Recommend> findRecommendByDate(List<String> dates) {
		List<Recommend> resultList = new ArrayList<Recommend>();
		dates.forEach(item->{
			Recommend date =new Recommend();
				date.setDate(item);
				Recommend recommend= recommendMapper.findRecommendByDate(date);
				//没有就创建				
				if(recommend == null){
					recommendMapper.addRecommend(date);
					resultList.add(date);
				}else{
					resultList.add(recommend);
				}
		});
		return resultList;
	}

	@Override
	public void delRecommendById(RecommendAttach param) {
		recommendMapper.delRecommendById(param);
	}

	@Override
	public void updateRecommend(Recommend param) {
		recommendMapper.updateRecommend(param);
	}

	@Override
	public Page<Recommend> findRecommendList(Recommend param) {
		if(param.getSize() == null){
			param.setSize(10);
		}
		List<Recommend> resultList = recommendMapper.findRecommendList(param);
		param.setClosePage(true);
		List<Recommend> resulCount = recommendMapper.findRecommendList(param);
		return new Page<Recommend>(resultList,resulCount.size());
	}
 
 
}
