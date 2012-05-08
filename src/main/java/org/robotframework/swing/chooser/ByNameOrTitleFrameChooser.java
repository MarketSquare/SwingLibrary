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

package org.robotframework.swing.chooser;

import java.awt.Component;

import javax.swing.JFrame;

import org.netbeans.jemmy.ComponentChooser;
import org.robotframework.swing.util.ObjectUtils;

public class ByNameOrTitleFrameChooser implements ComponentChooser {
    private ComponentChooser byNameComponentChooser;
    private final String componentType;
    private final String expectedNameOrTitle;

    public ByNameOrTitleFrameChooser(String expectedNameOrTitle,
            String componentType) {
        this.expectedNameOrTitle = expectedNameOrTitle;
        this.componentType = componentType;
        byNameComponentChooser = new ByNameComponentChooser(expectedNameOrTitle);
    }

    public boolean checkComponent(Component component) {
        return byNameComponentChooser.checkComponent(component)
                || titleMatches(component);
    }

    private boolean titleMatches(Component component) {
        if (component instanceof JFrame) {
            String title = ((JFrame) component).getTitle();
            return ObjectUtils.nullSafeEquals(title, expectedNameOrTitle);

        }
        return false;
    }

    public String getDescription() {
        return this.componentType + " with name or title '"
                + byNameComponentChooser.getDescription() + "'";
    }
}
