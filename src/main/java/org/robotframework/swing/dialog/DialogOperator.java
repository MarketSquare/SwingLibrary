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
package org.robotframework.swing.dialog;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.util.RegExComparator;
import org.robotframework.swing.common.Identifier;
import org.robotframework.swing.operator.ComponentWrapper;

public class DialogOperator extends JDialogOperator implements ComponentWrapper {

    public static DialogOperator newOperatorFor(int index) {
        return new DialogOperator(index);
    }
    
    private DialogOperator(int index) {
        super(index);
    }

    public static DialogOperator newOperatorFor(String title) {
        Identifier identifier = new Identifier(title);
        if (identifier.isRegExp())
            return new DialogOperator(createRegExpComponentChooser(identifier.asString()));
        return new DialogOperator(title);
    }
    
    private static ComponentChooser createRegExpComponentChooser(String identifier) {
        return new JDialogFinder(new DialogByTitleFinder(identifier, new RegExComparator()));
    }

    private DialogOperator(ComponentChooser chooser) {
        super(chooser);
    }
    
    private DialogOperator(String title) {
        super(title);
    }
}
