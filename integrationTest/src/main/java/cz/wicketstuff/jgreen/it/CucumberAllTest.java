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
package cz.wicketstuff.jgreen.it;

import cz.wicketstuff.jgreen.core.cucumber.CucumberRunner;
import cucumber.api.CucumberOptions;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Run from Gradle CLI: ./gradlew :cleanTest :test --tests cz.wicketstuff.jgreen.it.CucumberAllTest
 *
 */
@RunWith(CucumberRunner.class)
@CucumberOptions(
        monochrome = true,
        plugin = { "pretty", "html:build/cucumber-reports/html", "json:build/cucumber-reports/cucumber.json" },
        features = { "src/integrationTest/cucumber" },
        glue = { "cz.wicketstuff.jgreen.it" },
        tags = {"~@Ignore"},
        junit = {"--filename-compatible-names"}
)
public class CucumberAllTest {

    @Test
    public void dummyTest() {
        // real tests are in *.feature and Glue files, this one is only to make them runnable as junits
    }

}
