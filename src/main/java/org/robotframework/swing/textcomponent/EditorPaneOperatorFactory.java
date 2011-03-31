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

package org.robotframework.swing.textcomponent;

import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JEditorPaneOperator;
import org.robotframework.swing.chooser.ByNameComponentChooser;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;

public class EditorPaneOperatorFactory extends IdentifierParsingOperatorFactory<EditorPaneOperator> {
    @Override
    public EditorPaneOperator createOperatorByIndex(int index) {
        return new EditorPaneOperator(new JEditorPaneOperator((ContainerOperator) Context.getContext(), index));
    }

    @Override
    public EditorPaneOperator createOperatorByName(String name) {
        return new EditorPaneOperator(new JEditorPaneOperator((ContainerOperator) Context.getContext(), new ByNameComponentChooser(name)));
    }

}
