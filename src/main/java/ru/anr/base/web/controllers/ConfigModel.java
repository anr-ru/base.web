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

import ru.anr.base.BaseParent;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/**
 * A model for configuration properties.
 *
 * @author Alexey Romanchuk
 * @created Jul 8, 2022
 */
@XmlRootElement(name = "config")
public class ConfigModel {

    // Properties to config
    private Map<String, Object> props = BaseParent.toMap();

    /**
     * @return the props
     */
    @XmlElementWrapper(name = "props")
    public Map<String, Object> getProps() {
        return props;
    }

    /**
     * @param props the props to set
     */
    public void setProps(Map<String, Object> props) {
        this.props = props;
    }
}
