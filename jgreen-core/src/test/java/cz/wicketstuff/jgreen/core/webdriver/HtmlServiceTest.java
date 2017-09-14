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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Martin Strejc
 *
 */
public class HtmlServiceTest {

	private static final int NOP_SLEEP_5000 = 5000;
	private WebDriver driver;
	private HtmlService html;
	
	@Before
	public void beforeEach() {
		driver = mock(WebDriver.class);
		html = spy(new HtmlService(driver));
		doNothing().when(html).sleep(NOP_SLEEP_5000);
		doNothing().when(html).sleep(1);
	}

	@Test
	public void sleepSeconds_5() {
		html.sleepSeconds(5);
		verify(html, times(1)).sleep(NOP_SLEEP_5000);
	}
/*
	@Test
	public void waitForElement() {
		By elBy = By.id("element1"); 
		doAnswer(invocation -> mock(WebElement.class)).when(driver).findElement(elBy);
		html.waitForElement(elBy, 10);
		verify(html, times(3)).sleepSecond();;
	}
*/

}
