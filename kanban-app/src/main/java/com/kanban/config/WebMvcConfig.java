package com.kanban.config;

import com.kanban.controller.UserController;
import com.kanban.core.exception.ThikiExceptionResolver;
import com.kanban.interceptor.ThiKiInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.List;

/**
 * Created by L.x on 15-5-20.
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.kanban")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    private boolean cache = false;

    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(new ThymeleafViewResolver() {{
            setContentType("application/json;charset=UTF-8");
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
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ThiKiInterceptor());
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new ThikiExceptionResolver());
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }
}
