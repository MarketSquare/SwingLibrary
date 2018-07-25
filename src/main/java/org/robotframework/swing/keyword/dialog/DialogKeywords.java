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

package org.robotframework.swing.keyword.dialog;

import org.junit.Assert;

import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
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
    private final IdentifierParsingOperatorFactory<DialogOperator> operatorFactory = new DialogOperatorFactory();
    private final IComponentConditionResolver dialogExistenceResolver = new ComponentExistenceResolver(
            operatorFactory);

    @RobotKeyword("Selects a dialog as current context and sets focus to it.\n\n"
            + "*N.B.* Regular expression can be used to select the dialog by prefixing the identifier with ``regexp=``.\n"
            + "See more details in `Regular expressions` section.\n\n"
            + "Examples:\n"
            + "| `Select Dialog`  | About |\n"
            + "| `Select Dialog`  | regexp=^A.* | Selects a dialog starting with 'A' | \n")
    @ArgumentNames({ "identifier" })
    public void selectDialog(String identifier) {
        DialogOperator operator = operatorFactory.createOperator(identifier);
        operator.getFocus();
        Context.setContext(operator);
    }

    @RobotKeyword("Closes a dialog.\n\n"
            + "*N.B.* Regular expression can be used to close the dialog by prefixing the identifier with ``regexp=``.\n"
            + "See more details in `Regular expressions` section.\n\n"
            + "Examples:\n"
            + "| `Close Dialog` | About |\n"
            + "| `Close Dialog`  | regexp=^A.* | Closes a dialog starting with 'A' | \n")
    @ArgumentNames({ "identifier" })
    public void closeDialog(String identifier) {
        operatorFactory.createOperator(identifier).close();
    }

    @RobotKeyword("Fails if the dialog is not open.\n\n" + "Example:\n"
            + "| `Dialog Should Be Open` | About |\n")
    @ArgumentNames({ "identifier" })
    public void dialogShouldBeOpen(String identifier) {
        Assert.assertTrue("Dialog '" + identifier + "' is not open",
                dialogExistenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Fails if the dialog is open.\n"
            + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`.\n\n"
            + "Example:\n" + "| `Set Jemmy Timeouts` | 2 |\n"
            + "| `Dialog Should Not Be Open` | About |\n")
    @ArgumentNames({ "identifier" })
    public void dialogShouldNotBeOpen(String identifier) {
        Assert.assertFalse("Dialog '" + identifier + "' is open",
                dialogExistenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Closes all the dialogs that are open.")
    public void closeAllDialogs() {
        String timeout = "DialogWaiter.WaitDialogTimeout";
        long originalTimeout = JemmyProperties.getCurrentTimeout(timeout);
        JemmyProperties.setCurrentTimeout(timeout, 100);
        while (closePossibleDialog())
            ;
        JemmyProperties.setCurrentTimeout(timeout, originalTimeout);
    }

    private boolean closePossibleDialog() {
        JDialogOperator operator;
        try {
            operator = new JDialogOperator();
        } catch (TimeoutExpiredException e) {
            return false;
        }
        System.out.println("Closed dialog '" + operator.getTitle() + "'.");
        operator.close();
        return true;
    }
}
