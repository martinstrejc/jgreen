package cz.wicketstuff.jgreen.core.cucumber;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Martin Strejc
 *
 */
@ConfigurationProperties(prefix="jgreen.cucumber")
public class CucumberSettings {
    
    @NotNull
    @Valid
    private String screenshotsPath;

    @NotNull
    @Valid
    private Boolean screenshotOnEachStep;

    @NotNull
    @Valid
    private Boolean screenshotOnException;

    public String getScreenshotsPath() {
        return screenshotsPath;
    }

    public void setScreenshotsPath(String screenshotsPath) {
        this.screenshotsPath = screenshotsPath;
    }

    public Boolean getScreenshotOnEachStep() {
        return screenshotOnEachStep;
    }

    public void setScreenshotOnEachStep(Boolean screenshotOnEachStep) {
        this.screenshotOnEachStep = screenshotOnEachStep;
    }

    public Boolean getScreenshotOnException() {
        return screenshotOnException;
    }

    public void setScreenshotOnException(Boolean screenshotOnException) {
        this.screenshotOnException = screenshotOnException;
    }

}
