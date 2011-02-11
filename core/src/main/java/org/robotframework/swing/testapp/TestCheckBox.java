package org.robotframework.swing.testapp;

import javax.swing.JCheckBox;

public class TestCheckBox extends JCheckBox {
    public TestCheckBox(String text) {
        super(text);
        setName(withoutSpaces(text));
    }

    private String withoutSpaces(String text) {
        StringBuilder textWithoutSpaces = new StringBuilder();
        for (String piece : text.split(" ")) {
            textWithoutSpaces.append(piece);
        }
        return textWithoutSpaces.toString();
    }
}
