package spqbs.top.music.service;

import java.util.List;

import spqbs.top.music.common.util.Page;
import spqbs.top.music.model.Recommend;
import spqbs.top.music.model.RecommendAttach;

public interface IRecommendService {
	public void addRecommendAttach(List<RecommendAttach> attachList);
	public List<RecommendAttach> findPageList(RecommendAttach param);
	public List<Recommend> findRecommendByDate (List<String> dates);
	public void delRecommendById(RecommendAttach param);
	public void updateRecommend(Recommend param);
	public Page<Recommend> findRecommendList (Recommend param);

}
