package cn.thiki.domain.user.dao;

import org.springframework.stereotype.Component;

import cn.thiki.core.annotation.MyBatisRepository;
import cn.thiki.domain.user.model.User;

@Component
@MyBatisRepository
public interface UserDao {

    User queryByuserName(String userName);

    Integer insert(User user);
}