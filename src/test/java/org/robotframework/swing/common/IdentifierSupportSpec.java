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


package org.robotframework.swing.common;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class IdentifierSupportSpec extends Specification<IdentifierSupport> {
    private IdentifierSupport identifierSupport;

    public void create() {
        identifierSupport = new IdentifierSupport();
    }
    
    public class HandlingIndexes {
        public void recognisesIndexes() {
            specify(identifierSupport.isIndex("0"));
            specify(!identifierSupport.isIndex("somethingElse"));
        }
        
        public void retrievesIndex() {
            specify(identifierSupport.asIndex("1"), must.equal(1));
        }
    }
    
    public class ExtractingArguments {
        public void extractsOptionalArgument() {
            specify(identifierSupport.extractIntArgument(new String[] {"2"}), must.equal(2));
            specify(identifierSupport.extractIntArgument(new String[] {"2"}, 123), must.equal(2));
        }
        
        public void extractsDefaultArgumentWhenNoArgumentProvided() {
            specify(identifierSupport.extractIntArgument(new String[0]), must.equal(1));
            specify(identifierSupport.extractIntArgument(new String[0], 123), must.equal(123));
        }
    }
}
