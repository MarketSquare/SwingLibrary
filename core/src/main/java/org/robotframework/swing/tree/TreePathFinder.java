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
import org.springframework.util.ObjectUtils;

public class TreePathFinder {
    private final JTreeOperator treeOperator;

    public TreePathFinder(JTreeOperator treeOperator) {
        this.treeOperator = treeOperator;
    }

    public TreePath findPath(String path) {
        String[] nodeNames = treeOperator.parseString(path);
        TreeNode rootNode = (TreeNode) treeOperator.getRoot();

        nodeNames = getStartingPoint(nodeNames, rootNode);
        TreePath treePath = buildTreePath(rootNode, nodeNames);
        return treePath;
    }

    private String[] getStartingPoint(String[] nodeNames, TreeNode rootNode) {
        if (treeOperator.isRootVisible() && nodeNames[0].equals(rootNode.toString())) {
            return removeRoot(nodeNames);
        }
        return nodeNames;
    }

    private TreePath buildTreePath(TreeNode rootNode, String[] nodeNames) {
        TreePath treePathToNode = new TreePath(rootNode);
        Enumeration currentLevelChildren = rootNode.children();
        for (int i = 0; i < nodeNames.length; i++) {
            String nodeName = nodeNames[i];

            boolean foundMatch = false;
            while (currentLevelChildren.hasMoreElements()) {
                TreeNode currentNode = (TreeNode) currentLevelChildren.nextElement();
                if (ObjectUtils.nullSafeEquals(currentNode.toString(), nodeName)) {
                    currentLevelChildren = currentNode.children();
                    treePathToNode = treePathToNode.pathByAddingChild(currentNode);
                    foundMatch = true;
                    break;
                }
            }

            if (!foundMatch)
                return null;
        }
        return treePathToNode;
    }

    private String[] removeRoot(String[] nodeNames) {
        String[] newArray = new String[nodeNames.length - 1];
        for (int i = 1; i < nodeNames.length; i++) {
            newArray[i - 1] = nodeNames[i];
        }
        return newArray;
    }
}
