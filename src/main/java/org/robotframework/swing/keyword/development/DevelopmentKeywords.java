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

package org.robotframework.swing.keyword.development;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.container.ContainerIteratorForListing;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.operator.ComponentWrapper;

import java.awt.*;

@RobotKeywords
public class DevelopmentKeywords {

    @RobotKeyword("Prints components (their types and their internal names) from the selected context.\n\n"
        + "By default returns the component names without formatting used in the printout. If given an argument, the return value will have the same formatting as the printout. "
        + "The internal name is set with component's ``setName`` method: https://docs.oracle.com/javase/7/docs/api/java/awt/Component.html#setName(java.lang.String).\n"
        + "See keywords, `Select Window`, `Select Dialog` and `Select Context` for details about context.\n\n"
        + "Examples:\n"
        + "| `Select Main Window`         |\n"
        + "| `List Components In Context` |\n"
        + "| `List Components In Context` | formatted |\n")
    @ArgumentNames({ "formatted=" })
    public String listComponentsInContext(String formatted) {
        ComponentWrapper operator = Context.getContext();
        if ("".equals(formatted))
            return ContainerIteratorForListing.getComponentList((Container) operator.getSource()).toString();
        else {
            return ContainerIteratorForListing.getFormattedComponentList((Container) operator.getSource()).toString();
        }

    }

    @RobotKeywordOverload
    public String listComponentsInContext() {
        return listComponentsInContext("");
    }

}
