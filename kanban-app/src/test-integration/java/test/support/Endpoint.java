package test.support;

/**
 * Created by L.x on 15-5-29.
 */
public interface Endpoint {
    void get(String uri) throws Exception;

    void login(String username, String password) throws Exception;
}
