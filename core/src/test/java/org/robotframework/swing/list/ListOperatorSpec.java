package org.robotframework.swing.list;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.ListModel;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JListOperator;
import org.robotframework.swing.chooser.ListItemChooser;
import org.robotframework.swing.keyword.MockSupportSpecification;

@RunWith(JDaveRunner.class)
public class ListOperatorSpec extends MockSupportSpecification<ListOperator> {
    public class Any {
        private JListOperator jListOperator;

        public ListOperator create() {
            jListOperator = mock(JListOperator.class);
            return new ListOperator(jListOperator);
        }
        
        public void clearsSelection() {
            checking(new Expectations() {{
                one(jListOperator).clearSelection();
            }});
            
            context.clearSelection();   
        }
        
        public void getsSelectedValue() {
            checking(new Expectations() {{
                one(jListOperator).getSelectedValue(); will(returnValue("someValue"));
            }});
            
            specify(context.getSelectedValue(), "someValue");
        }
        
        public void getsSize() {
            checking(new Expectations() {{
                ListModel model = mock(ListModel.class);
                one(jListOperator).getModel(); will(returnValue(model));
                one(model).getSize(); will(returnValue(3));
            }});
            
            specify(context.getSize(), 3);
        }
        
        public void selectsAll() {
            checking(new Expectations() {{
                ListModel model = mock(ListModel.class);
                one(jListOperator).getModel(); will(returnValue(model));
                one(model).getSize(); will(returnValue(18));
                
                one(jListOperator).setSelectionInterval(0, 17);
            }});
            
            context.selectAll();
        }
        
        public void selectsItem() {
            final int[] indices = new int[] { 1, 2, 3, 4 };
            checking(new Expectations() {{
                one(jListOperator).findItemIndex(with(any(ListItemChooser.class))); will(returnValue(1));
                one(jListOperator).findItemIndex(with(any(ListItemChooser.class))); will(returnValue(4));
                
                one(jListOperator).selectItems(indices);
            }});
            
            context.selectItems(new ArrayList<String>() {{ add("one"); add("2"); add("3"); add("four"); }});
        }
        
        public void getsSource() {
            final Component component = dummy(Component.class);
            checking(new Expectations() {{
                one(jListOperator).getSource(); will(returnValue(component));
            }});
            
            specify(context.getSource(), component);
        }

        public void clicksOnListItem() {
            checking(new Expectations() {{
                one(jListOperator).clickOnItem(5, 1);
                one(jListOperator).findItemIndex(with(any(ListItemChooser.class))); will(returnValue(8));
                one(jListOperator).clickOnItem(8, 2);
            }});
            
            context.clickOnItem("5", 1);
            context.clickOnItem("someItem", 2);
        }
    }
}
