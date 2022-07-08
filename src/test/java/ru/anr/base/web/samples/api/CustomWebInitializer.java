package ru.anr.base.web.samples.api;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * @author Alexey Romanchuk
 * @created Jul 08, 2022
 */
public class CustomWebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {

        XmlWebApplicationContext context = new XmlWebApplicationContext();
        context.setConfigLocation("classpath:/web-context.xml");

        ServletRegistration.Dynamic dispatcher = container
                .addServlet("dispatcher", new DispatcherServlet(context));

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
