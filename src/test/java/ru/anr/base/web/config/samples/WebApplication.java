package ru.anr.base.web.config.samples;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.anr.base.services.BaseService;
import ru.anr.base.services.BaseServiceImpl;
import ru.anr.base.web.controllers.AbstractApplication;

/**
 * The main entry point for the application.
 *
 * @author Alexey Romanchuk
 * @created Mar 11, 2015
 */
@Configuration
@ImportResource("classpath:web-context.xml")
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        WebMvcAutoConfiguration.class
})
@EnableWebMvc
@EnableWebSecurity
//@EnableZuulProxy  // Must be enabled when doing local development in the Angular app
@PropertySources({
        @PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true),
        @PropertySource(value = "file:application.yml", ignoreResourceNotFound = true)
})
public class WebApplication extends AbstractApplication {

    /**
     * Main entry point
     *
     * @param args Arguments
     */
    public static void main(String... args) {
        runApp(WebApplication.class);
    }

    // Some test service
    @Bean("service")
    public BaseService service() {
        return new BaseServiceImpl();
    }
}
