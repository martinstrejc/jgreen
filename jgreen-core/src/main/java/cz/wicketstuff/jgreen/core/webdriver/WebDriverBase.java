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

    private static final String ATTRIBUTE_TRUE = "true";
    private static final String ATTRIBUTE_FALSE = "false";
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

    public void close() {
        driver.close();
    }

    /*
    Check if element present
     */
    
    public boolean isElementPresent(By by) {
        try {
            findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            log.debug("Element hasn't been found", e);
            return false;
        }
    }

    public void assertDisplayed(By by) {
        assertDisplayed(findElement(by));
    }

    public void assertNotDisplayed(By by) {
        try {
            assertNotDisplayed(findElement(by));
        } catch (NoSuchElementException e) {
            log.debug("assertNotDisplayed: Element hasn't been found", e);
        }
    }

    public void assertDisplayed(WebElement webElement) {
        if (!webElement.isDisplayed())
        {
            fail(webElement + " element should be displayed.");
        }
    }

    public void assertNotDisplayed(WebElement webElement) {
        if (webElement.isDisplayed())
        {
            fail(webElement + " element should not be displayed.");
        }
    }

    /*
    Alerts
     */

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            log.debug("Alert hasn't been found", e);
            return false;
        }
    }

    public String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    /*
    All about waiting
     */
    
    public void sleep(long timeInMillis) throws InterruptedException {
        Thread.sleep(timeInMillis);
    }

    public void sleepSeconds(int seconds) throws InterruptedException {
        sleep(seconds * 1000L);
    }

    public void waitForElement(By by) throws InterruptedException {
        waitForElement(getDefaultWaitSeconds(), by);
    }

    public void waitForElement(int timeInSeconds, By by) throws InterruptedException {
        for (int second = 0; second < timeInSeconds; second++) {
            if (isElementPresent(by)) {
                return;
            }
            sleep(1000);
        }
        fail(TIMEOUT);
    }

    public void waitForElementNotPresent(By by) throws InterruptedException {
        waitForElementNotPresent(getDefaultWaitSeconds(), by);
    }

    public void waitForElementNotPresent(int timeInSeconds, By by) throws InterruptedException {
        for (int second = 0; second < timeInSeconds; second++) {
            if (!isElementPresent(by)) {
                return;
            }
            sleep(1000);
        }
        fail(TIMEOUT);
    }

    public void waitAndClick(By by) throws InterruptedException {
        waitAndClick(getDefaultWaitSeconds(), by);
    }

    public void waitAndClick(int timeInSeconds, By by) throws InterruptedException {
        waitForElement(timeInSeconds, by);
        click(by);
    }

    public int getDefaultWaitSeconds() {
        return defaultWaitSeconds;
    }

    public void setDefaultWaitSeconds(int defaultWaitSeconds) {
        this.defaultWaitSeconds = defaultWaitSeconds;
    }

    /*
    URL opening
    */

    public String getAppHomePageUrl() {
        return appSettings.getHomePageUrl();
    }

    public void openUrl(String url) {
        driver.get(url);
    }

    public void openAppHomePageUrl() {
        openUrl(getAppHomePageUrl());
    }

    /*
    Element actions
     */

    public void click(By by) {
        findElement(by).click();
    }

    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public void setField(By by, CharSequence... keysToSend) {
        clear(by);
        sendKeys(by, keysToSend);
    }

    public void setFieldString(By by, String stringToSend) {
        clear(by);
        sendString(by, stringToSend);
    }


    public void waitAndSetField(By by, CharSequence... keysToSend) throws InterruptedException {
        waitAndSetField(getDefaultWaitSeconds(), by, keysToSend);
    }

    public void waitAndSetField(int timeInSeconds, By by, CharSequence... keysToSend) throws InterruptedException {
        sleepSeconds(timeInSeconds);
        setField(by, keysToSend);
    }

    public void sendString(By by, String stringToSend) {
        findElement(by).sendKeys(stringToSend);
    }

    public void sendKeys(By by, CharSequence... keysToSend) {
        findElement(by).sendKeys(keysToSend);
    }
    
    public void clear(By by) {
        findElement(by).clear();
    }

    public void selectFromDropdown(By by, String optionText) {
        new Select(driver.findElement(by)).selectByVisibleText(optionText);
    }
    
    /*
    Attribute actions
     */

    public void assertAttributeTrue(By by, String attribute) {
        assertEquals(ATTRIBUTE_TRUE, getElementAttribute(by, attribute));
    }

    public void assertAttributeFalse(By by, String attribute) {
        assertEquals(ATTRIBUTE_FALSE, getElementAttribute(by, attribute));
    }

    public boolean attributeTrue(By by, String attribute) {
        return ATTRIBUTE_TRUE.equals(getElementAttribute(by, attribute));
    }

    public boolean attributeNotTrue(By by, String attribute) {
        return !ATTRIBUTE_TRUE.equals(getElementAttribute(by, attribute));
    }

    public boolean attributeFalse(By by, String attribute) {
        return ATTRIBUTE_FALSE.equals(getElementAttribute(by, attribute));
    }
    
    public String getElementAttribute(By by, String attribute) {
        return findElement(by).getAttribute(attribute);
    }

    public void waitForAttributeTrue(By by, String attribute) throws InterruptedException {
        waitForAttributeTrue(getDefaultWaitSeconds(), by, attribute);
    }


    public void waitForAttributeEquals(By by, String attribute, String expectedValue) throws InterruptedException {
        waitForAttributeEquals(getDefaultWaitSeconds(), by, attribute, expectedValue);
    }

    public void assertAttributeEquals (By by, String attribute, String expectedValue){
        assertEquals(expectedValue, getElementAttribute(by, attribute));
    }

    public void assertAttributeNotEquals (By by, String attribute, String expectedValue){
        assertNotEquals(expectedValue, getElementAttribute(by, attribute));
    }

    public void waitForAttributeTrue(int timeInSeconds, By by, String attribute) throws InterruptedException {
        for (int second = 0; second < timeInSeconds; second++) {
            if (attributeTrue(by, attribute)) return;
            sleep(1000);
        }
        fail("Attribute '" + attribute + "' timeout, the TRUE value has not bean reached for " + timeInSeconds + " s");
    }

    public void waitForAttributeEquals(int timeInSeconds, By by, String attribute, String expectedValue) throws InterruptedException {
        for (int second = 0; second < timeInSeconds; second++) {
            if (attributeEquals(by, attribute, expectedValue)) return;
            sleep(1000);
        }
        failTimeout(timeInSeconds, attribute, expectedValue);
    }

    private void failTimeout(int timeInSeconds, String attribute, String expectedValue) {
        fail("Attribute '" + attribute + "' timeout, the value " + expectedValue + " has not bean reached for " + timeInSeconds + " s");
    }

    public void waitForAttributeNotEquals(By by, String attribute, String expectedValue) throws InterruptedException {
        waitForAttributeNotEquals(getDefaultWaitSeconds(), by, attribute, expectedValue);
    }

    public void waitForAttributeNotEquals(int timeInSeconds, By by, String attribute, String expectedValue) throws InterruptedException {
        for (int second = 0; second < timeInSeconds; second++) {
            if (!attributeEquals(by, attribute, expectedValue)) return;
            sleep(1000);
        }
        failTimeout(timeInSeconds, attribute, expectedValue);
    }

    public boolean attributeEquals(By by, String attribute, String expectedValue) {
        return expectedValue.equals(getElementAttribute(by, attribute));
    }

    public boolean attributeContains(By by, String attribute, String expectedValue) {
        return getElementAttribute(by, attribute).contains(expectedValue);
    }

    public boolean attributeNotContains(By by, String attribute, String expectedValue) {
        return !getElementAttribute(by, attribute).contains(expectedValue);
    }

    public void waitForAttributeContains(int timeInSeconds, By by, String attribute, String expectedValue) throws InterruptedException {
        for (int second = 0; second < timeInSeconds; second++) {
            if (attributeContains(by, attribute, expectedValue)) return;
            sleep(1000);
        }
        failTimeout(timeInSeconds, attribute, expectedValue);
    }

    public void waitForAttributeNotContains(By by, String attribute, String expectedValue) throws InterruptedException {
        waitForAttributeNotContains(getDefaultWaitSeconds(),by, attribute, expectedValue);
    }

    public void waitForAttributeNotContains(int timeInSeconds, By by, String attribute, String expectedValue) throws InterruptedException {
        for (int second = 0; second < timeInSeconds; second++) {
            if (attributeNotContains(by, attribute, expectedValue)) return;
            sleep(1000);
        }
        failTimeout(timeInSeconds, attribute, expectedValue);
    }

    public void waitForAttributeContains(By by, String attribute, String expectedValue) throws InterruptedException {
        waitForAttributeContains(getDefaultWaitSeconds(),by, attribute, expectedValue);
    }

    public void waitForAttributeFalse(By by, String attribute) throws InterruptedException {
        waitForAttributeFalse(getDefaultWaitSeconds(), by, attribute);
    }

     public void waitForAttributeFalse(int timeInSeconds, By by, String attribute) throws InterruptedException {
        for (int second = 0; second < timeInSeconds; second++) {
            if (attributeFalse(by, attribute)) return;
            sleep(1000);
        }
        fail(TIMEOUT);
    }

    public boolean isAttributePresent(By by, String attribute) {
        return findElement(by).getAttribute(attribute) != null;
    }

    public void assertAttributePresent(By by, String attribute) {
        assertEquals(true, isAttributePresent(by, attribute));
    }

    public void assertAttributeNotPresent(By by, String attribute) {
        assertEquals(false, isAttributePresent(by, attribute));
    }

    public void waitForAttributeNotPresent(By by, String attribute) throws InterruptedException {
        waitForAttributeNotPresent(getDefaultWaitSeconds(), by, attribute);
    }

    public void waitForAttributeNotPresent(int timeInSeconds, By by, String attribute) throws InterruptedException {
        for (int second = 0; second < timeInSeconds; second++) {
            if (!isAttributePresent(by, attribute)) return;
            sleep(1000);
        }
        fail(TIMEOUT);
    }

    public void waitForAttributePresent(By by, String attribute) throws InterruptedException {
        waitForAttributePresent(getDefaultWaitSeconds(), by, attribute);
    }

    public void waitForAttributePresent(int timeInSeconds, By by, String attribute) throws InterruptedException {
        for (int second = 0; second < timeInSeconds; second++) {
            if (isAttributePresent(by, attribute)) return;
            sleep(1000);
        }
        fail(TIMEOUT);
    }

    /*
    Text verification
     */

    public void verifyTextForElement(By by, String text){
        // verifies if element contains the exact text
        assertEquals(text, findElement(by).getText());
    }

    public void verifyTextForElementContains(By by, String text){
        // verifies if element contains the text
        assertTrue(driver.findElement(by).getText().matches("^[\\s\\S]*"+text+"[\\s\\S]*$"));
    }

    public String storeElementText(By by) throws Exception {
        return driver.findElement(by).getText();
    }

    /*
    Value verification
     */
    public String getElementValue(String id) throws Exception {
        return (String) ((JavascriptExecutor)driver).executeScript("return document.getElementById('"+id+"').value;");
    }

}
