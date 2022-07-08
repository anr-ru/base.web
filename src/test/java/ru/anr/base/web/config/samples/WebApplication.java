package ru.anr.base.web.config.samples;

import org.apache.jasper.servlet.JspServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.bind.annotation.RestController;
import ru.anr.base.BaseParent;
import ru.anr.base.web.samples.api.IndexController;
import ru.anr.base.web.samples.api.MockApiController;

import javax.annotation.PostConstruct;

/**
 * Main entry point for the application. Uses configs from oauth2 sso and
 * microservices infrastructure from Spring Boot stack.
 *
 * @author Alexey Romanchuk
 * @created Mar 11, 2015
 */
@Configuration
@RestController
@ImportResource("classpath:web-context.xml")
@ComponentScan(basePackageClasses = {IndexController.class, MockApiController.class})
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        WebMvcAutoConfiguration.class
})
//@EnableZuulProxy
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

    /**
     * Session event publisher definition
     *
     * @return The bean instance
     */
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    //@Bean
    public ServletRegistrationBean<JspServlet> customServletBean() {

        ServletRegistrationBean<JspServlet> bean = new ServletRegistrationBean<>(new JspServlet(), "/*");
        bean.setLoadOnStartup(3);
        bean.setInitParameters(BaseParent.toMap("httpMethods", "GET"));

        return bean;
    }
}
