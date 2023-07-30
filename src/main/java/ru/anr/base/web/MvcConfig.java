/*
 * Copyright 2014-2022 the original author or authors.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * A configuration for MVC-based Spring Boot projects. Exports some beans for templates
 * loading, locale processing and resource managements.
 *
 * @author Alexey Romanchuk
 * @created Nov 24, 2014
 */
public class MvcConfig implements WebMvcConfigurer, ResourceLocator {

    private static final Logger logger = LoggerFactory.getLogger(MvcConfig.class);

    // The classpath-related location of html/jsp templates
    private String templatesRoot = "/static/";

    // Templates suffix
    private String suffix = ".html";

    // To cache the templates or not
    private boolean caching = false;

    // Cache Period in seconds
    private int cachePeriod = 3600;

    /**
     * Defines the ViewResolver bean
     *
     * @return The bean instance
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {

        InternalResourceViewResolver r = new InternalResourceViewResolver();
        r.setViewClass(JstlView.class);
        r.setCache(caching);
        r.setPrefix(""); // reset this path as we use #addResourceHandlers below
        r.setSuffix(suffix);
        return r;
    }

    /**
     * Defines the locale interceptor
     *
     * @return The bean instance
     */
    @Bean(name = "localeInterceptor")
    @DependsOn("localeResolver")
    public LocaleChangeInterceptor interceptor() {
        return new LocaleChangeInterceptor();
    }

    /**
     * Defines the locale resolver which stores the current locale in cookies.
     *
     * @return Bean instance
     */
    @Bean(name = "localeResolver")
    public CookieLocaleResolver localeResolver() {

        CookieLocaleResolver r = new CookieLocaleResolver();
        // if set default, get locale from browser not work.
        // r.setDefaultLocale(Locale.ENGLISH);
        r.setCookieName("applocales");
        r.setCookieMaxAge(60 * 60 * 24 * 360 * 10); // 10 years

        return r;
    }

    // Defines the main servlet name
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable("dispatcher");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Resource locations = this.getPath("");
        logger.debug("Building resource handlers {}", locations);
        registry.addResourceHandler("/**")
                .addResourceLocations(locations)
                .setCachePeriod(cachePeriod)
                .resourceChain(caching)
                .addResolver(new PathResourceResolver());
    }

    @Override
    public Resource getPath(String resource) {
        logger.debug("Requesting a classpath resource {}/{}", this.templatesRoot, resource);
        return new ClassPathResource(this.templatesRoot + resource);
    }

    ///////////////////////////////////////////////////////////////////////////
    ///// getters/setters
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param templatesRoot the templatesRoot to set
     */
    public void setTemplatesRoot(String templatesRoot) {

        this.templatesRoot = templatesRoot;
    }

    /**
     * @param suffix the suffix to set
     */
    public void setSuffix(String suffix) {

        this.suffix = suffix;
    }

    /**
     * @param caching the caching to set
     */
    public void setCaching(boolean caching) {

        this.caching = caching;
    }

    public void setCachePeriod(int cachePeriod) {
        this.cachePeriod = cachePeriod;
    }
}
