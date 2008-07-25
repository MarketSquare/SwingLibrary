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

package org.robotframework.swing.keyword.checkbox;

import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.robotframework.swing.chooser.ByTextComponentChooser;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;

/**
 * @author Heikki Hulkko
 */
public class CheckBoxOperatorFactory extends IdentifierParsingOperatorFactory<JCheckBoxOperator> {
    public JCheckBoxOperator createOperatorByIndex(int index) {
        return new JCheckBoxOperator(Context.getContext(), index);
    }

    public JCheckBoxOperator createOperatorByName(final String name) {
        return new JCheckBoxOperator(Context.getContext(), new ByTextComponentChooser(name));
    }
}
