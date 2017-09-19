/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.wicketstuff.jgreen.core.webdriver;

import org.apache.commons.lang.BooleanUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Martin Strejc
 *
 */
// TODO this class has been copy pasted from the previous project
public class WebDriverFactoryBean 
    implements FactoryBean<WebDriver>, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(WebDriverFactoryBean.class);

    private final WebDriverSettings settings;
     
    private Set<WebDriver> driverInstances = new CopyOnWriteArraySet<>();

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
        WebDriver driver = chrome();

//        if ("firefox".equalsIgnoreCase(settings.getBrowser())) {
//            driver = firefox();
//        } else if ("chrome".equalsIgnoreCase(settings.getBrowser())) {
//            driver = chrome();
//        } else if (BooleanUtils.isTrue(settings.getRemoteEnabled())) {
//            driver = remote();
//        } else {
//            driver = chrome();
//        }

        driverInstances.add(driver);
        configureDriver(driver);
        return driver;
    }
    
    private WebDriver chrome() {
    	// System.setProperty("webdriver.chrome.driver", "c:/programs/chromedriver_2_32.exe");
    	// System.setProperty("webdriver.chrome.driver", settings.getChromeDriver());
    
    	ChromeDriverService.Builder builder = new ChromeDriverService.Builder();
    	builder.usingDriverExecutable(new File(settings.getChromeDriver()));
    	
    	DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    	ChromeOptions options = new ChromeOptions();
    	// options.addArguments("test-type");
    	// options.setBinary("c:\\programs\\chromedriver.exe");
    	// options.setBinary("c:/programs/chromedriver_2_32.exe");
    	// capabilities.setCapability("chrome.binary", "c:\\data\\chromedriver.exe");
    	options.addArguments(settings.getArguments());
    	capabilities.setCapability(ChromeOptions.CAPABILITY, options);
    	return new ChromeDriver(builder.build(), capabilities);
    }

//    private WebDriver firefox() {
//        System.setProperty("webdriver.gecko.driver", settings.getGeckoDriver());
//        return new FirefoxDriver();
//    }
//    
//    private WebDriver remote() throws MalformedURLException {
//        WebDriver driver = new RemoteWebDriver(new URL(settings.getRemoteHubUrl()), DesiredCapabilities.firefox());
//        driver.manage().window().setSize(new Dimension(1024, 768));
//        return driver;
//    }
    
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
