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
package cz.wicketstuff.jgreen.core.cucumber;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.BooleanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cz.wicketstuff.jgreen.core.ScreenshotProvider;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static cz.wicketstuff.jgreen.core.cucumber.AnnotationExtractor.*;

/**
 * @author Martin Strejc
 *
 */
@Aspect
public class CucumberLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(CucumberLoggingAspect.class);
    
    public static final Pattern FNAME_RESTRICTION_PATTERN = Pattern.compile("([^\\w;])+");
    
    @Autowired
    private CucumberSettings settings;

    @Around("@annotation(cucumber.api.java.Before)")
    public Object aroundBefore(ProceedingJoinPoint joinPoint) throws Throwable 
    {
        return loggingAround(joinPoint, Before.class, extractBeforeValue(method(joinPoint)));
    }

    @Around("@annotation(cucumber.api.java.After)")
    public Object aroundAfter(ProceedingJoinPoint joinPoint) throws Throwable 
    {
        return loggingAround(joinPoint, After.class, extractAfterValue(method(joinPoint)));
    }

    @Around("@annotation(cucumber.api.java.en.Given)")
    public Object aroundGiven(ProceedingJoinPoint joinPoint) throws Throwable 
    {
        
        return loggingAround(joinPoint, Given.class, extractGivenValue(method(joinPoint)));
    }

    @Around("@annotation(cucumber.api.java.en.When)")
    public Object aroundWhen(ProceedingJoinPoint joinPoint) throws Throwable 
    {
        return loggingAround(joinPoint, When.class, extractWhenValue(method(joinPoint)));
    }

    @Around("@annotation(cucumber.api.java.en.Then)")
    public Object aroundThen(ProceedingJoinPoint joinPoint) throws Throwable 
    {
        return loggingAround(joinPoint, Then.class, extractThenValue(method(joinPoint)));
    }

    @Around("@annotation(cucumber.api.java.en.And)")
    public Object aroundAnd(ProceedingJoinPoint joinPoint) throws Throwable 
    {
        return loggingAround(joinPoint, And.class, extractAndValue(method(joinPoint)));
    }

    @Around("@annotation(cucumber.api.java.en.But)")
    public Object aroundBut(ProceedingJoinPoint joinPoint) throws Throwable 
    {
        return loggingAround(joinPoint, But.class, extractButValue(method(joinPoint)));
    }

    protected Object loggingAround(ProceedingJoinPoint joinPoint, Class<?> methodAnnotation, String annotationValue) throws Throwable 
    {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Object target = joinPoint.getThis();
        String scenarioName = extractScenarioId(target);
        String interceptedMethod = signature.toLongString();

        log.debug("JoinPoint intercepted at {} for scenario {}, annotated by @{}(\"{}\")", interceptedMethod, scenarioName, methodAnnotation.getName(), annotationValue);
        
        if ((methodAnnotation == Before.class || methodAnnotation == After.class) && (!signature.getMethod().isAnnotationPresent(InternalCucumberBeforeAfter.class))) {
//            throw new IllegalStateException("Annotations @Before and @After are not allowed, use @CucumberBefore or @CucumberAfter instead for method " + interceptedMethod);
        }
        
        try
        {
            Object ret = joinPoint.proceed();
            log.debug("Intercepted method processed and returns {}", ret);
            if (BooleanUtils.isTrue(settings.getScreenshotOnEachStep())) {
                makeScreenshot(extractScreenshotProvider(target), createScreenshotPath(scenarioName, signature.getMethod().getName()), interceptedMethod);
            }
            return ret;
        }
        catch (Throwable t)
        {
            log.error("Caught an exception or error at " + interceptedMethod + " for scenario " + scenarioName, t);
            if (BooleanUtils.isTrue(settings.getScreenshotOnEachStep()) || BooleanUtils.isTrue(settings.getScreenshotOnException())) {
                makeScreenshot(extractScreenshotProvider(target), createScreenshotPath(scenarioName, signature.getMethod().getName()), interceptedMethod);
            }
            throw t;
        }
        finally
        {
            log.debug("JoinPoint finnaly statement at {} for scenario {}, annotated by @{}(\"{}\")", interceptedMethod, scenarioName, methodAnnotation.getName(), annotationValue);
        }        
    }
    
    protected void makeScreenshot(ScreenshotProvider screenshotProvider, Path filePath, String interceptedMethod) {
        if (screenshotProvider == null) {
            log.error("Cannot make screenshot to {} because of missing screenshotProvider at intercepted method {}", filePath, interceptedMethod);
            return;
        }
        log.info("Saving screenshot to {}", filePath);
        try {
            FileUtils.writeByteArrayToFile(filePath.toFile(), screenshotProvider.takeScreenshot());
        } catch (Exception e) {
            log.error("Cannot take screenshot into " + filePath, e);
        }
    }
    
    protected String extractScenarioId(Object target) {
        if (target instanceof ExecutedScenario) {
            Scenario scenario = ((ExecutedScenario)target).getExecutedScenario();
            return scenario == null ? null : scenario.getId();            
        }
        return null;
    }
    
    protected ScreenshotProvider extractScreenshotProvider(Object target) {
        return target instanceof ScreenshotProvider ?
                (ScreenshotProvider) target : null;
    }
    
    protected Path createScreenshotPath(String scenarioName, String methodName) {
        StringBuilder fileName = new StringBuilder();
        fileName.append("cucuscreen");
        fileName.append("-");
        fileName.append(newTime());
        if (scenarioName != null) {
            fileName.append("_");            
            fileName.append(FNAME_RESTRICTION_PATTERN.matcher(scenarioName).replaceAll("_"));            
        }
        fileName.append(";");
        fileName.append(methodName);
        fileName.append(".png");
        return Paths.get(settings.getScreenshotsPath(), fileName.toString());
    }
    
    protected long newTime() {
        return new Date().getTime();
    }
    
    private static Method method(JoinPoint joinPoint) {
        return ((MethodSignature)joinPoint.getSignature()).getMethod();
    }

}
