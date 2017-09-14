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

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author Martin Strejc
 *
 */
public class AbstractJGreenSpringBaseGlue extends AbstractJGreenSpringBase implements ApplicationContextAware  {

	private ApplicationContext applicationContext;
	
	@Autowired
	private ApplicationContext applicationContextSpring;

	@Inject
	private ApplicationContext applicationContextJEE;

	@Autowired
	private JGreenSettings jGreenSettings;

	@When("^The glue code extends AbstractJGreenSpringBase$")
	public void the_glue_code_extends_AbstractJGreenSpringBase() throws Exception {
		assertTrue("This glue code instance is not a subclass for AbstractJGreenSpringBase", AbstractJGreenSpringBase.class.isAssignableFrom(this.getClass()));
	}
	
	@And("^The glue code implements ApplicationContextAware interface$")
	public void the_glue_code_implements_ContextAware_interface() throws Exception {
		assertTrue("This glue code instance is not a subclass for ApplicationContextAware", ApplicationContextAware.class.isAssignableFrom(this.getClass()));
	}

	@Then("^the Spring context is autowired via a setter$")
	public void the_Spring_context_is_autowired_via_a_setter() throws Exception {
		assertNotNull(applicationContext);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Then("^the Spring context is autowired into a Spring annotated field$")
	public void the_Spring_context_is_autowired_into_a_Spring_annotated_field() throws Exception {
		assertNotNull(applicationContextSpring);		
	}

	@Then("^the Spring context is autowired into a JEE annotated field$")
	public void the_Spring_context_is_autowired_into_a_JEE_annotated_field() throws Exception {
		assertNotNull(applicationContextJEE);		
	}
	
	@When("^jgreen\\.properties file overrides jgreen\\.name property to 'My Cucumber Test'$")
	public void jgreen_properties_file_overrides_jgreen_name_property_to_My_Cucumber_Test() throws Exception {
	    // this step has not implementation, check the file jgreen.property
	}

	@Then("^the text 'My Cucumber Test' is injected into the configuration bean$")
	public void the_text_My_Cucumber_Test_is_injected_into_the_configuration_bean() throws Exception {
	    assertEquals("My Cucumber Test", jGreenSettings.getName());
	}
	
}
