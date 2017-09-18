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

import cz.wicketstuff.jgreen.core.ScreenshotProvider;
import cz.wicketstuff.jgreen.core.app.AppSettings;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author Martin Strejc
 * Framework for Selenium
 *
 */
public class WebDriverBase implements ScreenshotProvider {
    private static final Logger log = LoggerFactory.getLogger(WebDriverBase.class);

    private static final String TIMEOUT = "timeout";

    @Autowired
    protected WebDriver driver;
    
    @Autowired
    protected WebDriverSettings webDriverSettings;

    @Autowired
    protected AppSettings appSettings;

    private int defaultWaitSeconds = 120;

    protected boolean acceptNextAlert = true;

    @Override
    public byte[] takeScreenshot() {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

//    public void close() {
//        driver.close();
//    }


    /*
    Alerts
     */

//    public boolean isAlertPresent() {
//        try {
//            driver.switchTo().alert();
//            return true;
//        } catch (NoAlertPresentException e) {
//            log.debug("Alert hasn't been found", e);
//            return false;
//        }
//    }
//
//    public String closeAlertAndGetItsText() {
//        try {
//            Alert alert = driver.switchTo().alert();
//            String alertText = alert.getText();
//            if (acceptNextAlert) {
//                alert.accept();
//            } else {
//                alert.dismiss();
//            }
//            return alertText;
//        } finally {
//            acceptNextAlert = true;
//        }
//    }


    /*
    URL opening
    */

//    public String getAppHomePageUrl() {
//        return appSettings.getHomePageUrl();
//    }
//
//    public void openUrl(String url) {
//        driver.get(url);
//    }
//
//    public void openAppHomePageUrl() {
//        openUrl(getAppHomePageUrl());
//    }

    /*
    Element actions
     */

//    public void setField(By by, CharSequence... keysToSend) {
//        clear(by);
//        sendKeys(by, keysToSend);
//    }
//
//    public void setFieldString(By by, String stringToSend) {
//        clear(by);
//        sendString(by, stringToSend);
//    }
//
//
//    public void waitAndSetField(By by, CharSequence... keysToSend) throws InterruptedException {
//        waitAndSetField(getDefaultWaitSeconds(), by, keysToSend);
//    }
//
//    public void waitAndSetField(int timeInSeconds, By by, CharSequence... keysToSend) throws InterruptedException {
//        sleepSeconds(timeInSeconds);
//        setField(by, keysToSend);
//    }
//
//    public void sendString(By by, String stringToSend) {
//        findElement(by).sendKeys(stringToSend);
//    }
//
//    public void sendKeys(By by, CharSequence... keysToSend) {
//        findElement(by).sendKeys(keysToSend);
//    }
//    
//    public void clear(By by) {
//        findElement(by).clear();
//    }
//
//    public void selectFromDropdown(By by, String optionText) {
//        new Select(driver.findElement(by)).selectByVisibleText(optionText);
//    }
    
    /*
    Attribute actions
     */

    
    
//    public void assertAttributeEquals (By by, String attribute, String expectedValue){
//        assertEquals(expectedValue, getElementAttribute(by, attribute));
//    }
//
//    public void assertAttributeNotEquals (By by, String attribute, String expectedValue){
//        assertNotEquals(expectedValue, getElementAttribute(by, attribute));
//    }
//
//    public boolean attributeContains(By by, String attribute, String expectedValue) {
//        return getElementAttribute(by, attribute).contains(expectedValue);
//    }
//
//    public boolean attributeNotContains(By by, String attribute, String expectedValue) {
//        return !getElementAttribute(by, attribute).contains(expectedValue);
//    }
//
//    public boolean isAttributePresent(By by, String attribute) {
//        return findElement(by).getAttribute(attribute) != null;
//    }
//
//    public void assertAttributePresent(By by, String attribute) {
//        assertEquals(true, isAttributePresent(by, attribute));
//    }
//
//    public void assertAttributeNotPresent(By by, String attribute) {
//        assertEquals(false, isAttributePresent(by, attribute));
//    }
//
//    public void waitForAttributeNotPresent(By by, String attribute) throws InterruptedException {
//        waitForAttributeNotPresent(getDefaultWaitSeconds(), by, attribute);
//    }
//
//    public void waitForAttributeNotPresent(int timeInSeconds, By by, String attribute) throws InterruptedException {
//        for (int second = 0; second < timeInSeconds; second++) {
//            if (!isAttributePresent(by, attribute)) return;
//            sleep(1000);
//        }
//        fail(TIMEOUT);
//    }

    /*
    Value verification
     */
//    public String getElementValue(String id) throws Exception {
//        return (String) ((JavascriptExecutor)driver).executeScript("return document.getElementById('"+id+"').value;");
//    }

}
