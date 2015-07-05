package com.kanban.controller;

import com.alibaba.fastjson.JSONObject;
import com.kanban.core.User;
import com.kanban.core.logger.ThikiLogger;
import com.kanban.register.Register;
import com.kanban.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 碧濤 on 15-5-29.
 */
@RestController
public class RegisterController {
    private static ThikiLogger logger = ThikiLogger.getLogger(RegisterController.class);
    @Autowired
    private Register register;

    public void setRegister(Register register) {
        this.register = register;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JSONObject signUp(@RequestBody User user) throws Exception {
        logger.info("[用户注册]请求达到控制层，数据：{0}", user);
        register.signUp(user);
        logger.info("[用户注册]控制层响应结束,注册成功。");
        return JSONResult.buildMessage("注册成功。");
    }
}
