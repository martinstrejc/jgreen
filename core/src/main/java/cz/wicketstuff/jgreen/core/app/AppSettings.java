package cz.wicketstuff.jgreen.core.app;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Martin Strejc
 *
 */
@ConfigurationProperties(prefix="jgreen.app")
public class AppSettings {

    @NotNull
    @Valid
    private String homePageUrl;

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

}
