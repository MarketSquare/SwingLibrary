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

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.popup.PopupCaller;

@RunWith(JDaveRunner.class)
public class PopupMenuOperatorFactorySpec extends MockSupportSpecification<PopupMenuOperatorFactory> {
    public class CreatingFromPopupMenu {
        private JPopupMenuOperator popupMenuOperator;

        public PopupMenuOperatorFactory create() {
            popupMenuOperator = mock(JPopupMenuOperator.class);
            PopupMenuOperatorFactory popupMenuOperatorFactory = new PopupMenuOperatorFactory() {
                JPopupMenuOperator wrapWithOperator(JPopupMenu popupMenu) {
                    return popupMenuOperator;
                }
            };
            
            waitsToAvoidInstability(popupMenuOperatorFactory);
            
            return popupMenuOperatorFactory;
        }
        
        public void createsPopupOperator() {
            checking(new Expectations() {{
                one(popupMenuOperator).grabFocus();
            }});
            
            specify(context.createPopupOperator(dummy(JPopupMenu.class)), popupMenuOperator);
        }
        
        private void waitsToAvoidInstability(PopupMenuOperatorFactory popupMenuOperatorFactory) {
            final EventTool eventTool = injectMockTo(popupMenuOperatorFactory, EventTool.class);
            checking(new Expectations() {{
                one(eventTool).waitNoEvent(500);
            }});
        }
    }
    
    public class CreatingFromComponentOperator {
        private JPopupMenuOperator popupMenuOperator;
        private ComponentOperator popupTarget;
        private JPopupMenu popupMenu;

        public PopupMenuOperatorFactory create() {
            popupTarget = mock(ComponentOperator.class);
            
            popupMenuOperator = mock(JPopupMenuOperator.class);
            PopupMenuOperatorFactory popupMenuOperatorFactory = new PopupMenuOperatorFactory() {
                public JPopupMenuOperator createPopupOperator(JPopupMenu popupMenu) {
                    System.out.println("createPopupOperator");
                    return popupMenuOperator;
                }
            };
            
            popupMenu = dummy(JPopupMenu.class);
            
            final PopupCaller popupCaller = injectMockTo(popupMenuOperatorFactory, PopupCaller.class);
            checking(new Expectations() {{
                one(popupCaller).callPopupOnComponent(popupTarget, new Point(1, 2)); will(returnValue(popupMenu));
                one(popupTarget).getCenterX(); will(returnValue(1));
                one(popupTarget).getCenterY(); will(returnValue(2));
                allowing(popupTarget);
            }});
            
            return popupMenuOperatorFactory;
        }
        
        public void createsPopupOperator() {
            specify(context.createPopupOperator(popupTarget), popupMenuOperator); 
        }
    }
}
