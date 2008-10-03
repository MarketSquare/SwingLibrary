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

package org.robotframework.swing.keyword.tree;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.tree.TreePathFactory;
import org.robotframework.swing.tree.TreeSupport;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class TreeKeywords extends TreeSupport {
    private TreePathFactory treePathFactory = new TreePathFactory();

    @RobotKeyword("Collapses a tree node.\n"
        + "Assumes current context is a tree.\n\n"
        + "Examples:\n"
        + "| Collapse Tree Node | _Root|Folder_ |\n"
        + "| Collapse Tree Node | _3_ |\n")
    public void collapseTreeNode(String nodeIdentifier) {
        treeOperator().collapsePath(treePathFactory.createTreePath(nodeIdentifier));
    }

    @RobotKeyword("Expands a tree node.\n"
        + "Assumes current context is a tree.\n\n"
        + "Examples:\n"
        + "| Expand Tree Node | _Root|Folder_ |\n"
        + "| Expand Tree Node | _3_ |\n")
    public void expandTreeNode(String nodeIdentifier) {
        treeOperator().expandPath(treePathFactory.createTreePath(nodeIdentifier));
    }

    @RobotKeyword("Sets a tree node as selected.\n"
        + "Does not clear earlier selections. Assumes current context is a tree.\n\n"
        + "Example:\n"
        + "| Select Tree Node | _Root|Folder_ |\n")
    public void selectTreeNode(String nodeIdentifier) {
        treeOperator().addSelectionPath(treePathFactory.createTreePath(nodeIdentifier));
    }

    @RobotKeyword("Fails if the tree node is collapsed.\n"
        + "Assumes current context is a tree.\n\n"
        + "Example:\n"
        + "| Tree Node Should Be Expanded | _Root|Folder_ |\n")
    public void treeNodeShouldBeExpanded(String nodeIdentifier) {
        TreePath treePath = treePathFactory.createTreePath(nodeIdentifier);
        Assert.assertTrue("Tree node '" + nodeIdentifier + "' is not expanded.", treeOperator().isExpanded(treePath));
    }

    @RobotKeyword("Fails if the tree node is expanded.\n"
        + "Assumes current context is a tree.\n\n"
        + "Example:\n"
        + "| Tree Node Should Be Collapsed | _Root|Folder_ |\n")
    public void treeNodeShouldBeCollapsed(String nodeIdentifier) {
        TreePath treePath = treePathFactory.createTreePath(nodeIdentifier);
        Assert.assertTrue("Tree node '" + nodeIdentifier + "' is not collapsed.", treeOperator().isCollapsed(treePath));
    }

    @RobotKeyword("Sets a tree node as unselected.\n"
        + "Assumes current context is a tree.\n\n"
        + "Example:\n"
        + "| Unselect Tree Node | _Root|Folder_ |\n")
    public void unselectTreeNode(String nodeIdentifier) {
        treeOperator().removeSelectionPath(treePathFactory.createTreePath(nodeIdentifier));
    }

    @RobotKeyword("Fails if the node has child nodes.\n"
        + "Assumes current context is a tree.\n"
        + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`\n\n"
        + "Example:\n"
        + "| Tree Node Should Be Leaf | _Root|Folder_ |")
    public void treeNodeShouldBeLeaf(String nodeIdentifier) {
        TreeNode lastPathComponent =
            (TreeNode) treePathFactory.createTreePath(nodeIdentifier).getLastPathComponent();
        Assert.assertTrue("Tree node '" + nodeIdentifier + "' is not leaf.", lastPathComponent.isLeaf());
    }

    @RobotKeyword("Fails if the node doesn't have child nodes.\n"
        + "Assumes current context is a tree.\n"
        + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`\n\n"
        + "Example:\n"
        + "| Tree Node Should Not Be Leaf | _Root|Folder_ |")
    public void treeNodeShouldNotBeLeaf(String nodeIdentifier) {
        TreeNode lastPathComponent =
            (TreeNode) treePathFactory.createTreePath(nodeIdentifier).getLastPathComponent();
        Assert.assertFalse("Tree node '" + nodeIdentifier + "' is leaf.", lastPathComponent.isLeaf());
    }
}
