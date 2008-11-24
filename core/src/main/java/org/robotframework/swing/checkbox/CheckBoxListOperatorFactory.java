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

package org.robotframework.swing.checkbox;

import java.awt.Component;

import javax.swing.JCheckBox;

import org.netbeans.jemmy.operators.Operator.Finder;
import org.robotframework.swing.checkbox.CheckBoxOperator;
import org.robotframework.swing.factory.ComponentBasedOperatorFactory;
import org.robotframework.swing.factory.OperatorListFactory;

/**
 * @author Heikki Hulkko
 */
public class CheckBoxListOperatorFactory extends OperatorListFactory<CheckBoxOperator> {
    public CheckBoxListOperatorFactory() {
        super(new Finder(JCheckBox.class), new CheckBoxOperatorFactory());
    }

    private static class CheckBoxOperatorFactory implements ComponentBasedOperatorFactory<CheckBoxOperator> {
        public CheckBoxOperator createOperator(Component component) {
            return new CheckBoxOperator((JCheckBox) component);
        }
    }
}
