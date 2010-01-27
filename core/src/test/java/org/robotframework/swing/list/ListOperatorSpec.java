package org.robotframework.swing.list;

import java.awt.Component;
import java.util.Arrays;

import javax.swing.ListModel;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JListOperator;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.chooser.ListItemChooser;

@RunWith(JDaveRunner.class)
public class ListOperatorSpec extends MockSupportSpecification<ListOperator> {
    public class Any {
        private JListOperator jListOperator;
        private CellTextExtractor textExtractor;

        public ListOperator create() {
            jListOperator = mock(JListOperator.class);
            textExtractor = mock(CellTextExtractor.class);
            return new ListOperator(jListOperator, textExtractor);
        }
        
        public void clearsSelection() {
            checking(new Expectations() {{
                one(jListOperator).clearSelection();
            }});
            
            context.clearSelection();   
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
            checking(new Expectations() {{
                atLeast(1).of(textExtractor).itemCount(); will(returnValue(4));
                ignoring(textExtractor).getTextFromRenderedComponent(0); will(returnValue("one"));
                ignoring(textExtractor).getTextFromRenderedComponent(1); will(returnValue("2"));
                ignoring(textExtractor).getTextFromRenderedComponent(2); will(returnValue("3"));
                ignoring(textExtractor).getTextFromRenderedComponent(3); will(returnValue("four"));
                ignoring(jListOperator);
            }});
            
            context.selectItems(Arrays.asList("one", "2", "3", "four"));
        }
        
        public void getsSource() {
            final Component component = dummy(Component.class);
            checking(new Expectations() {{
                one(jListOperator).getSource(); will(returnValue(component));
            }});
            
            specify(context.getSource(), component);
        }

        public void clicksOnListItem() {
            final String[] items = new String[]{"someItem", "5"};
            checking(new Expectations() {{
                ignoring(textExtractor).itemCount(); will(returnValue(8));
                one(jListOperator).clickOnItem(5, 1);
                one(jListOperator).clickOnItem(0, 2);
                ignoring(textExtractor).getTextFromRenderedComponent(with(any(Integer.class))); will(returnValue(items[0]));
                ignoring(jListOperator).getTimeouts();
            }});
            
            context.clickOnItem(items[1], 1);
            context.clickOnItem(items[0], 2);
        }
    }
}
