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

import static org.junit.Assert.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.wicketstuff.jgreen.core.misc.TimerService;

/**
 * @author Martin Strejc
 *
 */
public class HtmlService {
	
	private static final Logger log = LoggerFactory.getLogger(HtmlService.class);

    private static final String ATTRIBUTE_TRUE = "true";
    
    private static final String ATTRIBUTE_FALSE = "false";

	protected final WebDriver driver;
	
	private final TimerService timer;

	public HtmlService(WebDriver webDriver, TimerService timer) {
		this.driver = webDriver;
		this.timer = timer;
	}

	public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public boolean isElementPresent(By by) {
        try {
            findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            log.trace("Element hasn't been found", e);
            return false;
        }
    }
    
    public boolean isElementPresent(By by, int timeInSeconds) {
        boolean present = false;
        int second = 0;
        while (!(present = isElementPresent(by)) && ++second <= timeInSeconds) {
        	timer.sleepSecond();
        }
        return present;
    } 

    public void waitForElement(By by, int timeInSeconds) {
        if (!isElementPresent(by, timeInSeconds)) {
	        fail("Element " + by.toString() + " has not been found in " 
	        		+ timeInSeconds + " s");
        }
    }

    public void waitForElementNotPresent(By by, int timeInSeconds) {
        if (isElementPresent(by, timeInSeconds)) {
        	fail("Element " + by.toString() + " has been unfortunately found");
        }
    }

    public void click(By by) {
        findElement(by).click();
    }
    
    public void waitAndClick(By by, int timeInSeconds) {
        waitForElement(by, timeInSeconds);
        click(by);
    }

    public String getElementAttribute(By by, String attribute) {
        return findElement(by).getAttribute(attribute);
    }

    public boolean isAttributeValue(By by, String attribute, String value, int timeInSeconds) {
        boolean attr = false;
        int second = 0;
        while (!(attr = compareAttributeValue(by, attribute, value)) && ++second <= timeInSeconds) {
        	timer.sleepSecond();
        }
        return attr;
    }
    
    public void waitForAttributeTrue(By by, String attribute, int timeInSeconds) {
    	waitForAttributeEquals(by, attribute, ATTRIBUTE_TRUE, timeInSeconds);
    }

    public void waitForAttributeFalse(By by, String attribute, int timeInSeconds) {
    	waitForAttributeEquals(by, attribute, ATTRIBUTE_FALSE, timeInSeconds);
    }

    public void waitForAttributeEquals(By by, String attribute, String value, int timeInSeconds) {
        if (!isAttributeValue(by, attribute, value, timeInSeconds)) {
            fail("Attribute '" + attribute + "' timeout, the '" 
            		+ value 
            		+ "' value has not bean reached for " + timeInSeconds + " s");        	
        }
    }

    public void waitForAttributeNotEquals(By by, String attribute, String value, int timeInSeconds) {
        if (isAttributeValue(by, attribute, value, timeInSeconds)) {
            fail("Attribute '" + attribute + "' timeout, the '" 
            		+ value 
            		+ "' value has not bean reached for " + timeInSeconds + " s");        	
        }
    }

    public boolean compareAttributeValue(By by, String attribute, String value) {
    	return value.equalsIgnoreCase(getElementAttribute(by, attribute));
    }

    public String getElementText(By by) {
        return driver.findElement(by).getText();
    }
    
}
