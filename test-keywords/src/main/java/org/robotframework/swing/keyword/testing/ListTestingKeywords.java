package org.robotframework.swing.keyword.testing;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
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
}
