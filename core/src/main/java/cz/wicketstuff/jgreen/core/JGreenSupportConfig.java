package cz.wicketstuff.jgreen.core;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import cz.wicketstuff.jgreen.core.app.AppConfig;
import cz.wicketstuff.jgreen.core.cucumber.CucumberConfig;
import cz.wicketstuff.jgreen.core.webdriver.WebDriverConfig;

/**
 * @author Martin Strejc
 *
 */
@Configuration
@Import({ CucumberConfig.class, 
          AppConfig.class, 
          WebDriverConfig.class
        })
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class JGreenSupportConfig {

}
