package test.support;

/**
 * Created by L.x on 15-5-29.
 */
public class ThikiClient {
    private Endpoint endpoint;

    public ThikiClient(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public void loginAs(final String username, final String password) throws Exception {
        endpoint.login(username, password);
    }

    public void listAllUsers() throws Exception {
        endpoint.get("/users");
    }
}
