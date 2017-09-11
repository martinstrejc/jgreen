package cz.wicketstuff.jgreen.core.cucumber;

import org.springframework.test.context.ContextConfiguration;

import cz.wicketstuff.jgreen.core.UatSupportConfig;
import cz.wicketstuff.jgreen.core.webdriver.WebDriverBase;

import cucumber.api.Scenario;

/**
 * @author Martin Strejc
 *
 */
@ContextConfiguration(classes = { UatSupportConfig.class })
public class UatCucumberBase extends WebDriverBase {

    @InternalCucumberBeforeAfter
    public void beforeEachScenario(Scenario scenario) {
        
    }

    @InternalCucumberBeforeAfter
    public void afterEachScenario(Scenario scenario) {
        driver.quit();
    }

}
