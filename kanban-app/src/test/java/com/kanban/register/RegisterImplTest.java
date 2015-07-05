package com.kanban.register;

import com.kanban.core.User;
import com.kanban.core.exception.BizException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by 碧濤 on 2015/7/5.
 */
public class RegisterImplTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void signUpUserSuccessfully() throws BizException {
        User user = new User();

        UsersRepository usersRepository = mock(UsersRepository.class);
        doNothing().when(usersRepository).insert(user);

        RegisterImpl register = new RegisterImpl();
        register.setUsersRepository(usersRepository);
        register.signUp(user);

        verify(usersRepository).insert(user);
    }

    @Test
    public void signUpButTheEmailHasAlreadyExits() throws BizException {
        exception.expectMessage(RegisterCodes.USER_ALREADY_EXIST);
        exception.expect(BizException.class);

        User user = new User();
        user.setEmail("foo");
        UsersRepository usersRepository = mock(UsersRepository.class);
        when(usersRepository.findUserByEmail("foo")).thenReturn(new User());

        RegisterImpl register = new RegisterImpl();
        register.setUsersRepository(usersRepository);
        register.signUp(user);

        verify(usersRepository).findUserByEmail("foo");
    }
}