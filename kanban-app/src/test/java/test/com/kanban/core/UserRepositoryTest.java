package test.com.kanban.core;

import com.kanban.core.User;
import com.kanban.core.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.com.kanban.EnableSpringTest;

import static com.kanban.matchers.UserMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@EnableSpringTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void returnUserWithRolesIfUserWasFound() throws Exception {
        User user = userRepository.findUserByUsername("admin");
        assertThat(user, username(equalTo("admin")));
        assertThat(user, password(equalTo("admin")));
        assertThat(user, hasRole("ADMIN"));
    }

    @Test
    public void returnNullIfUserWasNotFound() throws Exception {
        assertThat(userRepository.findUserByUsername("unknown"), is(nullValue()));
    }
}