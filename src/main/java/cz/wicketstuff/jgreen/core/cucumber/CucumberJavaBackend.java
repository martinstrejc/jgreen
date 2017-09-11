package cz.wicketstuff.jgreen.core.cucumber;

import java.util.List;

import cucumber.api.java.ObjectFactory;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Glue;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.java.JavaBackend;

/**
 * @author Martin Strejc
 *
 */
public class CucumberJavaBackend extends JavaBackend {

   // private final ObjectFactory objectFactory;
    
    public CucumberJavaBackend(ObjectFactory objectFactory,
            ClassFinder classFinder) {
        super(objectFactory, classFinder);
    }

//    public CucumberJavaBackend(ObjectFactory objectFactory) {
//        super(objectFactory);
//    }

    public CucumberJavaBackend(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    @Override
    public void loadGlue(Glue glue, List<String> gluePaths) {
        super.loadGlue(glue, gluePaths);
     //   glue.addBeforeHook(new JavaHookDefinition(method, tagExpressions, ((Before) annotation).order(), timeout, objectFactory));
        
    }

}
