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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
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
public class HtmlServiceTest {

	private static final Logger log = LoggerFactory.getLogger(HtmlServiceTest.class);
	
	
	private static final NoSuchElementException NO_SUCH_ELEMENT_EX = new NoSuchElementException("element1");

	int inv = 0;
	
	@Mock
	private WebDriver driver;

	@Mock
	private TimerService timer;

	@Spy
	@InjectMocks
	private HtmlService html;

	@Mock
	private WebElement EL;
	
	private By EL_BY;
	
	@Before
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
		inv = 0;
		EL_BY = spy(By.id("element1"));
	}
	
	// isElementPresent
	
	@Test
	public void isElementPresent_exists() {
		doReturn(mock(WebElement.class)).when(driver).findElement(EL_BY);
		assertTrue(html.isElementPresent(EL_BY));
	}

	@Test
	public void isElementPresent_missing() {
		doThrow(new NoSuchElementException("element1")).when(driver).findElement(EL_BY);
		assertFalse(html.isElementPresent(EL_BY));
	}

	// waitForElement
	
	@Test
	public void waitForElement_found() {
		doAnswer(invocation -> {if (++inv > 2 ) {return EL;} else {throw NO_SUCH_ELEMENT_EX;}}).when(driver).findElement(EL_BY);
		html.waitForElement(EL_BY, 5);
		verify(timer, times(2)).sleepSecond();
	}
	
	@Test(expected = AssertionError.class)
	public void waitForElement_notfound() {
		doAnswer(invocation -> {throw NO_SUCH_ELEMENT_EX;}).when(driver).findElement(EL_BY);
		html.waitForElement(EL_BY, 5);
	}

	@Test
	public void waitForElement_max_timeout() {
		doAnswer(invocation -> {if (++inv > 5 ) {return EL;} else {throw NO_SUCH_ELEMENT_EX;}}).when(driver).findElement(EL_BY);
		html.waitForElement(EL_BY, 5);
		verify(timer, times(5)).sleepSecond();
	}

	@Test(expected = AssertionError.class)
	public void waitForElement_max_timeout_over_border() {
		doAnswer(invocation -> {if (++inv > 6 ) {return EL;} else {throw NO_SUCH_ELEMENT_EX;}}).when(driver).findElement(EL_BY);
		html.waitForElement(EL_BY, 5);
	}

	
	// waitForElementNotPresent

	@Test(expected = AssertionError.class)
	public void waitForElementNotPresent_found() {
		doAnswer(invocation -> {if (++inv > 2 ) {return EL;} else {throw NO_SUCH_ELEMENT_EX;}}).when(driver).findElement(EL_BY);
		html.waitForElementNotPresent(EL_BY, 5);
	}

	@Test
	public void waitForElementNotPresent_notfound() {
		doAnswer(invocation -> {throw NO_SUCH_ELEMENT_EX;}).when(driver).findElement(EL_BY);
		html.waitForElementNotPresent(EL_BY, 5);
	}

	@Test(expected = AssertionError.class)
	public void waitForElementNotPresent_max_timeout() {
		doAnswer(invocation -> {if (++inv > 5 ) {return EL;} else {throw NO_SUCH_ELEMENT_EX;}}).when(driver).findElement(EL_BY);
		html.waitForElementNotPresent(EL_BY, 5);
		verify(timer, times(5)).sleepSecond();
	}

	@Test
	public void waitForElementNotPresent_max_timeout_over_border() {
		doAnswer(invocation -> {if (++inv > 6 ) {return EL;} else {throw NO_SUCH_ELEMENT_EX;}}).when(driver).findElement(EL_BY);
		html.waitForElementNotPresent(EL_BY, 5);
	}

	// waitAndClick
	@Test
	public void waitAndClick() {
		doAnswer(invocation -> {if (++inv > 2 ) {return EL;} else {throw NO_SUCH_ELEMENT_EX;}}).when(driver).findElement(EL_BY);
		doNothing().when(EL).click();
		html.waitAndClick(EL_BY, 5);
		verify(timer, times(2)).sleepSecond();
		verify(EL, times(1)).click();
	}
	
	// compareAttributeValue
	
	@Test
	public void compareAttributeValue_true() {
		doReturn(EL).when(driver).findElement(EL_BY);
		doReturn("true").when(EL).getAttribute("attr1");
		assertTrue(html.compareAttributeValue(EL_BY, "attr1", "true"));
	}

	@Test
	public void compareAttributeValue_tRUe_case_sensitive() {
		doReturn(EL).when(driver).findElement(EL_BY);
		doReturn("tRUe").when(EL).getAttribute("attr1");
		assertTrue(html.compareAttributeValue(EL_BY, "attr1", "true"));
	}

	@Test
	public void compareAttributeValue_false() {
		doReturn(EL).when(driver).findElement(EL_BY);
		doReturn("false").when(EL).getAttribute("attr1");
		assertFalse(html.compareAttributeValue(EL_BY, "attr1", "true"));
	}

	@Test
	public void compareAttributeValue_FALSE_case_sensitive() {
		doReturn(EL).when(driver).findElement(EL_BY);
		doReturn("FALSE").when(EL).getAttribute("attr1");
		assertFalse(html.compareAttributeValue(EL_BY, "attr1", "true"));
	}

	@Test
	public void compareAttributeValue_empty() {
		doReturn(EL).when(driver).findElement(EL_BY);
		doReturn("").when(EL).getAttribute("attr1");
		assertFalse(html.compareAttributeValue(EL_BY, "attr1", "true"));
	}

	@Test
	public void compareAttributeValue_missing_attribute() {
		doReturn(EL).when(driver).findElement(EL_BY);
		assertFalse(html.compareAttributeValue(EL_BY, "attr1", "true"));
	}
	
	// waitForAttributeTrue

	@Test
	public void waitForAttributeTrue_found() {
		doAnswer(invocation -> ++inv > 2 ? "true" : null).when(EL).getAttribute("attr1");
		doReturn(EL).when(driver).findElement(EL_BY);
		html.waitForAttributeTrue(EL_BY, "attr1", 5);
		verify(timer, times(2)).sleepSecond();
	}

	@Test(expected = AssertionError.class)
	public void waitForAttributeTrue_timeout() {
		doAnswer(invocation -> ++inv > 6 ? "true" : null).when(EL).getAttribute("attr1");
		doReturn(EL).when(driver).findElement(EL_BY);
		html.waitForAttributeTrue(EL_BY, "attr1", 5);
	}

	@Test
	public void waitForAttributeTrue_border() {
		doAnswer(invocation -> ++inv > 5 ? "true" : null).when(EL).getAttribute("attr1");
		doReturn(EL).when(driver).findElement(EL_BY);
		html.waitForAttributeTrue(EL_BY, "attr1", 5);
	}

	@Test(expected = AssertionError.class)
	public void waitForAttributeTrue_false() {
		doReturn("false").when(EL).getAttribute("attr1");
		doReturn(EL).when(driver).findElement(EL_BY);
		html.waitForAttributeTrue(EL_BY, "attr1", 5);
		verify(timer, times(5)).sleepSecond();
	}

	
	// waitForAttributeFalse

	@Test
	public void waitForAttributeFalse_found() {
		doAnswer(invocation -> ++inv > 2 ? "false" : null).when(EL).getAttribute("attr1");
		doReturn(EL).when(driver).findElement(EL_BY);
		html.waitForAttributeFalse(EL_BY, "attr1", 5);
		verify(timer, times(2)).sleepSecond();
	}

	@Test(expected = AssertionError.class)
	public void waitForAttributeFalse_timeout() {
		doAnswer(invocation -> ++inv > 6 ? "false" : null).when(EL).getAttribute("attr1");
		doReturn(EL).when(driver).findElement(EL_BY);
		html.waitForAttributeFalse(EL_BY, "attr1", 5);
	}

	@Test
	public void waitForAttributeFalse_border() {
		doAnswer(invocation -> ++inv > 5 ? "false" : null).when(EL).getAttribute("attr1");
		doReturn(EL).when(driver).findElement(EL_BY);
		html.waitForAttributeFalse(EL_BY, "attr1", 5);
	}

	@Test(expected = AssertionError.class)
	public void waitForAttributeFalse_true() {
		doReturn("true").when(EL).getAttribute("attr1");
		doReturn(EL).when(driver).findElement(EL_BY);
		html.waitForAttributeFalse(EL_BY, "attr1", 5);
		verify(timer, times(5)).sleepSecond();
	}

	
	// waitForAttributeNotEquals

	@Test(expected = AssertionError.class)
	public void waitForAttributeNotEquals_found() {
		doAnswer(invocation -> ++inv > 2 ? "false" : null).when(EL).getAttribute("attr1");
		doReturn(EL).when(driver).findElement(EL_BY);
		html.waitForAttributeNotEquals(EL_BY, "attr1", "true", 5);
		verify(timer, times(2)).sleepSecond();
	}

	@Test
	public void waitForAttributeNotEquals_timeout() {
		doAnswer(invocation -> ++inv > 6 ? "false" : null).when(EL).getAttribute("attr1");
		doReturn(EL).when(driver).findElement(EL_BY);
		html.waitForAttributeNotEquals(EL_BY, "attr1", "true", 5);
	}

	@Test
	public void waitForAttributeNotEquals_timeout2() {
		doAnswer(invocation -> ++inv > 6 ? "true" : null).when(EL).getAttribute("attr1");
		doReturn(EL).when(driver).findElement(EL_BY);
		html.waitForAttributeNotEquals(EL_BY, "attr1", "true", 5);
	}

	@Test
	public void waitForAttributeNotEquals_border() {
		doAnswer(invocation -> ++inv > 5 ? "false" : null).when(EL).getAttribute("attr1");
		doReturn(EL).when(driver).findElement(EL_BY);
		html.waitForAttributeNotEquals(EL_BY, "attr1", "true", 5);
	}

	@Test(expected = AssertionError.class)
	public void waitForAttributeNotEquals_true() {
		doReturn("true").when(EL).getAttribute("attr1");
		doReturn(EL).when(driver).findElement(EL_BY);
		html.waitForAttributeNotEquals(EL_BY, "attr1", "true", 5);
		verify(timer, times(5)).sleepSecond();
	}

}
