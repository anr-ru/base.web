/*
 * Copyright 2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package ru.anr.base.web;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

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
     * Templates suffix
     */
    private String suffix = ".html";

    /**
     * To cache the JSPs or not
     */
    private boolean caching = true;

    /**
     * Defining a ViewResolver bean
     * 
     * @return Bean instance
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {

        InternalResourceViewResolver r = new InternalResourceViewResolver();
        r.setViewClass(JstlView.class);
        r.setCache(caching);

        if (templatesRoot != null) {
            r.setPrefix(templatesRoot);
        }

        r.setSuffix(suffix);
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

    /**
     * @param suffix
     *            the suffix to set
     */
    public void setSuffix(String suffix) {

        this.suffix = suffix;
    }

    /**
     * @param caching
     *            the caching to set
     */
    public void setCaching(boolean caching) {

        this.caching = caching;
    }
}
