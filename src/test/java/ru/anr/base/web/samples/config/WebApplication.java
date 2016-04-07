package ru.anr.base.web.samples.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * Main entry point for the application. Uses configs from oauth2 sso and
 * micro-services insfrastructure from Spring Boot stack.
 * 
 *
 *
 * @author Alexey Romanchuk
 * @created Mar 11, 2015
 *
 */
@Configuration
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@RestController
@ImportResource("classpath:web-context.xml")
@SpringBootApplication
public class WebApplication extends WebSecurityConfigurerAdapter {

    /**
     * The logger
     */
    private static final Logger logger = LoggerFactory.getLogger(WebApplication.class);

    /**
     * Writing the App versions
     */
    @PostConstruct
    public void logStarted() {

        logger.info("WEB Application started");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests().anyRequest().anonymous();

    }

    /**
     * Main entry point
     * 
     * @param args
     *            Arguments
     */
    public static void main(String... args) {

        SpringApplication app = new SpringApplication(WebApplication.class);

        app.addListeners(new ApplicationPidFileWriter("./target/app.pid"));
        app.run(args);
    }

    /**
     * Session event publisher definition
     * 
     * @return The bean instance
     */
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {

        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
}
