package test.com.kanban.spring.security;

import com.kanban.core.Role;
import com.kanban.core.User;
import com.kanban.spring.security.UserDialects;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.kanban.matchers.UserMatchers.*;
import static com.kanban.spring.security.UserDialects.grant;
import static com.kanban.utils.CollectionUtils.asSet;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class UserDialectsTest {

    private UserDialects dialects;
    private  User anUser;

    @Before
    public void setUp() throws Exception {
        dialects = new UserDialects();
        anUser = new User() {{
            setClientId("a_client");
            setClientSecret("123456");
            setResourceIds(asSet("kanban", "others"));
            setScopes(asSet("read", "write"));
            setGrantedTypes(asSet("password", "refresh_token"));
            setUsername("zhangsan");
            setPassword("123456");
            setRoles(withRoles("USER", "ADMIN"));
        }};

    }

    @Test
    public void grantWithRoleToUpperCase() throws Exception {
        assertThat(UserDialects.grant("admin"), equalTo(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertThat(UserDialects.grant("ADMIN"), equalTo(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    public void toUserDetails() throws Exception {
        UserDetails userDetails = dialects.toUserDetails(anUser);

        assertThat(userDetails, username(equalTo("zhangsan")));
        assertThat(userDetails, password(equalTo("123456")));
        assertThat(userDetails, grantedRoles("USER", "ADMIN"));
    }

    @Test
    public void toClientDetails() throws Exception {
        ClientDetails userDetails = dialects.toClientDetails(anUser);

        assertThat(userDetails, clientId(equalTo("a_client")));
        assertThat(userDetails, clientSecret(equalTo("123456")));
        assertThat(userDetails, resourceIds(equalTo(asSet("kanban", "others"))));
        assertThat(userDetails, scope(equalTo(asSet("read", "write"))));
        assertThat(userDetails, grantedTypes(equalTo(asSet("password", "refresh_token"))));
        assertThat(userDetails, grantedRoles("USER", "ADMIN"));
    }

    private Matcher<Object> grantedTypes(Matcher<Set<String>> matcher) {
        return hasProperty("authorizedGrantTypes", matcher);
    }

    private Matcher<Object> scope(Matcher<Set<String>> matcher) {
        return hasProperty("scope", matcher);
    }

    private Matcher<Object> grantedRoles(String... roles) {
        return hasProperty("authorities", hasItems(as(roles)));
    }

    private GrantedAuthority[] as(String... roles) {
        ArrayList<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            result.add(grant(role));
        }
        return result.toArray(new GrantedAuthority[result.size()]);
    }


    private List<Role> withRoles(String... roles) {
        ArrayList<Role> result = new ArrayList<Role>();
        for (String role : roles) {
            result.add(new Role(role));
        }
        return result;
    }

}