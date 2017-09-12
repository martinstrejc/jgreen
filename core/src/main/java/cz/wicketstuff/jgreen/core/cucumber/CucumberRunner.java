package cz.wicketstuff.jgreen.core.cucumber;

import java.io.IOException;

import org.junit.runners.model.InitializationError;

import cucumber.api.junit.Cucumber;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Martin Strejc
 *
 */
public class CucumberRunner extends Cucumber {

    private static final Logger log = LoggerFactory.getLogger(CucumberRunner.class);

    /**
     * @param clazz
     * @throws InitializationError
     * @throws IOException
     */
    public CucumberRunner(Class<?> clazz) throws InitializationError, IOException {
        super(clazz);
    }
    
    @Override
    protected Runtime createRuntime(ResourceLoader resourceLoader,
            ClassLoader classLoader, RuntimeOptions runtimeOptions)
            throws InitializationError, IOException {
        log.debug("Creating cucumber runtime");
        ClassFinder classFinder = new ResourceLoaderClassFinder(resourceLoader, classLoader);
        return new Runtime(resourceLoader, classFinder, classLoader, runtimeOptions);
    }

}
