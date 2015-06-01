package test.support;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.mortbay.util.ajax.JSON;
import org.springframework.security.config.BeanIds;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by L.x on 15-5-29.
 */
public class ThikiServer implements Endpoint {
    private MockMvc mvc;
    private ResultActions result;
    private Map<String, String> accessToken = new HashMap<String, String>();
    private TokenStore tokenStore;

    public ThikiServer(WebApplicationContext webApplicationContext) {
        mvc = enableSpringSecurity(MockMvcBuilders.webAppContextSetup(webApplicationContext), webApplicationContext).build();
        tokenStore = webApplicationContext.getBean(TokenStore.class);
    }

    private DefaultMockMvcBuilder enableSpringSecurity(DefaultMockMvcBuilder builder, WebApplicationContext webApplicationContext) {
        return builder.addFilter(new DelegatingFilterProxy(BeanIds.SPRING_SECURITY_FILTER_CHAIN, webApplicationContext), "/*");
    }


    public void assertAccessTokenCreated() throws Exception {
        result.andExpect(jsonPath("access_token").exists()).andDo(new ResultHandler() {
            @Override
            public void handle(MvcResult result) throws Exception {
                String content = result.getResponse().getContentAsString();
                accessToken.putAll((Map<String, String>) JSON.parse(content));
            }
        });
    }

    public void assertReturnedAllUsers() throws Exception {
        result.andExpect(jsonPath("$").isArray());
    }

    public void assertAnUnauthorizedMessageSent() throws Exception {
        assertFailingOn("unauthorized");
    }

    public void assertABadCredentialMessageSent() throws Exception {
        assertFailingOn(OAuth2Exception.INVALID_GRANT);
    }

    public void whenAccessTokenExpired() {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(accessToken());
        tokenStore.storeAccessToken(expires(accessToken), tokenStore.readAuthentication(accessToken));
    }

    private String accessToken() {
        return accessToken.get("access_token");
    }

    private DefaultOAuth2AccessToken expires(OAuth2AccessToken accessToken) {
        DefaultOAuth2AccessToken expired = new DefaultOAuth2AccessToken(accessToken);
        expired.setExpiration(new Date(System.currentTimeMillis() - 1000));
        return expired;
    }

    public void assertAnAccessTokenExpiredMessageSent() throws Exception {
        assertFailingOn(OAuth2Exception.INVALID_TOKEN,containsString("Access token expired"));
    }

    private void assertFailingOn(String error, Matcher<String>... descriptionMatchers) throws Exception {
        result.andExpect(jsonPath("error").value(error)).andExpect(jsonPath("error_description").value(allOf(descriptionMatchers)));
    }

    public void login(String username, String password) throws Exception {
        result = mvc.perform(post(String.format("/oauth/token?username=%s&password=%s&grant_type=password&client_id=kanban&client_secret=123456", username, password)));
    }

    @Override
    public void get(String uri) throws Exception {
        result = mvc.perform(withAccessToken(MockMvcRequestBuilders.get(uri)));
    }

    private MockHttpServletRequestBuilder withAccessToken(MockHttpServletRequestBuilder request) {
        return request.param("access_token", accessToken());
    }


}
