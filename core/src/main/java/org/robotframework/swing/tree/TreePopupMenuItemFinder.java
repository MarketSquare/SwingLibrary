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

import javax.swing.JMenuItem;

import org.robotframework.swing.arguments.IdentifierHandler;
import org.robotframework.swing.context.Context;

import abbot.finder.BasicFinder;
import abbot.finder.matchers.JMenuItemMatcher;
import abbot.tester.ComponentTester;
import abbot.tester.JComponentTester;
import abbot.tester.JTreeLocation;

/**
 * @author Heikki Hulkko
 */
public class TreePopupMenuItemFinder implements ITreePopupMenuItemFinder {
    private BasicFinder basicFinder = new BasicFinder();
    private ComponentTester componentTester = new JComponentTester();
    private IdentifierHandler<JTreeLocation> treeLocationFactory = new JTreeLocationFactory();

    public JMenuItem findMenu(String nodeIdentifier, String menuPath) {
        return findMenu(treeLocationFactory.parseArgument(nodeIdentifier), menuPath);
    }

    private JMenuItem findMenu(JTreeLocation treeLocation, String menuPath) {
        popupMenuOnContainer(treeLocation);
        return findMenu(menuPath);
    }

    private void popupMenuOnContainer(JTreeLocation nodeLocation) {
        componentTester.actionShowPopupMenu(Context.getContext().getSource(), nodeLocation);
    }

    private JMenuItem findMenu(String menuPath) {
        try {
            return (JMenuItem) basicFinder.find(new JMenuItemMatcher(menuPath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
