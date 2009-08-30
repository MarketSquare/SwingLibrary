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

import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.swing.popup.DefaultPopupCaller;
import org.robotframework.swing.popup.PopupCaller;

public class PopupMenuOperatorFactory {
    private EventTool eventTool = new EventTool();
    private PopupCaller<ComponentOperator> popupCaller = new DefaultPopupCaller();
    
    public JPopupMenuOperator createPopupOperator(ComponentOperator componentOperator) {
        Point pointToClick = getPointToClick(componentOperator);
        JPopupMenu popupMenu = callPopupOnComponent(componentOperator, pointToClick);
        return createPopupOperator(popupMenu);
    }

    public JPopupMenuOperator createPopupOperator(JPopupMenu popupMenu) {
        JPopupMenuOperator popupMenuOperator = wrapWithOperator(popupMenu);
        popupMenuOperator.grabFocus();
        waitToAvoidInstability();
        return popupMenuOperator;
    }

    public JPopupMenu callPopupOnComponent(ComponentOperator componentOperator, Point pointToClick) {
        return popupCaller.callPopupOnComponent(componentOperator, pointToClick);
    }
    
    protected void waitToAvoidInstability() {
        eventTool.waitNoEvent(500);
    }
    
    JPopupMenuOperator wrapWithOperator(JPopupMenu popupMenu) {
        return new JPopupMenuOperator(popupMenu);
    }
    
    private Point getPointToClick(ComponentOperator componentOperator) {
        int x = componentOperator.getCenterX();
        int y = componentOperator.getCenterY();
        return new Point(x, y);
    }
}
