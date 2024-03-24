/*
 * Copyright 2014-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ru.anr.base.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import ru.anr.base.BaseSpringParent;
import ru.anr.base.web.ResourceLocator;

import javax.annotation.PostConstruct;
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

    private static final Logger logger = LoggerFactory.getLogger(AbstractApplication.class);

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

    @PostConstruct
    public void started() {
        logger.info("Web App started: version={}, build={}", this.version, this.build);
    }


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
     * A configuration for loading 'favicon.ico' from the 'assets' directory.
     */
    @Configuration
    public static class FaviconConfiguration {

        @Autowired
        private ResourceLocator resources;

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
            requestHandler.setLocations(list(resources.getPath("/assets/favicon.ico")));
            requestHandler.setCacheSeconds(0);
            return requestHandler;
        }
    }
}
