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

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        logger.info("Started the main 'dispatcher' servlet");
    }
}
