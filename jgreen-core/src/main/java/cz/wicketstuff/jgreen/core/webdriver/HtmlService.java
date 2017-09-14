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

/**
 * @author Martin Strejc
 *
 */
public class HtmlService {
	
	private static final Logger log = LoggerFactory.getLogger(HtmlService.class);
	
	protected WebDriver driver;

	public HtmlService(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public boolean isElementPresent(By by) {
        try {
            findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            log.debug("Element hasn't been found", e);
            return false;
        }
    }

    public void waitForElement(By by, long timeInSeconds) {
        for (int second = 0; second < timeInSeconds; second++) {
            if (isElementPresent(by)) {
                return;
            }
            sleepSecond();
        }
        fail("Timeout message.....");
    }
    
    protected void sleepSecond() {
    	sleepSeconds(1);
    }

    protected void sleepSeconds(int seconds) {
        sleep(seconds * 1000L);
    }

    protected void sleep(long timeInMillis) {
    	try {
			Thread.sleep(timeInMillis);
		} catch (InterruptedException e) {
			log.error("The thread " + Thread.currentThread().getName() + " has been interrupted while sleeping, zZZ");
			Thread.currentThread().interrupt();
		}
    }

}
