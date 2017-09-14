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
import static org.hamcrest.CoreMatchers.*;

import java.io.StringWriter;

import javax.inject.Inject;

import org.apache.log4j.Appender;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger log = LoggerFactory.getLogger(AbstractJGreenSpringBaseGlue.class);
	
	private ApplicationContext applicationContext;
	
	@Autowired
	private ApplicationContext applicationContextSpring;

	@Inject
	private ApplicationContext applicationContextJEE;

	@Autowired
	private JGreenSettings jGreenSettings;
	
	private Appender appender;
	private StringWriter logWriter = new StringWriter();

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

	@When("^a message is logged via slf4j$")
	public void a_message_is_logged_via_slf4j() throws Exception {
		logWriter = new StringWriter();
		appender = new WriterAppender(new SimpleLayout(), logWriter);
		org.apache.log4j.Logger.getRootLogger().addAppender(appender);
		log.info("a message is really logged via slf4j");
	}

	@Then("^the message appears in the log4j log$")
	public void the_message_appears_in_the_log4j_log() throws Exception {
		org.apache.log4j.Logger.getRootLogger().removeAppender(appender);
		assertThat(logWriter.toString(), containsString("a message is really logged via slf4j"));
	}

}
