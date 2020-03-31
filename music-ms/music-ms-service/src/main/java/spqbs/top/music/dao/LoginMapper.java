package spqbs.top.music.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import spqbs.top.music.model.User;

@Mapper
public interface LoginMapper {
	
	public User findOpenid(@Param("opneId") String opneId);
	public Integer saveUser(@Param("param") User param);

}
