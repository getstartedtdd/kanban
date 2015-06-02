package com.kanban.matchers;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;

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
}
