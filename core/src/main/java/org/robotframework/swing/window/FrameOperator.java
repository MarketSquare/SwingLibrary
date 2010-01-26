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
package org.robotframework.swing.window;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.util.RegExComparator;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.operator.ComponentWrapper;

public class FrameOperator extends JFrameOperator implements ComponentWrapper {

    public static FrameOperator newOperatorFor(int index) {
        return new FrameOperator(index);
    }
    
    private FrameOperator(int index) {
        super(index);
    }
    
    public static FrameOperator newOperatorFor(String name) {
        if (IdentifierSupport.isRegExpPrefixed(name)) {
            String identifier = IdentifierSupport.removeRegExpPrefix(name);
            return new FrameOperator(createRegExpChooser(identifier));
        }
        return new FrameOperator(name);
    }

    private static ComponentChooser createRegExpChooser(String title) {
        return new JFrameFinder(new FrameByTitleFinder(title, new RegExComparator()));
    }

    private FrameOperator(ComponentChooser chooser) {
        super(chooser);
    }

    private FrameOperator(String title) {
        super(title);
    }
    
}
