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

package org.robotframework.swing.keyword.internalframe;

import org.junit.Assert;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.internalframe.InternalFrameIteratorForListing;
import org.robotframework.swing.internalframe.InternalFrameOperator;
import org.robotframework.swing.internalframe.InternalFrameOperatorFactory;
import org.robotframework.swing.operator.ComponentWrapper;
import org.robotframework.swing.util.ComponentExistenceResolver;
import org.robotframework.swing.util.IComponentConditionResolver;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.util.List;

@RobotKeywords
public class InternalFrameKeywords {
    private final IdentifierParsingOperatorFactory<InternalFrameOperator> operatorFactory = new InternalFrameOperatorFactory();
    private final IComponentConditionResolver existenceResolver = new ComponentExistenceResolver(
            operatorFactory);

    @RobotKeyword("Closes internal frame.\n\n" + "Example:\n"
            + "| `Close Internal Frame`  | My Internal Frame |\n")
    @ArgumentNames({ "identifier" })
    public void closeInternalFrame(String identifier) {
        ((JInternalFrame) createOperator(identifier).getSource())
                .doDefaultCloseAction();
    }

    @RobotKeyword("Iconifies internal frame.\n\n" + "Example:\n"
            + "| `Close Internal Frame`  | My Internal Frame |\n")
    @ArgumentNames({ "identifier" })
    public void iconifyInternalFrame(String identifier) {
        iconify(identifier, true);
    }

    @RobotKeyword("De-iconifies internal frame.\n\n" + "Example:\n"
            + "| `Close Internal Frame`  | My Internal Frame |\n")
    @ArgumentNames({ "identifier" })
    public void deIconifyInternalFrame(String identifier) {
        iconify(identifier, false);
    }

    private void iconify(String identifier, boolean shouldIconify) {
        try {
            ((JInternalFrame) createOperator(identifier).getSource())
                    .setIcon(shouldIconify);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }

    @RobotKeyword("Maximizes internal frame.\n\n" + "Example:\n"
            + "| `Maximize Internal Frame`  | My Internal Frame |\n")
    @ArgumentNames({ "identifier" })
    public void maximizeInternalFrame(String identifier) {
        maximize(identifier, true);
    }

    @RobotKeyword("Minimizes internal.\n\n" + "Example:\n"
            + "| `Minimize Internal Frame`  | My Internal Frame |\n")
    @ArgumentNames({ "identifier" })
    public void minimizeInternalFrame(String identifier) {
        maximize(identifier, false);
    }

    private void maximize(String identifier, boolean shouldMaximize) {
        try {
            ((JInternalFrame) createOperator(identifier).getSource())
                    .setMaximum(shouldMaximize);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
    }

    @RobotKeyword("Fails if the internal frame doesn't exist in the current context.\n\n"
            + "Example:\n"
            + "| `Internal Frame Should Exist` | My Internal Frame |\n")
    @ArgumentNames({ "identifier" })
    public void internalFrameShouldExist(String identifier) {
        Assert.assertTrue("Internal frame '" + identifier + "' doesn't exist.",
                existenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Fails if the internal frame exists in the current context.\n\n"
            + "Example:\n"
            + "| `Internal Frame Should Not Exist` | My Internal Frame |\n")
    @ArgumentNames({ "identifier" })
    public void internalFrameShouldNotExist(String identifier) {
        Assert.assertFalse("Internal frame '" + identifier + "' exists.",
                existenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Fails if the internal frame is *not* open.\n\n"
            + "Example:\n"
            + "| `Internal Frame Should Be Open` | My Internal Frame |\n")
    @ArgumentNames({ "identifier" })
    public void internalFrameShouldBeOpen(String identifier) {
        Assert.assertTrue("Internal frame '" + identifier + "' is not open.",
                createOperator(identifier).isVisible());
    }

    @RobotKeyword("Fails if the internal frame *is* open.\n\n" + "Example:\n"
            + "| `Internal Frame Should Not Be Open` | My Internal Frame |\n")
    @ArgumentNames({ "identifier" })
    public void internalFrameShouldNotBeOpen(String identifier) {
        Assert.assertFalse("Internal frame '" + identifier + "' is open.",
                createOperator(identifier).isVisible());
    }

    @RobotKeyword("Returns all frames that are open in the current context." + "\n\n"
            + "Returns empty list if the context is not selected.\n\n"
            + "Example:\n"
            + "| `Select Main Window` |\n"
            + "| ${frames}= | `Get Internal Frames In Context` |\n"
            + "| `Should Contain` | ${frames} | Test Internal Frame |\n")
    public List<String> getInternalFramesInContext() {
        ComponentWrapper operator = Context.getContext();
        return InternalFrameIteratorForListing.getFrameList((Container) operator.getSource());
    }

    private InternalFrameOperator createOperator(String identifier) {
        return operatorFactory.createOperator(identifier);
    }
}
