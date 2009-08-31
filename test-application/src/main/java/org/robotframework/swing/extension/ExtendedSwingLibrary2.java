package org.robotframework.swing.extension;

import org.robotframework.swing.SwingLibrary;

public class ExtendedSwingLibrary2 extends SwingLibrary {
    public ExtendedSwingLibrary2() {
        super("com/some/own/keyword/*.class");
    }
}
