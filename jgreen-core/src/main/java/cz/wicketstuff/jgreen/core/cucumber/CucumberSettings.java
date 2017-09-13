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
package cz.wicketstuff.jgreen.core.cucumber;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Martin Strejc
 *
 */
@ConfigurationProperties(prefix="jgreen.cucumber")
public class CucumberSettings {
    
    @NotNull
    @Valid
    private String screenshotsPath;

    @NotNull
    @Valid
    private Boolean screenshotOnEachStep;

    @NotNull
    @Valid
    private Boolean screenshotOnException;

    public String getScreenshotsPath() {
        return screenshotsPath;
    }

    public void setScreenshotsPath(String screenshotsPath) {
        this.screenshotsPath = screenshotsPath;
    }

    public Boolean getScreenshotOnEachStep() {
        return screenshotOnEachStep;
    }

    public void setScreenshotOnEachStep(Boolean screenshotOnEachStep) {
        this.screenshotOnEachStep = screenshotOnEachStep;
    }

    public Boolean getScreenshotOnException() {
        return screenshotOnException;
    }

    public void setScreenshotOnException(Boolean screenshotOnException) {
        this.screenshotOnException = screenshotOnException;
    }

}
