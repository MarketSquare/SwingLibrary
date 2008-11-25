package org.robotframework.swing.testapp;

import java.io.File;

import javax.swing.JFileChooser;

public class TestFileChooser extends JFileChooser {
    public static String selectedFilePath;
    public static boolean cancelled = false;

    public TestFileChooser() {
        setName("testFileChooser");
    }
    
    @Override
    public void approveSelection() {
        super.approveSelection();
        cancelled = false;
        File selected = super.getSelectedFile();
        if (selected != null) {
            selectedFilePath = selected.getAbsolutePath();
        }
    }
    
    @Override
    public void cancelSelection() {
        super.cancelSelection();
        cancelled = true;
    }
}
