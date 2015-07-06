package com.kanban.register;

import com.kanban.core.User;
import com.kanban.core.exception.BizException;

/**
 * Created by 碧濤 on 2015/6/22.
 */
public interface Register {
    void signUp(User user) throws BizException;
}
