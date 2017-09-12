package cz.wicketstuff.jgreen.core.webdriver;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Martin Strejc
 *
 */
@ConfigurationProperties(prefix="jgreen.webdriver")
public class WebDriverSettings {

    private String browser;
    private String chromeDriver;
    private String geckoDriver;

    @NotNull
    @Valid
    private Boolean remoteEnabled;

    @NotNull
    @Valid
    private String remoteHubUrl;

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getChromeDriver() {
        return chromeDriver;
    }

    public void setChromeDriver(String chromeDriver) {
        this.chromeDriver = chromeDriver;
    }

    public String getGeckoDriver() {
        return geckoDriver;
    }

    public void setGeckoDriver(String geckoDriver) {
        this.geckoDriver = geckoDriver;
    }

    public Boolean getRemoteEnabled() {
        return remoteEnabled;
    }

    public void setRemoteEnabled(Boolean remoteEnabled) {
        this.remoteEnabled = remoteEnabled;
    }

    public String getRemoteHubUrl() {
        return remoteHubUrl;
    }

    public void setRemoteHubUrl(String remoteHubUrl) {
        this.remoteHubUrl = remoteHubUrl;
    }
}
