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
package cz.wicketstuff.jgreen.it;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cz.wicketstuff.jgreen.core.webdriver.HtmlService;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;


public class PetClinicGlue {

	private static final Logger log = LoggerFactory.getLogger(PetClinicGlue.class);

	private static final String HOME_URL = "http://localhost:8080/";

	private static final int defTime = 2;

	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String phone;

	private JavascriptExecutor js;

	@Inject
	private HtmlService html;

	@Given("^I open Pet Clinic$")
	public void iOpenPetClinic() throws Throwable {
		html.openUrl(HOME_URL);
		html.waitFor(() -> html.elementPresents(By.className("img-responsive")), defTime);
		log.info("Pet Clinic homepage is opened");
	}

	@And("^I go to Find Owner page$")
	public void iGoToFindOwnerPage() throws Throwable {
		html.waitFor(() -> html.elementPresents(By.linkText("FIND OWNERS")), defTime);
		html.waitAndClick(By.linkText("FIND OWNERS"), defTime);
		html.waitFor(() -> html.elementPresents(By.id("lastName")), defTime);
		log.info("Find Owner page is displayed");
	}

	@When("^I press Add Owner button$")
	public void iPressAddOwnerButton() throws Throwable {
		html.waitAndClick(By.linkText("Add Owner"), defTime);
	}

	@Then("^I am on Owner form$")
	public void iAmOnOwnerForm() throws Throwable {
		html.waitFor(() -> html.elementPresents(By.id("firstName")), defTime);
		log.info("Owners form is displayed");

	}

	@Given("^I go to Owner form$")
	public void iGoToOwnerForm() throws Throwable {
		html.openUrl(HOME_URL+"/owners/new");
		html.waitFor(() -> html.elementPresents(By.id("firstName")), defTime);
		log.info("Owners form is displayed");
	}

	@When("^I fill in the form with random information$")
	public void iFillInTheFormWithRandomInformation() throws Throwable {
		firstName = RandomStringUtils.randomAlphabetic(6);
		lastName = RandomStringUtils.randomAlphabetic(6);
		address = RandomStringUtils.randomAlphabetic(10);
		city = RandomStringUtils.randomAlphabetic(5);
		phone = RandomStringUtils.randomNumeric(7);

		html.setField(By.id("firstName"), firstName );
		html.setField(By.id("lastName"), lastName );
		html.setField(By.id("address"), address );
		html.setField(By.id("city"), city );
		html.setField(By.id("telephone"), phone );

		log.info("First name is: {}", firstName);
		log.info("Last name is: {}", lastName);
		log.info("Address is: {}", address);
		log.info("City name is: {}", city);
		log.info("Phone is: {}", phone);

	}

	@And("^I press Add Owner$")
	public void iPressAddOwner() throws Throwable {
		html.waitAndClick(By.cssSelector("button.btn.btn-default"), defTime);

	}

	@Then("^I am on Owner Information page$")
	public void iAmOnOwnerInformationPage() throws Throwable {
		html.waitFor(() -> html.elementPresents(By.cssSelector("div.container.xd-container")), defTime);
		log.info("Owner Information page opened");
	}

	@And("^Information is the same as I added$")
	public void informationIsTheSameAsIAdded() throws Throwable {
		html.verifyTextForElement(By.xpath("//div/div/table[1]/tbody/tr[1]/td/b"), firstName+" "+lastName);
		html.verifyTextForElement(By.xpath("//div/div/table[1]/tbody/tr[2]/td"), address);
		html.verifyTextForElement(By.xpath("//div/div/table[1]/tbody/tr[3]/td"), city);
		html.verifyTextForElement(By.xpath("//div/div/table[1]/tbody/tr[4]/td"), phone);
		log.info("Owner Information is correct");

	}

	@And("^Fill in Owners last name and press Find Owner$")
	public void fillInOwnersLastNameAndPressFindOwner() throws Throwable {
		html.sendKeys(By.xpath("//div/div/form/div[1]/div/div/input"), lastName );
		html.waitAndClick(By.cssSelector("button.btn.btn-default"), defTime);
	}


	@When("^I open Find Owner page$")
	public void iOpenFindOwnerPage() throws Throwable {
		html.openUrl(HOME_URL+"/owners/find");
		html.waitFor(() -> html.elementPresents(By.id("lastName")), defTime);
		log.info("Find Owner page is displayed");

	}
}
