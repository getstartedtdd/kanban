package cn.thiki.dao;

import org.springframework.stereotype.Component;

import cn.thiki.annotation.MyBatisRepository;
import cn.thiki.dmo.User;

@Component
@MyBatisRepository
public interface UserDao {

	User queryByuserName(String userName);
	
	Integer insert(User user);
}
