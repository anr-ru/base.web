package ru.anr.base.web.config.samples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.anr.base.services.BaseService;
import ru.anr.base.services.BaseServiceImpl;

import javax.annotation.PostConstruct;

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
//@EnableZuulProxy  // Must be enabled when doing local development in the Angular app
public class WebApplication extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebApplication.class);

    @PostConstruct
    public void logStarted() {
        logger.info("WEB Application started");
    }

    // TODO: migrate the security configuration from WebSecurityConfigurerAdapter
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().anyRequest().anonymous();
    }

    //@Bean
    //public WebSecurityCustomizer ignoringCustomizer() {
    //    return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
    //}


    /**
     * Main entry point
     *
     * @param args Arguments
     */
    public static void main(String... args) {
        SpringApplication app = new SpringApplication(WebApplication.class);
        app.addListeners(new ApplicationPidFileWriter("./target/app.pid"));
        app.run(args);
    }

    @Bean("service")
    public BaseService service() {
        return new BaseServiceImpl();
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
}
