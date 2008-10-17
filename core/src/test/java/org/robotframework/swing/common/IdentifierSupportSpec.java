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
    
    public class ExtractingIdentifiers {
        public void extractsIdentifier() {
            String[] id = new String[] { "ID=someIdentifier" };
            specify(identifierSupport.extractId(id), must.equal("someIdentifier"));
        }
        
        public void extractsIdentifierFromAnyIndex() {
            String[] id = new String[] { "", "", "ID=someIdentifier" };
            specify(identifierSupport.extractId(id), must.equal("someIdentifier"));
        }
        
        public void returnsDefaultIdentifierWhenNoIdentiferFound() {
            String[] id = new String[0];
            specify(identifierSupport.extractId(id), must.equal("0"));
        }
    }
}
