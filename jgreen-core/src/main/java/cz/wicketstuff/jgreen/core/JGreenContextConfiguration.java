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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import cz.wicketstuff.jgreen.core.misc.TimerService;
import cz.wicketstuff.jgreen.core.misc.TimerServiceImpl;
import cz.wicketstuff.jgreen.core.webdriver.HtmlService;

/**
 * @author Martin Strejc
 *
 */
@Configuration
@PropertySource(name="jgreen", value = {
        "classpath:cz/wicketstuff/jgreen/core/jgreen-default.properties", 
        "classpath:jgreen.properties", 
        "classpath:jgreen-${spring.profiles.active}.properties"
        }, ignoreResourceNotFound = true)
@EnableConfigurationProperties(JGreenSettings.class)
public class JGreenContextConfiguration implements ApplicationListener<ApplicationEvent> {
	
	private static final Logger log = LoggerFactory.getLogger(JGreenContextConfiguration.class);
	
	@Autowired
	private JGreenSettings jGreenSettings;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
            // ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
            log.info(jGreenSettings.getName() + " has been initialized");
        }
		
	}
	
	@Bean
	public TimerService timer() {
		return new TimerServiceImpl();
	}
	
	@Bean
	public HtmlService html(TimerService timer) {
		// TODO add webdriver here
		return new HtmlService(null, timer);
	}
    
    

}
