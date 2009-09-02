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

package org.robotframework.swing.menu;

import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.comparator.EqualsStringComparator;
import org.robotframework.swing.context.Context;

public class MenuSupport extends IdentifierSupport {
    private EventTool eventTool = new EventTool();
    private EqualsStringComparator comparator = new EqualsStringComparator();

    protected JMenuBarOperator menubarOperator() {
        JMenuBarOperator menuBarOperator = new JMenuBarOperator((ContainerOperator) Context.getContext());
        menuBarOperator.setComparator(comparator);
        return menuBarOperator;
    }

    protected JMenuItemOperator showMenuItem(final String path) {
        JMenuItemOperator menuItemOperator = menubarOperator().showMenuItem(path);
        menuItemOperator.setComparator(comparator);
        waitToAvoidInstability();
        menuItemOperator.grabFocus();
        waitToAvoidInstability();
        return menuItemOperator;
    }

    private void waitToAvoidInstability() {
        eventTool.waitNoEvent(200);
    }
}
