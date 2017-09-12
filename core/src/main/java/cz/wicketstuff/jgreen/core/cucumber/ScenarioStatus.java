package cz.wicketstuff.jgreen.core.cucumber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Martin Strejc
 *
 */
public enum ScenarioStatus {

    PASSED,
    UNDEFINED,
    PENDING,
    SKIPPED, 
    FAILED
    ;

    private static final Logger log = LoggerFactory.getLogger(ScenarioStatus.class);

    public static boolean isPassed(String status) {
        return matchesStatus(PASSED, status);
    }

    public static boolean isUndefined(String status) {
        return matchesStatus(UNDEFINED, status);        
    }

    public static boolean isPending(String status) {
        return matchesStatus(PENDING, status);        
    }

    public static boolean isSkipped(String status) {
        return matchesStatus(SKIPPED, status);        
    }

    public static boolean isFailed(String status) {
        return matchesStatus(FAILED, status);
    }
    
    public static boolean matchesStatus(ScenarioStatus matchingStatus, String status) {
        if (matchingStatus == null) {
            throw new IllegalArgumentException("matchingStatus cannot be null");
        }
        return status == null ? false : matchingStatus.name().equalsIgnoreCase(status);
    }
    
    public static ScenarioStatus valueOfIgnoreCase(String value) {
        return value == null ? null : valueOf(value.toUpperCase());
    }

    public static ScenarioStatus valueOfIgnoreCaseAny(String value) {
        if (value == null) 
        {
            return null;
        }
        try {
            return valueOf(value.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            log.debug("Convert unknown value to NULL because of exception.", e);
            return null;
        }
    }

}
