/*
 * Copyright 2008 Nokia Siemens Networks Oyj
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

package org.robotframework.swing.common;


public class IdentifierSupport {
    public final static String REGEXP_IDENTIFIER_PREFIX = "regexp=";

    public int asIndex(String identifier) {
        return Integer.parseInt(identifier);
    }

    public boolean isIndex(String identifier) {
        try {
            Integer.parseInt(identifier);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public int extractIntArgument(String[] args) {
        return extractIntArgument(args, 1);
    }
    
    public int extractIntArgument(String[] args, int defaultValue) {
        if (args.length == 0) {
            return defaultValue;
        } else {
            return Integer.parseInt(args[0]);
        }
    }
}
