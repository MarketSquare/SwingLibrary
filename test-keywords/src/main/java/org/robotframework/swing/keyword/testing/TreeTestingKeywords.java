package org.robotframework.swing.keyword.testing;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.testapp.TestTree;

@RobotKeywords
public class TreeTestingKeywords {
    @RobotKeyword
    public void resetChildCounterToZero() {
        TestTree.insertedChildrenCounter = 0;
    }
}
