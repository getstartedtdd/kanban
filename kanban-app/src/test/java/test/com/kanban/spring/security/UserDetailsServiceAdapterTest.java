package test.com.kanban.spring.security;

import com.kanban.core.User;
import com.kanban.core.UserRepository;
import com.kanban.spring.security.UserDetailsDialect;
import com.kanban.spring.security.UserDetailsServiceAdapter;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;

public class UserDetailsServiceAdapterTest {
    public static final Object USER_NOT_EXISTS = null;
    public static final String USERNAME = "<username>";
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserDetailsDialect userDetailsDialect;
    private UserDetailsServiceAdapter userDetailsService;

    @Before
    public void setUp() throws Exception {
        userDetailsService = new UserDetailsServiceAdapter(userRepository, userDetailsDialect);
    }

    @Test
    public void returnUserDetailsIfUserFound() throws Exception {
        final UserDetails expected = context.mock(UserDetails.class);
        final User user = new User();
        expectUserFoundAs(user);

        context.checking(new Expectations() {{
            oneOf(userDetailsDialect).toUserDetails(user);
            will(returnValue(expected));
        }});

        UserDetails userDetails = userDetailsService.loadUserByUsername(USERNAME);

        assertThat(userDetails, sameInstance(expected));
    }

    @Test
    public void throwsUsernameNotFoundExceptionIfUserNotFound() throws Exception {
        expectUserFoundAs(USER_NOT_EXISTS);

        context.checking(new Expectations() {{
            never(userDetailsDialect).toUserDetails(with(any(User.class)));
        }});

        try {
            userDetailsService.loadUserByUsername(USERNAME);
            fail("should raising exception");
        } catch (UsernameNotFoundException expected) {
            assertTrue(true);
        }
    }

    private void expectUserFoundAs(final Object user) {
        context.checking(new Expectations() {{
            allowing(userRepository).findUserByUsername(USERNAME);
            will(returnValue(user));
        }});
    }
}