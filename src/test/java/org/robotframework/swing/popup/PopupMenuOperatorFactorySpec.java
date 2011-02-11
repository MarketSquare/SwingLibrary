package org.robotframework.swing.popup;

import java.awt.Point;

import javax.swing.JPopupMenu;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.jdave.mock.MockSupportSpecification;

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
        private JPopupMenu popupMenu = dummy(JPopupMenu.class);

        public PopupMenuOperatorFactory create() {
            popupTarget = mock(ComponentOperator.class);
            
            popupMenuOperator = mock(JPopupMenuOperator.class);
            PopupMenuOperatorFactory popupMenuOperatorFactory = new PopupMenuOperatorFactory() {
                public JPopupMenuOperator createPopupOperator(JPopupMenu popupMenu) {
                    return popupMenuOperator;
                }
            };
            
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
