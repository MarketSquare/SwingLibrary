package org.robotframework.swing.keyword.testing;

import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JListOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.testapp.TestListResults;

@RobotKeywords
public class ListTestingKeywords {
    @RobotKeyword
    public Object getListSelection() {
        return TestListResults.selectedElement;
    }
    
    @RobotKeyword
    public int getSelectionClickCount() {
        return TestListResults.clickCount;
    }
    
    @RobotKeyword
    public Object[] getSelectedValues(String index) {
        JListOperator operator = new JListOperator((ContainerOperator) Context.getContext(), Integer.parseInt(index));
        return operator.getSelectedValues();
    }
}
