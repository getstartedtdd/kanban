package test.com.kanban;

import com.kanban.config.WebMvcConfig;
import com.kanban.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
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
        @Autowired
        private ResourcePatternResolver resourceResolver;
        @Autowired
        private DataSource dataSource;


        @PostConstruct
        public void initialize() throws IOException {
            ResourceDatabasePopulator initializer = new ResourceDatabasePopulator(resourceResolver.getResources("classpath*:scripts/*.sql"));
            initializer.execute(dataSource);
        }
    }
}
