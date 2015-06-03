package com.kanban.config;

import com.kanban.controller.UserController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * Created by L.x on 15-5-20.
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = UserController.class)
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    private boolean cache = false;

    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(new ThymeleafViewResolver() {{
            setContentType("text/html;charset=UTF-8");
            setCache(cache);
            setTemplateEngine(new SpringTemplateEngine() {{
                setTemplateResolver(new ServletContextTemplateResolver() {{
                    setTemplateMode(StandardTemplateModeHandlers.HTML5.getTemplateModeName());
                    setPrefix("/views/");
                    setSuffix(".html");
                    setCacheable(cache);
                }});
            }});
        }});
    }


    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


}
