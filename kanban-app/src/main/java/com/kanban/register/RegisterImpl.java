package com.kanban.register;

import com.kanban.core.User;
import com.kanban.core.exception.BizException;
import com.kanban.core.logger.ThikiLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 碧濤 on 2015/6/22.
 */
@Service("register")
public class RegisterImpl implements Register {
    private static ThikiLogger logger = ThikiLogger.getLogger(RegisterImpl.class);

    @Autowired
    private UsersRepository usersRepository;

    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void signUp(User user) throws BizException {
        logger.info("[用户注册]请求达到服务层，数据：{0}", user);
        User alreadyExistUser = usersRepository.findUserByEmail(user.getEmail());
        if (alreadyExistUser != null) {
            throw new BizException(RegisterCodes.USER_ALREADY_EXIST);
        }
        usersRepository.insert(user);
    }
}
