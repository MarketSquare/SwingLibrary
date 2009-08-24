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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;

import org.netbeans.jemmy.Waitable;
import org.netbeans.jemmy.operators.JTreeOperator;
import org.robotframework.swing.common.SmoothInvoker;

public class TreePathWaitable implements Waitable {
    private final String path;
    private final JTree tree;

    public TreePathWaitable(JTree tree, String path) {
        this.tree = tree;
        this.path = path;
    }

    public Object actionProduced(Object ignoredHere) {
        TreeInfo treeInfo = createTreeInfo();
        TreeNodes treeNodes = new TreeNodes(treeInfo);
        return treeNodes.extractTreePath(path);
    }
    
    public String getDescription() {
        return "Tree path";
    }

    private TreeInfo createTreeInfo() {
        return new TreeInfo() {
            public String getNodeText(Object node) {
                return extractTextSmoothly(node);
            }

            public Object getRoot() {
                return getRootSmoothly();   
            }

            public boolean rootIsVisible() {
                return new JTreeOperator(tree).isRootVisible();
            }

            public Iterator<Object> getChildren(Object node) {
                TreeModel model = tree.getModel();
                int childCount = model.getChildCount(node);
                List<Object> children = new ArrayList<Object>(childCount);
                for (int i = 0; i < childCount; i++) {
                    children.add(model.getChild(node, i));
                }
                return children.iterator();
            }
        };
    }

    private String extractTextSmoothly(Object node) {
        return new NodeTextExtractor(tree).getText(node, path);
    }
    
    private Object getRootSmoothly() {
        return new SmoothInvoker<Object>() {
            public Object work() {
                return tree.getModel().getRoot();
            }
        }.invoke();
    }
}