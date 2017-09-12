package cz.wicketstuff.jgreen.core.cucumber;

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
