package org.robotframework.swing.keyword.testing;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.javalib.util.ArrayUtil;
import org.robotframework.swing.testapp.TestTreeResults;

@RobotKeywords
public class TreeTestingKeywords {
    @RobotKeyword
    public void clearSavedNodes() {
        TestTreeResults.nodes.clear();
    }
    
    @RobotKeyword
    public void savedNodesShouldBe(String[] expectedNodes) {
        ArrayUtil.assertArraysContainSame(expectedNodes, TestTreeResults.nodes.toArray(new String[0]));
    }
}
