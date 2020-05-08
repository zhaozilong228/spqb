package spqbs.top.music.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spqbs.top.music.entity.UserEntity;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity, String>  {

}
