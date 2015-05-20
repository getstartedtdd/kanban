package com.kanban.config;

import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * Created by L.x on 15-5-20.
 */
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {

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
