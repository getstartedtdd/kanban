package com.kanban.core;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by L.x on 15-6-2.
 */
@Repository
public interface UserRepository {
    @ResultMap("results.user")
    @Select("select * from users where username=#{username}")
    User findUserByUsername(String username);
}
