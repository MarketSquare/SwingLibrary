package org.robotframework.swing.combobox;

import java.awt.Component;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.robotframework.swing.comparator.EqualsStringComparator;

@RunWith(JDaveRunner.class)
public class DefaultComboBoxOperatorSpec extends Specification<DefaultComboBoxOperator> {
    public class Any {
        private JComboBoxOperator jComboboxOperator;
        private String comboItemIdentifier = "someItem";

        public DefaultComboBoxOperator create() {
            jComboboxOperator = mock(JComboBoxOperator.class);
            return new DefaultComboBoxOperator(jComboboxOperator);
        }
        
        public void wrapsSource() {
            final Component source = dummy(Component.class);
            checking(new Expectations() {{
                one(jComboboxOperator).getSource(); will(returnValue(source));
            }});
            
            specify(context.getSource(), source);
        }
        
        public void selectsItemWithName() {
            checking(new Expectations() {{
                one(jComboboxOperator).pushComboButton();
                one(jComboboxOperator).selectItem(with(equal(comboItemIdentifier)), with(any(EqualsStringComparator.class)));
            }});
            
            context.selectItem(comboItemIdentifier);
        }
        
        public void getsSelectedItem() {
            final Object selectedItem = new Object();
            checking(new Expectations() {{
                one(jComboboxOperator).getSelectedItem(); will(returnValue(selectedItem));
            }});
            
            specify(context.getSelectedItem(), selectedItem);
        }
    }
}
