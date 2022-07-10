package ru.anr.base.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import ru.anr.base.BaseSpringParent;

import java.util.Collections;

/**
 * The base application class.
 *
 * @author Alexey Romanchuk
 * @created Mar 11, 2015
 */
@Configuration
@RestController
public abstract class AbstractApplication extends BaseSpringParent {

    /**
     * The build number
     */
    @Value("${application.build:0}")
    private String build;
    /**
     * The version
     */
    @Value("${application.version:0.0.0}")
    private String version;

    /**
     * A runner of applications
     *
     * @param appClass The application's class
     * @param args     Arguments
     */
    public static void runApp(Class<?> appClass, String... args) {
        SpringApplication app = new SpringApplication(appClass);
        app.addListeners(new ApplicationPidFileWriter("app.pid"));
        app.run(args);
    }

    /**
     * Session event publisher definition
     *
     * @return The bean instance
     */
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    /**
     * A configuration for loading 'favicon.ico'
     */
    @Configuration
    public static class FaviconConfiguration {

        @Autowired
        private ApplicationContext ctx;

        /**
         * @return A mapper bean
         */
        @Bean
        public SimpleUrlHandlerMapping myFaviconHandlerMapping() {

            SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
            mapping.setOrder(Integer.MIN_VALUE);
            mapping.setUrlMap(Collections.singletonMap("/favicon.ico", myFaviconRequestHandler()));
            return mapping;
        }

        @Bean
        protected ResourceHttpRequestHandler myFaviconRequestHandler() {
            ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
            requestHandler.setLocations(list(ctx.getResource("classpath:/static/favicon.ico")));
            requestHandler.setCacheSeconds(0);
            return requestHandler;
        }
    }
}
