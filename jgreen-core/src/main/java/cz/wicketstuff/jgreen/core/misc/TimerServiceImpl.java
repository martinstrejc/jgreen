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
package cz.wicketstuff.jgreen.core.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Martin Strejc
 *
 */
public class TimerServiceImpl implements TimerService {
	
	private static final Logger log = LoggerFactory.getLogger(TimerServiceImpl.class);

	@Override
	public void sleepSecond() {
		sleepSeconds(1);
	}

	@Override
	public void sleepSeconds(long seconds) {
		threadSleep(1000 * seconds);
	}

    protected void threadSleep(long timeInMillis) {
    	try {
			Thread.sleep(timeInMillis);
		} catch (InterruptedException e) {
			log.error("The thread " + Thread.currentThread().getName() + " has been interrupted while sleeping, zZZ");
			Thread.currentThread().interrupt();
		}
    }

	
	
}
