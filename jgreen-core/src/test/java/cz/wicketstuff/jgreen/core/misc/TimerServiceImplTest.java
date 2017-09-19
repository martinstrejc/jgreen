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

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * @author Martin Strejc
 *
 */
public class TimerServiceImplTest {
	
	@Spy
	private TimerServiceImpl timer;

	@Before
	public void beforeEach() {
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Test method for {@link cz.wicketstuff.jgreen.core.misc.TimerServiceImpl#sleepSeconds(long)}.
	 */
	@Test
	public void sleepSeconds() {
		doNothing().when(timer).threadSleep(2000);
		timer.sleepSeconds(2);
		verify(timer, times(1)).threadSleep(2000);
	}

	/**
	 * Test method for {@link cz.wicketstuff.jgreen.core.misc.TimerServiceImpl#sleepSecond()}.
	 */
	@Test
	public void sleepSecond() {
		doNothing().when(timer).threadSleep(1000);
		timer.sleepSecond();
		verify(timer, times(1)).threadSleep(1000);
	}

}
