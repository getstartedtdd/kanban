package com.kanban.matchers;

import org.hamcrest.Matcher;

import java.util.Set;

import static org.hamcrest.Matchers.*;

/**
 * Created by L.x on 15-6-2.
 */
public class UserMatchers {
    public static Matcher<Object> password(Matcher<String> matcher) {
        return hasProperty("password", matcher);
    }

    public static Matcher<Object> username(Matcher<String> matcher) {
        return hasProperty("username", matcher);
    }

    public static Matcher<Object> hasRole(String roleName) {
        return hasProperty("roles", hasItem(hasProperty("name", equalTo(roleName))));
    }

    public static Matcher<Object> resourceIds(Matcher<Set<String>> matcher) {
        return hasProperty("resourceIds", matcher);
    }

    public static Matcher<Object> clientSecret(Matcher<String> matcher) {
        return hasProperty("clientSecret", matcher);
    }

    public static Matcher<Object> clientId(Matcher<String> matcher) {
        return hasProperty("clientId", matcher);
    }

    public static Matcher<Object> grantedTypes(Matcher<Set<String>> matcher) {
        return hasProperty("grantedTypes", matcher);
    }

    public static Matcher<Object> scopes(Matcher<Set<String>> matcher) {
        return hasProperty("scopes", matcher);
    }
}
