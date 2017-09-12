package cz.wicketstuff.jgreen.core.app;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Martin Strejc
 *
 */
@Configuration
@EnableConfigurationProperties(AppSettings.class)
public class AppConfig {


}
