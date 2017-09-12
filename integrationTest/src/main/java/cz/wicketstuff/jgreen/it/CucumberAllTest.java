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
