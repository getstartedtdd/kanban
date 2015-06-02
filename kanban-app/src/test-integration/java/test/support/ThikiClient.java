package test.support;

/**
 * Created by L.x on 15-5-29.
 */
public class ThikiClient {
    private Endpoint endpoint;

    public ThikiClient(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public void loginAs(String clientId, String secretKey) throws Exception {
        endpoint.login(clientId, secretKey);
    }

    public void listAllUsers() throws Exception {
        endpoint.get("/users");
    }
}
