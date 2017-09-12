package cz.wicketstuff.jgreen.core.cucumber;

import org.springframework.test.context.ContextConfiguration;

import cz.wicketstuff.jgreen.core.JGreenSupportConfig;
import cz.wicketstuff.jgreen.core.webdriver.WebDriverBase;

import cucumber.api.Scenario;

/**
 * @author Martin Strejc
 *
 */
@ContextConfiguration(classes = { JGreenSupportConfig.class })
public class JGreenCucumberBase extends WebDriverBase {

    @InternalCucumberBeforeAfter
    public void beforeEachScenario(Scenario scenario) {
        
    }

    @InternalCucumberBeforeAfter
    public void afterEachScenario(Scenario scenario) {
        driver.quit();
    }

}
