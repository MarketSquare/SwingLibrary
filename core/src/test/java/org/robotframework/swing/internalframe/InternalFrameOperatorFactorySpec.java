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


package org.robotframework.swing.internalframe;

import java.awt.Component;

import javax.swing.JInternalFrame;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;

@RunWith(JDaveRunner.class)
public class InternalFrameOperatorFactorySpec extends OperatorFactorySpecification<InternalFrameOperatorFactory> {
    public class Any extends AnyIdentifierParsingOperatorFactory {
        @Override
        protected OperatorFactory<InternalFrameOperator> createOperatorFactory() {
            return new InternalFrameOperatorFactory();
        }

        @Override
        protected Component createComponent() {
            return new JInternalFrame() {
                public boolean isShowing() {
                    return true;
                }
            };
        }
    }
}
