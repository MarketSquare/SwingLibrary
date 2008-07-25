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

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.tree.TreePathFactory;
import org.robotframework.swing.tree.TreeSupport;
import org.robotframework.swing.util.ComponentExistenceResolver;
import org.robotframework.swing.util.IComponentConditionResolver;


/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class TreeNodeExistenceKeywords extends TreeSupport {
    private IComponentConditionResolver treeNodeExistenceResolver = new ComponentExistenceResolver(new TreePathFactory());

    @RobotKeyword("Fails if the tree node does not exist.\n" + " Assumes current context is a tree.\n\n" + "Example:\n"
        + "| Tree Node Should Exist | _Root|Folder_ |\n")
    public void treeNodeShouldExist(String nodeIdentifier) {
        Assert.assertTrue("Tree node '" + nodeIdentifier + "' doesn't exist.", treeNodeExistenceResolver.satisfiesCondition(nodeIdentifier));
    }

    @RobotKeyword("Fails if the tree node exists.\n" + " Assumes current context is a tree.\n\n" + "Example:\n"
        + "| Tree Node Should Not Exist | _Root|Folder_ |\n")
    public void treeNodeShouldNotExist(String nodeIdentifier) {
        Assert.assertFalse("Tree node '" + nodeIdentifier + "' exists.", treeNodeExistenceResolver.satisfiesCondition(nodeIdentifier));
    }
}
