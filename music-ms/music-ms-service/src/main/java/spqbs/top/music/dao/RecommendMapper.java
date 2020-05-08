package spqbs.top.music.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spqbs.top.music.model.Recommend;
import spqbs.top.music.model.RecommendAttach;

@Mapper
public interface RecommendMapper {
	 
	public void addRecommendAttach(@Param("attachList") List<RecommendAttach> attachList);
	public List<RecommendAttach> findPageList(@Param("param")RecommendAttach param);
	public void delRecommendListAttach(@Param("param")RecommendAttach param);
	public void delRecommendById(@Param("param")RecommendAttach param);
	public Recommend findRecommendByDate(@Param("param")Recommend param);
	public void addRecommend(@Param("param")Recommend param);
	public void updateRecommend(@Param("param")Recommend param);
	public List<Recommend> findRecommendList(@Param("param")Recommend param);
	
	
	
}
