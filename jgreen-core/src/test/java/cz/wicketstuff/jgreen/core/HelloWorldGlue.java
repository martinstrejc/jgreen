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
package cz.wicketstuff.jgreen.core;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cz.wicketstuff.jgreen.core.webdriver.HtmlService;

import javax.inject.Inject;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HelloWorldGlue  {

	private static final Logger log = LoggerFactory.getLogger(HelloWorldGlue.class);

	@Inject
	private HtmlService html;

	private String searchValue;


	@Given("^I go to Google\\.cz page$")
	public void i_go_to_Google_cz_page() throws Exception {
        html.openUrl("https://www.google.cz/");

	}

	@Given("^I wait until it is loaded$")
	public void i_wait_until_it_is_loaded() throws Exception {
		html.waitFor(() -> html.elementPresents(By.id("hplogo")), 2);
	}

	@When("^I type \"([^\"]*)\" to search field$")
	public void i_type_to_search_field(String searchValue) throws Exception {
		html.setField(By.id("lst-ib)"), searchValue);
	}

	@When("^I click Search button$")
	public void i_click_Search_button() throws Exception {
	//	html.waitFor( () -> html.click(By.name("btnK")), 1);
		html.click(By.name("btnK"));
	}

	@Then("^I am on search results page$")
	public void i_am_on_search_results_page() throws Exception {
		html.waitFor(() -> html.elementPresents(By.id("lst-ib)")), 2);
		html.assertAttributeEquals(By.id("lst-ib)"), "value", searchValue);
	}


}
