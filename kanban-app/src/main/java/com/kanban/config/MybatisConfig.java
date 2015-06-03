package com.kanban.config;

import com.kanban.core.UserRepository;
import com.kanban.utils.SetTypeHandler;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;

import static org.springframework.util.ClassUtils.getPackageName;

/**
 * Created by L.x on 15-6-2.
 */
@Configuration
@Import({MybatisConfig.DataSourceConfig.class, MybatisConfig.MapperScannerConfig.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MybatisConfig {


    @PropertySource("classpath:jdbc.properties")
    public static class DataSourceConfig {
        @Value("${jdbc.user}")
        private String user;
        @Value("${jdbc.password}")
        private String password;
        @Value("${jdbc.url}")
        private String url;

        @Bean
        public DataSource dataSource() {
            MysqlDataSource mysql = new MysqlDataSource();
            mysql.setUser(user);
            mysql.setPassword(password);
            mysql.setURL(url);
            return mysql;
        }
    }


    @Bean
    @Lazy
    public SqlSessionFactoryBean sessionFactory(final DataSource dataSource,final ResourcePatternResolver resourceResolver) throws IOException {
        return new SqlSessionFactoryBean() {{
            setDataSource(dataSource);
            setMapperLocations(resourceResolver.getResources("classpath:com/kanban/core/**.mybatis.xml"));
            setTypeHandlers(new TypeHandler[]{new SetTypeHandler()});
        }};
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    public static class MapperScannerConfig {
        @Bean
        public MapperScannerConfigurer mapperScannerConfigurer() {
            return new MapperScannerConfigurer() {{
                setSqlSessionFactoryBeanName("sessionFactory");
                setAnnotationClass(Repository.class);
                setBasePackage(getPackageName(UserRepository.class));
            }};
        }
    }


}
