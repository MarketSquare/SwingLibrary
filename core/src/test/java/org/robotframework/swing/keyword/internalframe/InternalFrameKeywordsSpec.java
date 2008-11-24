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


package org.robotframework.swing.keyword.internalframe;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.internalframe.DefaultInternalFrameOperator;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.util.IComponentConditionResolver;

@RunWith(JDaveRunner.class)
public class InternalFrameKeywordsSpec extends MockSupportSpecification<InternalFrameKeywords> {
    private String identifier = "someInternalFrame";
    
    public class Any {
        public InternalFrameKeywords create() {
            return new InternalFrameKeywords();
        }
        
        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }
        
        public void hasCloseInternalFrameKeyword() {
            specify(context, satisfies(new RobotKeywordContract("closeInternalFrame")));
        }
        
        public void hasInternalFrameShouldExistKeyword() {
          specify(context, satisfies(new RobotKeywordContract("internalFrameShouldExist")));
        }
        
        public void hasInternalFrameShouldNotExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("internalFrameShouldNotExist")));
        }
        
        public void hasInternalFrameShouldBeOpenKeyword() {
            specify(context, satisfies(new RobotKeywordContract("internalFrameShouldBeOpen")));
        }
       
        public void hasInternalFrameShouldNotBeOpenKeyword() {
            specify(context, satisfies(new RobotKeywordContract("internalFrameShouldNotBeOpen")));
        }
        
        public void hasOperatorFactory() {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }
        
        public void hasExistenceResolver() {
            specify(context, satisfies(new FieldIsNotNullContract("existenceResolver")));
        }
    }
    
    public class Operating {
        private OperatorFactory<DefaultInternalFrameOperator> operatorFactory;
        private DefaultInternalFrameOperator operator;

        public InternalFrameKeywords create() {
            InternalFrameKeywords internalFrameKeywords = new InternalFrameKeywords();
            injectMockOperatorFactory(internalFrameKeywords);
            return internalFrameKeywords;
        }
        
        public void closesInternalFrame() {
            checking(new Expectations() {{
                one(operator).close();
            }});
            
            context.closeInternalFrame(identifier);
        }
        
        public void shouldBeOpenPassesIfFrameIsOpen() throws Throwable {
            checking(new Expectations() {{
                one(operator).isVisible(); will(returnValue(true));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.internalFrameShouldBeOpen(identifier);
                }
            }, must.not().raise(Exception.class));
        }
        
        public void shouldBeOpenFailsIfFrameIsNotOpen() throws Throwable {
            checking(new Expectations() {{
                one(operator).isVisible(); will(returnValue(false));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.internalFrameShouldBeOpen(identifier);
                }
            }, raiseExactly(AssertionFailedError.class, "Internal frame '" + identifier + "' is not open."));
        }

        public void shouldNotBeOpenPassesIfFrameIsNotOpen() throws Throwable {
            checking(new Expectations() {{
                one(operator).isVisible(); will(returnValue(false));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.internalFrameShouldNotBeOpen(identifier);
                }
            }, must.not().raise(Exception.class));
        }
        
        public void shouldNotBeOpenFailsIfFrameIsOpen() throws Throwable {
            checking(new Expectations() {{
                one(operator).isVisible(); will(returnValue(true));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.internalFrameShouldNotBeOpen(identifier);
                }
            }, raiseExactly(AssertionFailedError.class, "Internal frame '" + identifier + "' is open."));
        }
        
        private void injectMockOperatorFactory(InternalFrameKeywords internalFrameKeywords) {
            operatorFactory = injectMockTo(internalFrameKeywords, "operatorFactory", IdentifierParsingOperatorFactory.class);
            operator = mock(DefaultInternalFrameOperator.class);
            
            checking(new Expectations() {{
                one(operatorFactory).createOperator(identifier); will(returnValue(operator));
            }});
        }
    }
    
    public class CheckingExistence {
        private IComponentConditionResolver existenceResolver;

        public InternalFrameKeywords create() {
            InternalFrameKeywords internalFrameKeywords = new InternalFrameKeywords();
            existenceResolver = injectMockTo(internalFrameKeywords, "existenceResolver", IComponentConditionResolver.class);
            return internalFrameKeywords;
        }
        
        public void shouldExistPassesIfFrameExists() throws Throwable {
            checking(new Expectations() {{
                one(existenceResolver).satisfiesCondition(identifier); will(returnValue(true));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.internalFrameShouldExist(identifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void shouldExistFailsIfFrameDoesntExists() throws Throwable {
            checking(new Expectations() {{
                one(existenceResolver).satisfiesCondition(identifier); will(returnValue(false));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.internalFrameShouldExist(identifier);
                }
            }, raiseExactly(AssertionFailedError.class, "Internal frame '" + identifier + "' doesn't exist."));
        }
        
        public void shouldNotExistPassesIfFrameDoesntExists() throws Throwable {
            checking(new Expectations() {{
                one(existenceResolver).satisfiesCondition(identifier); will(returnValue(false));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.internalFrameShouldNotExist(identifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void shouldNotExistFailsIfFrameExists() {
            checking(new Expectations() {{
                one(existenceResolver).satisfiesCondition(identifier); will(returnValue(true));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.internalFrameShouldNotExist(identifier);
                }
            }, raiseExactly(AssertionFailedError.class, "Internal frame '" + identifier + "' exists."));
        }
    }
}
