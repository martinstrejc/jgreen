package cz.wicketstuff.jgreen.core.webdriver;

import org.apache.commons.lang.BooleanUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Martin Strejc
 *
 */
public class WebDriverFactoryBean 
    implements FactoryBean<WebDriver>, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(WebDriverFactoryBean.class);

    private final WebDriverSettings settings;
     
    private Set<WebDriver> driverInstances = new CopyOnWriteArraySet<WebDriver>();

    public WebDriverFactoryBean(WebDriverSettings settings) {
        this.settings = settings;
    }

    @Override
    public void destroy() throws Exception {
        log.debug("Destroying all instances of webdriver");
        for (WebDriver driver : driverInstances) {
            destroy(driver);
        }
    }
    
    public void destroy(WebDriver driver) {
        try {
            log.debug("Destroying driver {}", driver);
            driver.quit();
        } catch (Exception e) {
            log.error("Cannot destroy a driver " + driver, e);
        } finally {
            driverInstances.remove(driver);    
        }
    }

//    @Override
//    public synchronized WebDriver getObject() throws Exception {
//        System.setProperty("webdriver.chrome.driver", settings.getChromeDriver());
//        WebDriver driver = new ChromeDriver();
//        driverInstances.add(driver);
//        return driver;
//    }
    
    @Override
    public synchronized WebDriver getObject() throws Exception {
        WebDriver driver;

        if ("firefox".equalsIgnoreCase(settings.getBrowser())) {
            driver = firefox();
        } else if ("chrome".equalsIgnoreCase(settings.getBrowser())) {
            driver = chrome();
        } else if (BooleanUtils.isTrue(settings.getRemoteEnabled())) {
            driver = remote();
        } else {
            driver = chrome();
        }

        driverInstances.add(driver);
        configureDriver(driver);
        return driver;
    }
    
    private WebDriver chrome() {
        System.setProperty("webdriver.chrome.driver", settings.getChromeDriver());
        return new ChromeDriver();
    }

    private WebDriver firefox() {
        System.setProperty("webdriver.gecko.driver", settings.getGeckoDriver());
        return new FirefoxDriver();
    }
    
    private WebDriver remote() throws MalformedURLException {
        WebDriver driver = new RemoteWebDriver(new URL(settings.getRemoteHubUrl()), DesiredCapabilities.firefox());
        driver.manage().window().setSize(new Dimension(1024, 768));
        return driver;
    }
    
    protected void configureDriver(WebDriver driver) {
        //todo: configure local webdriver, if needed
    }

    @Override
    public Class<WebDriver> getObjectType() {
        return WebDriver.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
    

}
