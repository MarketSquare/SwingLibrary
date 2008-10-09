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

package org.robotframework.swing.tree;

import java.awt.Point;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.Timeouts;
import org.netbeans.jemmy.Waitable;
import org.netbeans.jemmy.Waiter;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JTreeOperator;
import org.robotframework.swing.popup.DefaultPopupCaller;
import org.robotframework.swing.popup.IPopupCaller;

/**
 * @author Sami Honkonen
 * @author Heikki Hulkko
 */
public class EnhancedTreeOperator extends JTreeOperator {
    private IPopupCaller popupCaller = new DefaultPopupCaller();

    public EnhancedTreeOperator(ContainerOperator containerOperator, ComponentChooser componentChooser) {
        super(containerOperator, componentChooser);
    }

    public EnhancedTreeOperator(ContainerOperator containerOperator, int index) {
        super(containerOperator, index);
    }

    public EnhancedTreeOperator(JTree tree) {
        super(tree);
    }

    public JPopupMenu callPopupOnRow(int row) {
        selectRow(row);
        scrollToRow(row);
        Point pointToClick = getPointToClick(row);
        return popupCaller.callPopupOnComponent(this, pointToClick);
    }

    @Override
    public TreePath findPath(final String treePath) {
        try {
            return (TreePath) createTreeWaiter(treePath).waitAction(null);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Waiter createTreeWaiter(String treePath) {
        Waiter waiter = new Waiter(new TreePathWaitable(treePath));
        Timeouts nextNodeTimeout = copyTimeout("JTreeOperator.WaitNextNodeTimeout");
        waiter.setTimeouts(nextNodeTimeout);
        return waiter;
    }

    private Timeouts copyTimeout(String timeout) {
        Timeouts times = getTimeouts().cloneThis();
        times.setTimeout("Waiter.WaitingTime", getTimeouts().getTimeout(timeout));
        return times;
    }

    private class TreePathWaitable implements Waitable {
        private final String path;

        private TreePathWaitable(String path) {
            this.path = path;
        }

        public Object actionProduced(Object arg0) {
            return new TreePathFinder(EnhancedTreeOperator.this).findPath(path);
        }

        public String getDescription() {
            return "Tree path";
        }
    }
}
