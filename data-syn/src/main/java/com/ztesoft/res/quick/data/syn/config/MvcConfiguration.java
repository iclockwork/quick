package com.ztesoft.res.quick.data.syn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.velocity.VelocityConfig;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

/**
 * MvcConfiguration
 *
 * @author: fengwang
 * @date: 2018-7-24 13:24
 * @version: 1.0
 * @since: JDK 1.7
 */
@Configuration
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private final ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Bean
    public VelocityConfig velocityConfig() {
        VelocityConfigurer cfg = new VelocityConfigurer();
        cfg.setResourceLoader(resourceLoader);
        cfg.setResourceLoaderPath("classpath:/static/templates/");
        cfg.setConfigLocation(new ClassPathResource("velocity.properties"));
        return cfg;
    }

    @Bean
    public ViewResolver viewResolver() {
        VelocityViewResolver resolver = new VelocityViewResolver();
        resolver.setViewClass(VelocityToolboxView.class);
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setPrefix("");
        resolver.setSuffix(".vm");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/META-INF/resources/", "classpath:/resources/",
                "classpath:/static/", "classpath:/public/"};
        registry.addResourceHandler("/**").addResourceLocations(
                CLASSPATH_RESOURCE_LOCATIONS);
    }
}
