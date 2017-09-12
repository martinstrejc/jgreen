/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.wicketstuff.jgreen.core.webdriver;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Martin Strejc
 *
 */
@ConfigurationProperties(prefix="jgreen.webdriver")
public class WebDriverSettings {

    private String browser;
    private String chromeDriver;
    private String geckoDriver;

    @NotNull
    @Valid
    private Boolean remoteEnabled;

    @NotNull
    @Valid
    private String remoteHubUrl;

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getChromeDriver() {
        return chromeDriver;
    }

    public void setChromeDriver(String chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    public String getGeckoDriver() {
        return geckoDriver;
    }

    public void setGeckoDriver(String geckoDriver) {
        this.geckoDriver = geckoDriver;
    }

    public Boolean getRemoteEnabled() {
        return remoteEnabled;
    }

    public void setRemoteEnabled(Boolean remoteEnabled) {
        this.remoteEnabled = remoteEnabled;
    }

    public String getRemoteHubUrl() {
        return remoteHubUrl;
    }

    public void setRemoteHubUrl(String remoteHubUrl) {
        this.remoteHubUrl = remoteHubUrl;
    }
}
