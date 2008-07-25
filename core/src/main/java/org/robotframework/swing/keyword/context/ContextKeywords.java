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

package org.robotframework.swing.keyword.context;

import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.factory.OperatorFactory;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class ContextKeywords {
    private OperatorFactory<ContainerOperator> operatorFactory = new ContainerOperatorFactory();

    @RobotKeyword("Selects a container as current context.\n\n"
        + "Example:\n"
        + "| Select Context | _myPanel_ | # Sets _'myPanel'_ as current context |\n")
    public void selectContext(String identifier) {
        Context.setContext(operatorFactory.createOperator(identifier));
    }
}
