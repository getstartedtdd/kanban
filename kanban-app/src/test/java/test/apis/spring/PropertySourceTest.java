package test.apis.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

/**
 * Created by L.x on 15-6-2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PropertySourceTest {
    @Autowired
    private Config config;

    @Configuration
    @PropertySource("classpath:test.properties")
    public static class Config {
        @Value("${foo}")
        public String foo;
        @Value("#{systemProperties['java.class.version']}")
        public float classVersion;
        @Value("${unknown:default}")
        public String defaultValueOnMissing;

        @Bean
        public static PlaceholderConfigurerSupport placeholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
        }
    }

    @Test
    public void propertiesToBeAutowired() throws Exception {
        assertThat("property from file", config.foo, equalTo("bar"));
        assertThat("property from system properties", config.classVersion, greaterThan(50f));
        assertThat(config.defaultValueOnMissing, equalTo("default"));
    }
}
