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
 */package cz.wicketstuff.jgreen.core.cucumber;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;

/**
 * @author Martin Strejc
 *
 */
public final class AnnotationExtractor {
    
    private AnnotationExtractor() {
        // no instance, util class
    }

    public static String extractBeforeValue(Method method) {
        Before ann = method.getAnnotation(Before.class);
        return ann == null ? null : StringUtils.join(ann.value(), ",");
    }

    public static String extractAfterValue(Method method) {
        After ann = method.getAnnotation(After.class);
        return ann == null ? null : StringUtils.join(ann.value(), ",");
    }

    public static String extractGivenValue(Method method) {
        Given ann = method.getAnnotation(Given.class);
        return ann == null ? null : ann.value();
    }

    public static String extractWhenValue(Method method) {
        When ann = method.getAnnotation(When.class);
        return ann == null ? null : ann.value();
    }

    public static String extractThenValue(Method method) {
        Then ann = method.getAnnotation(Then.class);
        return ann == null ? null : ann.value();
    }

    public static String extractButValue(Method method) {
        But ann = method.getAnnotation(But.class);
        return ann == null ? null : ann.value();
    }

    public static String extractAndValue(Method method) {
        And ann = method.getAnnotation(And.class);
        return ann == null ? null : ann.value();
    }

}
