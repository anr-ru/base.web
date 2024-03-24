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
package ru.anr.base.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * @author Alexey Romanchuk
 * @created Jul 08, 2022
 */
public class WebInitializer implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory.getLogger(WebInitializer.class);

    @Autowired
    private WebApplicationContext context;

    @Override
    public void onStartup(ServletContext container) {

        ServletRegistration.Dynamic dispatcher = container
                .addServlet("dispatcher", new DispatcherServlet(context));

        if (dispatcher != null) {
            dispatcher.setLoadOnStartup(1);
            dispatcher.addMapping("/");

            logger.info("Started the main 'dispatcher' servlet");
        }
    }
}
