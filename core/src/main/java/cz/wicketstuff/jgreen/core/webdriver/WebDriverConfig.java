package cz.wicketstuff.jgreen.core.webdriver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Martin Strejc
 *
 */
@Configuration
@EnableConfigurationProperties(WebDriverSettings.class)
public class WebDriverConfig {
    
  @Bean
  @Autowired
  public WebDriverFactoryBean webDriverFacotyrBean(WebDriverSettings settings) {
      return new WebDriverFactoryBean(settings);
  }

}
