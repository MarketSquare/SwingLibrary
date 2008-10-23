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


package org.robotframework.swing.keyword.tree;

import org.jmock.Expectations;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.tree.EnhancedTreeOperator;
import org.robotframework.swing.tree.TreeSupport;

public abstract class TreeSpecification<T extends TreeSupport> extends MockSupportSpecification<T> {
    protected T treeKeywords;
    protected EnhancedTreeOperator treeOperator;
    protected String treeIdentifier = "someTree";

    protected T populateWithMockOperatingFactoryAndContextVerifier(T treeKeywords) {
        this.treeKeywords = treeKeywords;
        treeOperator = mock(EnhancedTreeOperator.class);
        
        injectMockOperatorFactory();
        injectMockContextVerifier();
        
        return treeKeywords;
    }

    protected void injectMockContextVerifier() {
        final IContextVerifier contextVerifier = injectMockTo(treeKeywords, IContextVerifier.class);
        checking(new Expectations() {{
            one(contextVerifier).verifyContext();
        }});
    }

    protected void injectMockOperatorFactory() {
        final OperatorFactory operatorFactory = injectMockTo(treeKeywords, OperatorFactory.class);
        checking(new Expectations() {{
            one(operatorFactory).createOperator(treeIdentifier);
            will(returnValue(treeOperator));
        }});
    }
}
