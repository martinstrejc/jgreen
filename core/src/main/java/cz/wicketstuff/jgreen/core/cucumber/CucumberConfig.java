package cz.wicketstuff.jgreen.core.cucumber;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author Martin Strejc
 *
 */
@Configuration
@PropertySource(name="cucumber", value = {
        "classpath:cucumber-default.properties", 
        "classpath:cucumber.properties", 
        "classpath:cucumber-${spring.profiles.active}.properties"
        }, ignoreResourceNotFound = true)
@EnableConfigurationProperties(CucumberSettings.class)
public class CucumberConfig {
    
    @Bean
    public CucumberLoggingAspect cucumberLoggingAspect() {
        return new CucumberLoggingAspect();
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
