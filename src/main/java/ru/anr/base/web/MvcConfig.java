/**
 * 
 */
package ru.anr.base.web;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * Configuration for MVC projects. Exports some beans for template and locale
 * processing.
 *
 *
 * @author Alexey Romanchuk
 * @created Nov 24, 2014
 *
 */
@Configuration
public class MvcConfig {

    /**
     * Location of jsp templates (by default is '/WEB-INF/')
     */
    private String templatesRoot = "/WEB-INF/";

    /**
     * Defining a ViewResolver bean
     * 
     * @return Bean instance
     */
    @Bean
    public UrlBasedViewResolver viewResolver() {

        UrlBasedViewResolver r = new UrlBasedViewResolver();
        r.setViewClass(JstlView.class);
        r.setCache(false);

        if (templatesRoot != null) {
            r.setPrefix(templatesRoot);
        }

        r.setSuffix(".html");
        return r;
    }

    /**
     * Defining a locale interceptor
     * 
     * @return Bean instance
     */
    @Bean(name = "localeInterceptor")
    @DependsOn("localeResolver")
    public LocaleChangeInterceptor interceptor() {

        return new LocaleChangeInterceptor();
    }

    /**
     * Defining a locale resolver, which store current locale in cookie.
     * 
     * @return Bean instance
     */
    @Bean(name = "localeResolver")
    public CookieLocaleResolver localeResolver() {

        CookieLocaleResolver r = new CookieLocaleResolver();
        r.setDefaultLocale(Locale.ENGLISH);
        r.setCookieName("applocales");
        r.setCookieMaxAge(60 * 60); // 1 hour

        return r;
    }

    // /////////////////////////////////////////////////////////////////////////
    // /// getters/setters
    // /////////////////////////////////////////////////////////////////////////

    /**
     * @param templatesRoot
     *            the templatesRoot to set
     */
    public void setTemplatesRoot(String templatesRoot) {

        this.templatesRoot = templatesRoot;
    }
}
