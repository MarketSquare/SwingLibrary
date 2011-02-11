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

import java.util.Enumeration;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.netbeans.jemmy.operators.JTreeOperator;

public class TreeIterator {
    private final JTreeOperator treeOperator;

    public TreeIterator(JTreeOperator treeOperator) {
        this.treeOperator = treeOperator;
    }
    
    public void operateOnAllNodes(TreePathAction treePathAction) {
        doOnAll(root(), treePathAction);
    }

    TreePath root() {
        return new TreePath(treeOperator.getRoot());
    }
    
    private void doOnAll(TreePath parent, TreePathAction treePathAction) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        for (Enumeration<TreeNode> e = node.children(); e.hasMoreElements();) {
            TreePath path = parent.pathByAddingChild(e.nextElement());
            doOnAll(path, treePathAction);
        }
        
        treePathAction.operate(parent);
    }
}
