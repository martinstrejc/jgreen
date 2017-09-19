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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.function.BooleanSupplier;

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

	// TODO missing test
	public void openUrl(String url) {
		driver.get(url);
	}

	/**
	 * Shortcut method to {@link WebDriver#findElement(By)}.
	 * The element can be specified by id, xpath, etc.
	 * 
	 * @param by the element identifier.
	 * @return the specified element
     * @throws NoSuchElementException when the element hasn't been found
	 */
	public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public boolean elementPresents(By by) {
        try {
            findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            log.trace("Element hasn't been found", e);
            return false;
        }
    }

    /**
     * Shortcut method to {@link WebElement#getText()}, 
     * where the element is found using <strong>by</strong> argument.
     * 
     * @param by the identified on the {@link WebElement} element
     * @return text of the element
     * @throws NoSuchElementException when the element hasn't been found
     */
    public String getElementText(By by) {
        return driver.findElement(by).getText();
    }


    public void click(By by) {
        findElement(by).click();
    }
    
    public void waitAndClick(By by, int timeInSeconds) {
        waitFor(() -> elementPresents(by), timeInSeconds);
        click(by);
    }

    public String getElementAttribute(By by, String attribute) {
        return findElement(by).getAttribute(attribute);
    }

    
    public boolean attributeEqualsIgnoreCase(By by, String attribute, String value) {
    	return value.equalsIgnoreCase(getElementAttribute(by, attribute));
    }

    public boolean attributeTrue(By by, String attribute) {
    	return attributeEqualsIgnoreCase(by, attribute, ATTRIBUTE_TRUE);
    }

    public boolean attributeFalse(By by, String attribute) {
    	return attributeEqualsIgnoreCase(by, attribute, ATTRIBUTE_FALSE);
    }

    public void waitFor(final BooleanSupplier condition, int timeInSeconds) {
        if (!timeoutCondition(condition, timeInSeconds)) {
            fail("Timeout");        	
        }
    }
    
    public void waitForNot(final BooleanSupplier condition, int timeInSeconds) {
        if (timeoutCondition(condition, timeInSeconds)) {
            fail("Timeout");        	
        }
    }

    /**
     * Check the condition within the specified timeout. Returns the condition until
     * the timeout is reached, then the condition is considered as <code>false</code>.
     * 
     * @param condition evaluated condition
     * @param timeInSeconds timeout in seconds when condition is evaluated
     * @return the evaluated condition or <code>false</code> it is timeouted
     */
    public boolean timeoutCondition(final BooleanSupplier condition, int timeInSeconds) {
        boolean ret = false;
        int second = 0;
        while (!(ret = condition.getAsBoolean()) && ++second <= timeInSeconds) {
        	timer.sleepSecond();
        }
        return ret;
    }
    
    // TODO tests
    public void assertDisplayed(By by) {
        assertDisplayed(findElement(by));
    }

    // TODO tests
    public void assertNotDisplayed(By by) {
        try {
            assertNotDisplayed(findElement(by));
        } catch (NoSuchElementException e) {
            log.trace("assertNotDisplayed: Element hasn't been found", e);
        }
    }

    // TODO tests
    public void assertDisplayed(WebElement webElement) {
        if (!webElement.isDisplayed()) {
            fail(webElement + " element should be displayed.");
        }
    }

    // TODO tests
    public void assertNotDisplayed(WebElement webElement) {
        if (webElement.isDisplayed()) {
            fail(webElement + " element should not be displayed.");
        }
    }

    /*
    Text verification
     */

    // TODO tests
    public void assertElementText(By by, String expectedText){
        assertEquals(expectedText, findElement(by).getText());
    }

    // TODO tests
    public void assertElementTextContains(By by, String containedText){
    	assertThat(getElementText(by), containsString(containedText));
    }

    /*
    Attribute actions
    */

    // TODO tests
	public void assertAttributeEquals(By by, String attribute, String expectedValue) {
		assertEquals(expectedValue, getElementAttribute(by, attribute));
	}

    // TODO tests
	public void assertAttributeNotEquals(By by, String attribute, String expectedValue) {
		assertNotEquals(expectedValue, getElementAttribute(by, attribute));
	}

    // TODO tests
	// TODO check if true/false is really a good solution or rather create an own junit regex matcher
	// https://code.google.com/archive/p/hamcrest-text-patterns/
	// https://piotrga.wordpress.com/2009/03/27/hamcrest-regex-matcher/
	public void assertAttributeMatches(By by, String attribute, String regex) { 
		assertTrue(getElementAttribute(by, attribute).matches(regex));
	}

    // TODO tests
	public boolean attributeContains(By by, String attribute, String expectedValue) {
		return getElementAttribute(by, attribute).contains(expectedValue);
	}

    // TODO tests
	public boolean attributePresents(By by, String attribute) {
		return getElementAttribute(by, attribute) != null;
	}

	
    // TODO tests
	public void assertAttributePresent(By by, String attribute) {
		assertEquals(true, attributePresents(by, attribute));
	}

    // TODO tests
	public void assertAttributeNotPresent(By by, String attribute) {
		assertEquals(false, attributePresents(by, attribute));
	}

}
