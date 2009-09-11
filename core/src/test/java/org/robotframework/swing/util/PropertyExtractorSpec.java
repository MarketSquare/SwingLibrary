package org.robotframework.swing.util;

import java.util.HashMap;
import java.util.Map;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class PropertyExtractorSpec extends Specification<Void> {
    public class Any {
        public void extractsProperties() {
            Object bean = new MyBean();
            Map<String, Object> expectedProperties = new HashMap<String, Object>() {{
                put("something", "someValue");
                put("otherProp", "otherValue");
            }};
            
            
            PropertyExtractor extractor = new PropertyExtractor();
            specify(extractor.extractProperties(bean), expectedProperties);
        }
    }
}

class MyBean {
    public String getSomething() {
        return "someValue";
    }
    
    public String getSomethingElse(int arg) {
        return "somethingElse";
    }
    
    private String getSomethingPrivate() {
        return "somethingPrivate";
    }
    
    public void setFoo() {}
    
    public Object getOtherProp() {
        return "otherValue";
    }
    
    public boolean isTrue() {
        return true;
    }
}