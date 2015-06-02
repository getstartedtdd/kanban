package com.kanban.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.IOException;

/**
 * Created by L.x on 15-5-29.
 */

@Import({MybatisConfig.class, WebSecurityConfig.class})
public class ApplicationConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() throws IOException {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
