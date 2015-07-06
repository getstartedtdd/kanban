package com.kanban.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kanban.core.User;
import com.kanban.register.Register;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by 碧濤 on 2015/7/4.
 */
public class RegisterControllerTest {
    @Test
    public void signUp() throws Exception {
        User user = new User();
        JSONObject expectedMessage = new JSONObject() {
            {
                put("message", "注册成功。");
            }
        };
        Register register = mock(Register.class);
        RegisterController registerController = new RegisterController();
        registerController.setRegister(register);
        JSONObject actualMessage = registerController.signUp(user);

        verify(register).signUp(user);
        assertEquals(expectedMessage, actualMessage);
    }
}