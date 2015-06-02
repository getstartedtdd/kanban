package test.com.kanban.core;

import com.kanban.core.User;
import com.kanban.core.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.com.kanban.EnableSpringTest;

import static com.kanban.matchers.UserMatchers.*;
import static com.kanban.utils.CollectionUtils.asSet;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@EnableSpringTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUserByUsername() throws Exception {
        assertMatchingUserFromDatabase(userRepository.findUserByUsername("admin"));
    }

    @Test
    public void findUserByClientId() throws Exception {
        assertMatchingUserFromDatabase(userRepository.findUserByClientId("kanban"));
    }

    private void assertMatchingUserFromDatabase(User user) {
        assertThat(user, username(equalTo("admin")));
        assertThat(user, password(equalTo("admin")));
        assertThat(user, clientId(equalTo("kanban")));
        assertThat(user, clientSecret(equalTo("123456")));
        assertThat(user, resourceIds(equalTo(asSet("kanban", "others"))));
        assertThat(user, scopes(equalTo(asSet("read", "write"))));
        assertThat(user, grantedTypes(equalTo(asSet("password", "refresh_token", "client_credentials"))));
        assertThat(user, hasRole("ADMIN"));
    }

    @Test
    public void returnNullIfUserWasNotFound() throws Exception {
        assertThat(userRepository.findUserByUsername("unknown"), is(nullValue()));
        assertThat(userRepository.findUserByClientId("<invalid_client>"), is(nullValue()));
    }
}