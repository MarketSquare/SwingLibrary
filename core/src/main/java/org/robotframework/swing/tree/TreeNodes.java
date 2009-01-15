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

import org.robotframework.javalib.util.ArrayUtil;
import org.springframework.util.ObjectUtils;

public class TreeNodes {
    private final TreeNode root;
    private final boolean rootIsVisible;

    public TreeNodes(TreeNode root, boolean rootIsVisible) {
        this.root = root;
        this.rootIsVisible = rootIsVisible;
    }
    
    public TreePath extractTreePath(String path) {
        return buildTreePath(parse(path));
    }
    
    private String[] parse(String path) {
        return removeRootIfNecessary(path.split("\\|"));
    }
    
    private String[] removeRootIfNecessary(String[] nodeNames) {
        if (rootIsVisible && nodeNames.length > 0 && nodeNames[0].equals(root.toString())) {
            return ArrayUtil.copyOfRange(nodeNames, 1, nodeNames.length);
        } else {
            return nodeNames;
        }
    }
    
    @SuppressWarnings("unchecked")
    private TreePath buildTreePath(String[] nodeNames) {
        TreePath treePathToNode = new TreePath(root);
        Enumeration<TreeNode> currentLevelChildren = root.children();
        for (String nodeName : nodeNames) {
            boolean foundMatch = false;
            
            while (currentLevelChildren.hasMoreElements()) {
                TreeNode currentNode = currentLevelChildren.nextElement();
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
}
