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
package org.robotframework.swing.dialog;

import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.util.RegExComparator;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.operator.ComponentWrapper;

public class DialogOperator extends JDialogOperator implements ComponentWrapper {

    public static DialogOperator newOperatorFor(int index) {
        return new DialogOperator(index);
    }
    
    private DialogOperator(int index) {
        super(index);
    }

    public static DialogOperator newOperatorFor(String title) {
        if (titleIsRegExpPrefixed(title)) {
            String identifier = removeRegExpPrefix(title);
            JDialogFinder chooser = createRegExpComponentChooser(identifier);
            return new DialogOperator(chooser);
        }
        return new DialogOperator(title);
    }

    private static boolean titleIsRegExpPrefixed(String title) {
        return title.startsWith(IdentifierSupport.REGEXP_IDENTIFIER_PREFIX);
    }

    private static String removeRegExpPrefix(String title) {
        return title.replaceFirst(IdentifierSupport.REGEXP_IDENTIFIER_PREFIX, "");
    }
    
    private static JDialogFinder createRegExpComponentChooser(String identifier) {
        DialogByTitleFinder finder = new DialogByTitleFinder(identifier, new RegExComparator());
        return new JDialogFinder(finder);
    }

    private DialogOperator(JDialogFinder chooser) {
        super(chooser);
    }
    
    private DialogOperator(String title) {
        super(title);
    }
}
