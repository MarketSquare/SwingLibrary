package org.robotframework.swing.component;

import javax.swing.JComponent;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.popup.PopupMenuOperatorFactory;

@RunWith(JDaveRunner.class)
public class ComponentOperatorSpec extends MockSupportSpecification<ComponentOperator> {
    public class Any {
        private ComponentOperator componentOperator;

        public ComponentOperator create() {
            componentOperator = new ComponentOperator(dummy(JComponent.class));
            return componentOperator;
        }
        
        public void createsPopupMenuOperator() {
            final PopupMenuOperatorFactory menuOperatorFactory = injectMockToContext(PopupMenuOperatorFactory.class);
            final JPopupMenuOperator popupOperator = dummy(JPopupMenuOperator.class);
            
            checking(new Expectations() {{
                one(menuOperatorFactory).createPopupOperator(componentOperator); will(returnValue(popupOperator));
                
            }});
            
            specify(componentOperator.invokePopup(), popupOperator);
        }
    }
}
