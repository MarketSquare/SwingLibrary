/*
 * Copyright 2008-2011 Nokia Siemens Networks Oyj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.robotframework.swing.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PropertyExtractor {
    public Map<String, Object> extractProperties(Object bean) {
        try {
            return parsePropertyValuesFrom(bean);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Object> parsePropertyValuesFrom(Object bean) throws Exception {
        Map<String, Object> properties = new HashMap<String, Object>();
        for (Method method : bean.getClass().getMethods()) {
            if (isGetter(method)) {
                Object value = invoke(bean, method);
                String prop = parsePropNameFrom(method);
                properties.put(prop, value);
            }
        }        
        return properties;
    }

    private boolean isGetter(Method m) {
        String methodName = m.getName();
        return methodName.startsWith("get") && 
               m.getParameterTypes().length == 0 && 
               !methodName.equals("getClass");
    }
    
    private Object invoke(Object bean, Method method) {
        try {
            return method.invoke(bean, new Object[0]);
        } catch (Exception ignored) {
            return null;
        }
    }
    
    private String parsePropNameFrom(Method method) {
        return Character.toLowerCase(method.getName().charAt(3)) + method.getName().substring(4);
    }
}
