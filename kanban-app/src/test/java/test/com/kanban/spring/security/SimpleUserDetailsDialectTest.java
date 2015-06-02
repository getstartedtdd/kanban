package test.com.kanban.spring.security;

import com.kanban.core.Role;
import com.kanban.core.User;
import com.kanban.matchers.UserMatchers;
import com.kanban.spring.security.SimpleUserDetailsDialect;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static com.kanban.matchers.UserMatchers.password;
import static com.kanban.matchers.UserMatchers.username;
import static com.kanban.spring.security.SimpleUserDetailsDialect.grant;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class SimpleUserDetailsDialectTest {

    @Test
    public void grantWithRoleToUpperCase() throws Exception {
        assertThat(SimpleUserDetailsDialect.grant("admin"), equalTo(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertThat(SimpleUserDetailsDialect.grant("ADMIN"), equalTo(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    public void toUserDetails() throws Exception {
        SimpleUserDetailsDialect dialect = new SimpleUserDetailsDialect();

        UserDetails userDetails = dialect.toUserDetails(anUser("zhangsan", "123456", withRoles("USER", "ADMIN")));

        assertThat(userDetails, username(equalTo("zhangsan")));
        assertThat(userDetails, password(equalTo("123456")));
        assertThat(userDetails, grantedRoles("USER", "ADMIN"));
    }

    private Matcher<? super UserDetails> grantedRoles(String... roles) {
        return hasProperty("authorities", hasItems(as(roles)));
    }

    private GrantedAuthority[] as(String... roles) {
        ArrayList<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            result.add(grant(role));
        }
        return result.toArray(new GrantedAuthority[result.size()]);
    }

    private User anUser(final String username, final String password, final List<Role> roles) {
        return new User() {{
            setUsername(username);
            setPassword(password);
            setRoles(roles);
        }};
    }

    private List<Role> withRoles(String... roles) {
        ArrayList<Role> result = new ArrayList<Role>();
        for (String role : roles) {
            result.add(new Role(role));
        }
        return result;
    }

}