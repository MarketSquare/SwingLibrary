package org.robotframework.swing.chooser;

import javax.swing.ListModel;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JListOperator;

@RunWith(JDaveRunner.class)
public class ListItemChooserSpec extends Specification<Void> {
    public class Any {
        private JListOperator operator;
        private ListModel model;

        public void create() {
            operator = mock(JListOperator.class);
            model = mock(ListModel.class);
            checking(new Expectations() {{
                one(operator).getModel(); will(returnValue(model));
                one(model).getElementAt(6); will(returnValue("someItem"));
            }});
        }
        
        public void passesWhenMatches() {
            specify(new ListItemChooser("someItem").checkItem(operator, 6));
        }
        
        public void doesntPassWhenDoesntMatch() {
            specify(!new ListItemChooser("somethingElse").checkItem(operator, 6));
        }
    }
}
