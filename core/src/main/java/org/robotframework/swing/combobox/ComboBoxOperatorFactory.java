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

package org.robotframework.swing.combobox;

import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.robotframework.swing.chooser.ByNameComponentChooser;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.factory.DefaultContextVerifyingOperatorFactory;

public class ComboBoxOperatorFactory extends DefaultContextVerifyingOperatorFactory<ComboBoxOperator> {
    public ComboBoxOperator createOperatorByIndex(int index) {
        JComboBoxOperator jComboboxOperator = new JComboBoxOperator((ContainerOperator) Context.getContext(), index);
        return new DefaultComboBoxOperator(jComboboxOperator);
    }

    public ComboBoxOperator createOperatorByName(String name) {
        JComboBoxOperator jComboboxOperator = new JComboBoxOperator((ContainerOperator) Context.getContext(), new ByNameComponentChooser(name));
        return new DefaultComboBoxOperator(jComboboxOperator);
    }
}
