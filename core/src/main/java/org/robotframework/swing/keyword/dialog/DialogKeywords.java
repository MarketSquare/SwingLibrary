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

package org.robotframework.swing.keyword.dialog;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.dialog.DialogOperator;
import org.robotframework.swing.dialog.DialogOperatorFactory;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.util.ComponentExistenceResolver;
import org.robotframework.swing.util.IComponentConditionResolver;

@RobotKeywords
public class DialogKeywords {
    private IdentifierParsingOperatorFactory<DialogOperator> operatorFactory = new DialogOperatorFactory();
    private IComponentConditionResolver dialogExistenceResolver = new ComponentExistenceResolver(operatorFactory);

    @RobotKeyword("Selects a dialog as current context.\n\n"
        + "Example:\n"
        + "| Select Dialog  | _About_ |\n")
    public void selectDialog(String identifier) {
        Context.setContext(operatorFactory.createOperator(identifier));
    }

    @RobotKeyword("Closes a dialog.\n\n"
        + "Example:\n"
        + "| Close Dialog | _About_ |\n")
    public void closeDialog(String identifier) {
        operatorFactory.createOperator(identifier).close();
    }

    @RobotKeyword("Fails if the dialog is not open.\n\n"
        + "Example:\n"
        + "| Dialog Should Be Open | _About_ |\n")
    public void dialogShouldBeOpen(String identifier) {
        Assert.assertTrue("Dialog '" + identifier + "' is not open", dialogExistenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Fails if the dialog is open.\n"
        + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`.\n\n"
        + "Example:\n"
        + "| Set Jemmy Timeouts | _2_ |\n"
        + "| Dialog Should Not Be Open | _About_ |\n")
    public void dialogShouldNotBeOpen(String identifier) {
        Assert.assertFalse("Dialog '" + identifier + "' is open", dialogExistenceResolver.satisfiesCondition(identifier));
    }
}
