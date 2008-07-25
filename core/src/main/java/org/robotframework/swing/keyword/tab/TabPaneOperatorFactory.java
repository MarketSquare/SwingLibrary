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

package org.robotframework.swing.keyword.tab;

import javax.swing.JTabbedPane;

import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.robotframework.swing.chooser.ByNameComponentChooser;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.DefaultContextVerifier;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.factory.ContextBasedOperatorFactory;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;

/**
 * @author Heikki Hulkko
 */
public class TabPaneOperatorFactory extends IdentifierParsingOperatorFactory<JTabbedPaneOperator> implements
    ContextBasedOperatorFactory<JTabbedPaneOperator> {
    private IContextVerifier contextVerifier = new DefaultContextVerifier();

    @Override
    public JTabbedPaneOperator createOperator(String identifier) {
        contextVerifier.verifyContext();
        return super.createOperator(identifier);
    }

    @Override
    public JTabbedPaneOperator createOperatorByIndex(int index) {
        return new JTabbedPaneOperator(Context.getContext(), index);
    }

    @Override
    public JTabbedPaneOperator createOperatorByName(String name) {
        return new JTabbedPaneOperator(Context.getContext(), new ByNameComponentChooser(name));
    }

    public JTabbedPaneOperator createOperatorFromContext() {
        if (JTabbedPane.class.isAssignableFrom(Context.getContext().getSource().getClass())) {
            return new JTabbedPaneOperator((JTabbedPane) Context.getContext().getSource());
        } else {
            contextVerifier.verifyContext();
            return new JTabbedPaneOperator(Context.getContext());
        }
    }
}
