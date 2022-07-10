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
package ru.anr.base.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.anr.base.BaseSpringParent;
import ru.anr.base.services.serializer.JSONSerializerImpl;
import ru.anr.base.services.serializer.Serializer;

import java.util.Map;

/**
 * A base prototype for the Index Page
 *
 * @author Alexey Romanchuk
 * @created Jul 8, 2022
 */
public abstract class AbstractIndexController extends BaseSpringParent {

    private static final Logger logger = LoggerFactory.getLogger(AbstractIndexController.class);

    /**
     * The build number
     */
    @Value("${application.build:0}")
    private String build;

    @Value("${application.version:0.0.0}")
    private String version;

    /**
     * JSON serializer
     */
    private final Serializer json = new JSONSerializerImpl();

    /**
     * Prepares a config model
     *
     * @return A model instance
     */
    protected ConfigModel prepareConfig() {
        ConfigModel cfg = new ConfigModel();
        cfg.getProps().put("buildnumber", build);
        cfg.getProps().put("version", version);
        cfg.getProps().put("production", isProdMode());
        cfg.getProps().put("profiles", getProfiles());
        return cfg;
    }

    /**
     * Returns a JSON with configuration parameters
     *
     * @return The serialized JSON
     */
    @RequestMapping(value = {"/config"}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getConfig() {
        return json.toStr(prepareConfig());
    }

    /**
     * Initializes the main page with some variables.
     *
     * @param model A model with variables for the page
     * @return A view to redirect
     */
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String getPageData(Map<String, Object> model) {

        logger.info("Profiles: {}", getProfiles());

        model.put("production", isProdMode());
        model.put("buildnumber", build);

        return "index"; // the name of the template
    }
}
