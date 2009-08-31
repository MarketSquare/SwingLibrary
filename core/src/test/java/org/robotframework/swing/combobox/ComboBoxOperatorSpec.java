package org.robotframework.swing.combobox;

import java.awt.Component;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JComboBoxOperator;

@RunWith(JDaveRunner.class)
public class ComboBoxOperatorSpec extends Specification<ComboBoxOperator> {
    public class Any {
        private JComboBoxOperator jComboboxOperator;
        private String comboItemIdentifier = "someItem";
        private ItemTextExtractor textExtractor;

        public ComboBoxOperator create() {
            jComboboxOperator = mock(JComboBoxOperator.class);
            textExtractor = mock(ItemTextExtractor.class);
            return new ComboBoxOperator(jComboboxOperator, textExtractor);
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
                one(textExtractor).itemCount(); will(returnValue(1));
                one(textExtractor).getTextFromRenderedComponent(0); will(returnValue(comboItemIdentifier));
                one(jComboboxOperator).selectItem(0);
                one(jComboboxOperator).hidePopup();
            }});
            
            context.selectItem(comboItemIdentifier);
        }
        
        public void selectsItemWithIndex() {
            checking(new Expectations() {{
                one(jComboboxOperator).pushComboButton();
                one(jComboboxOperator).selectItem(1);
            }});
            
            context.selectItem("1");
        }
        
        public void getsSelectedItem() {
            final Object selectedItem = new Object();
            checking(new Expectations() {{
                one(jComboboxOperator).getSelectedItem(); will(returnValue(selectedItem));
            }});
            
            specify(context.getSelectedItem(), selectedItem);
        }
        
        public void getItemEnabledState() {
            checking(new Expectations() {{
                one(jComboboxOperator).isEnabled(); will(returnValue(false));
            }});
            
            specify(context.isEnabled(), false);
        }
        
        public void typesText() {
            checking(new Expectations() {{
                one(jComboboxOperator).clearText();
                one(jComboboxOperator).typeText("someText");
            }});
            
            context.typeText("someText");
        }
    }
}
