package org.robotframework.swing.combobox;

import java.awt.Component;

import javax.swing.ComboBoxModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JComboBoxOperator;

@RunWith(JDaveRunner.class)
public class ItemTextExtractorSpec extends Specification<ItemTextExtractor> {
    private JComboBoxOperator comboboxOperator;
    private ComboBoxModel comboBoxModel;
    
    public class ExtractingTextFromComponent {
        private ListCellRenderer cellRenderer;
        private String element = "someElement";
        private JList popupList = dummy(JList.class);
        private int itemIndex = 1;

        public ItemTextExtractor create() {
            comboboxOperator = mock(JComboBoxOperator.class);
            comboBoxModel = mock(ComboBoxModel.class);
            cellRenderer = mock(ListCellRenderer.class);
            
            checking(new Expectations() {{
                one(comboboxOperator).getModel(); will(returnValue(comboBoxModel));
                one(comboboxOperator).waitList(); will(returnValue(popupList));
                one(comboBoxModel).getElementAt(itemIndex); will(returnValue(element));
                one(comboboxOperator).getSelectedIndex(); will(returnValue(0));
                one(comboboxOperator).hasFocus(); will(returnValue(true));
                one(comboboxOperator).getRenderer(); will(returnValue(cellRenderer));
            }});
            
            return new ItemTextExtractor(comboboxOperator);
        }
        
        public void extractsTextFromComboboxItem() {
            checking(new Expectations() {{    
                one(cellRenderer).getListCellRendererComponent(popupList, element, itemIndex, false, true);
                will(returnValue(new ComponentWithText())); 
            }});
            
            specify(context.getTextFromRenderedComponent(itemIndex), "someText");
        }
        
        public void getsTextFromElementWhenComponentHasNotText() {
            checking(new Expectations() {{
                one(comboboxOperator).getModel(); will(returnValue(comboBoxModel));
                one(comboBoxModel).getElementAt(itemIndex); will(returnValue(element));
                one(cellRenderer).getListCellRendererComponent(popupList, element, itemIndex, false, true);
                will(returnValue(dummy(Component.class, "Not a text component"))); 
            }});
            
            specify(context.getTextFromRenderedComponent(itemIndex), "someElement");
        }
    }

    public class GettingItemCount {
        public ItemTextExtractor create() {
            comboboxOperator = mock(JComboBoxOperator.class);
            comboBoxModel = mock(ComboBoxModel.class);
            checking(new Expectations() {{
                one(comboboxOperator).getModel(); will(returnValue(comboBoxModel));
            }});
            
            return new ItemTextExtractor(comboboxOperator);
        }
        
        public void getsItemCount() {
            checking(new Expectations() {{
                one(comboBoxModel).getSize(); will(returnValue(10));
            }});
            
            specify(context.itemCount(), 10);
        }
    }
    
    private class ComponentWithText extends Component {
        public String getText() {
            return "someText";
        }
    }
}
