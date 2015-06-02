package test.com.kanban;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;
import test.support.ThikiClient;
import test.support.ThikiServer;

/**
 * Created by L.x on 15-5-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@EnableSpringTest
public class RestfulResourceSecurityTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private ThikiServer server;
    private ThikiClient client;

    @Before
    public void setUp() throws Exception {
        server = new ThikiServer(webApplicationContext);
        client = new ThikiClient(server);
    }

    @Test
    public void listAllUsersForTrustClient() throws Exception {
        client.loginAs("kanban", "123456");
        server.assertAccessTokenCreated();

        client.listAllUsers();
        server.assertReturnedAllUsers();
    }

    @Test
    public void reportErrorToClientWithBadCredentials() throws Exception {
        client.loginAs("<bad_client>", "<bad_secret_key>");
        server.assertABadCredentialMessageSent();

        client.listAllUsers();
        server.assertAnUnauthorizedMessageSent();
    }

    @Test
    public void reportErrorToClientWhenAccessTokenWasExpired() throws Exception {
        client.loginAs("kanban", "123456");
        server.assertAccessTokenCreated();

        server.whenAccessTokenExpired();

        client.listAllUsers();
        server.assertAnAccessTokenExpiredMessageSent();
    }
}
