package test.com.kanban;

import com.kanban.config.WebMvcConfig;
import com.kanban.config.ApplicationConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by L.x on 15-5-29.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ContextConfiguration(classes = EnableSpringTest.Config.class)
@WebAppConfiguration
public @interface EnableSpringTest {
    @Configuration
    @Import({WebMvcConfig.class, ApplicationConfig.class})
    public static class Config {

    }
}
