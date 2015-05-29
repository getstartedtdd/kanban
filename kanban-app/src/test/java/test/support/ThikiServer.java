package test.support;

import org.mortbay.util.ajax.JSON;
import org.springframework.security.config.BeanIds;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by L.x on 15-5-29.
 */
public class ThikiServer implements Endpoint {
    private MockMvc mvc;
    private ResultActions result;
    private Map<String, String> accessToken = new HashMap<String, String>();

    public ThikiServer(WebApplicationContext webApplicationContext) {
        mvc = enableSpringSecurity(MockMvcBuilders.webAppContextSetup(webApplicationContext), webApplicationContext).build();
    }

    private DefaultMockMvcBuilder enableSpringSecurity(DefaultMockMvcBuilder builder, WebApplicationContext webApplicationContext) {
        return builder.addFilter(webApplicationContext.getBean(BeanIds.SPRING_SECURITY_FILTER_CHAIN, Filter.class), "/*");
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

    public void expectAccessTokenExpires() {
        //todo:how to expire an accessToken
    }

    public void assertAnAccessTokenExpiredMessageSent() throws Exception {
        assertFailingOn(OAuth2Exception.INVALID_TOKEN);
    }
    private void assertFailingOn(String error) throws Exception {
        result.andExpect(jsonPath("error").value(error));
    }

    public void login(String username, String password) throws Exception {
        result = mvc.perform(post(String.format("/oauth/token?username=%s&password=%s&grant_type=password&client_id=kanban&client_secret=123456", username, password)));
    }

    @Override
    public void get(String uri) throws Exception {
        result = mvc.perform(withAccessToken(MockMvcRequestBuilders.get(uri)));
    }

    private MockHttpServletRequestBuilder withAccessToken(MockHttpServletRequestBuilder request) {
        return request.param("access_token", accessToken.get("access_token"));
    }


}
