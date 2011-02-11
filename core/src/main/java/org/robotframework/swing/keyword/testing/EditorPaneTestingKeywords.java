package org.robotframework.swing.keyword.testing;

import org.junit.Assert;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.testapp.TestEditorPane;

@RobotKeywords
public class EditorPaneTestingKeywords {
    @RobotKeyword
    public void linkShouldHaveBeenClicked() {
        Assert.assertTrue("Link was not clicked", TestEditorPane.clicked);
    }
    
    @RobotKeyword
    public void hyperLinkEventDescriptionShouldBe(String expectedDescription) {
        Assert.assertEquals(expectedDescription, TestEditorPane.eventDescription);
    }
}
