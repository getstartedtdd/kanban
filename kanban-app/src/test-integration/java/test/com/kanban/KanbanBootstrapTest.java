package test.com.kanban;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.resource.ResourceCollection;
import org.w3c.dom.Element;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by L.x on 15-5-20.
 */
public class KanbanBootstrapTest {
    private Server server = new Server(3000);
    private WebClient client = new WebClient(BrowserVersion.CHROME);

    @Before
    public void startServer() throws Exception {
        server.setStopAtShutdown(true);
        server.setHandler(new WebAppContext() {{
            setContextPath("/kanban");
            setBaseResource(new ResourceCollection(new String[]{
                    "src/main/webapp",
                    "../kanban-resources"
            }));
        }});
        server.start();
    }

    @After
    public void stopServer() throws Exception {
        server.stop();
    }

    @Test
    public void checkingPreparation() throws Exception {
        AlertSpy alert = new AlertSpy();
        client.setAlertHandler(alert);
        HtmlPage home = client.getPage("http://localhost:3000/kanban");
        Element element = home.getFirstByXPath("//p");

        assertThat(element.getTextContent(), containsString("Kanban"));
        alert.assertAlertWithMessage(equalTo("ok"));
    }

    private static class AlertSpy implements AlertHandler {
        private BlockingQueue<String> message = new ArrayBlockingQueue<String>(1);

        public void handleAlert(Page page, String content) {
            message.add(content);
        }

        public void assertAlertWithMessage(Matcher<String> matcher) throws InterruptedException {
            assertThat(message.poll(1, TimeUnit.SECONDS), matcher);
        }
    }
}
