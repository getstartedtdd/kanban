package com.kanban.register;

import com.kanban.core.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by 碧濤 on 2015/6/22.
 */
@Repository
public interface UsersRepository {
    @Insert("insert into users(email,password) values(#{email},#{password})")
    void insert(User user);

    @ResultMap("register.findUserByEmail")
    @Select("select * from users where email=#{email}")
    User findUserByEmail(String email);
}
