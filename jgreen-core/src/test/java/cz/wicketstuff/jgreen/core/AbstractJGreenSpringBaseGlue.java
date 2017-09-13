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
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author Martin Strejc
 *
 */
@ContextConfiguration(classes={JGreenContextConfiguration.class})
public class AbstractJGreenSpringBaseGlue extends AbstractJGreenSpringBase implements ApplicationContextAware  {

	private ApplicationContext applicationContext;
	
	@When("^The glue code extends AbstractJGreenSpringBase$")
	public void the_glue_code_extends_AbstractJGreenSpringBase() throws Exception {
		assertTrue("This glue code instance is not a subclass for AbstractJGreenSpringBase", AbstractJGreenSpringBase.class.isAssignableFrom(this.getClass()));
	}
	
	@And("^The glue code implements ApplicationContextAware interface$")
	public void the_glue_code_implements_ContextAware_interface() throws Exception {
		assertTrue("This glue code instance is not a subclass for ApplicationContextAware", ApplicationContextAware.class.isAssignableFrom(this.getClass()));
	}

	@Then("^the Spring context is autowired$")
	public void the_Spring_context_is_autowired() throws Exception {
		assertNotNull(applicationContext);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
