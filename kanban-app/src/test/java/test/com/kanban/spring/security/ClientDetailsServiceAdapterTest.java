package test.com.kanban.spring.security;

import com.kanban.core.User;
import com.kanban.core.UserRepository;
import com.kanban.spring.security.ClientDetailsDialect;
import com.kanban.spring.security.ClientDetailsServiceAdapter;
import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;

public class ClientDetailsServiceAdapterTest {
    public static final Object USER_NOT_EXISTS = null;
    public static final String CLIENT_ID = "<username>";
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    @Mock
    private UserRepository userRepository;
    @Mock
    private ClientDetailsDialect clientDetailsDialect;
    private ClientDetailsServiceAdapter clientDetailsService;

    @Before
    public void setUp() throws Exception {
        clientDetailsService = new ClientDetailsServiceAdapter(userRepository, clientDetailsDialect);
    }

    @Test
    public void returnClientDetailsIfUserFound() throws Exception {
        final ClientDetails expected = context.mock(ClientDetails.class);
        final User user = new User();
        expectUserFoundAs(user);

        context.checking(new Expectations() {{
            oneOf(clientDetailsDialect).toClientDetails(user);
            will(returnValue(expected));
        }});

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(CLIENT_ID);

        assertThat(clientDetails, sameInstance(expected));
    }

    @Test
    public void throwsNoSuchClientExceptionIfUserNotFound() throws Exception {
        expectUserFoundAs(USER_NOT_EXISTS);

        context.checking(new Expectations() {{
            never(clientDetailsDialect).toClientDetails(with(any(User.class)));
        }});

        try {
            clientDetailsService.loadClientByClientId(CLIENT_ID);
            fail("should raising exception");
        } catch (NoSuchClientException expected) {
            assertTrue(true);
        }
    }

    private void expectUserFoundAs(final Object user) {
        context.checking(new Expectations() {{
            allowing(userRepository).findUserByClientId(CLIENT_ID);
            will(returnValue(user));
        }});
    }
}