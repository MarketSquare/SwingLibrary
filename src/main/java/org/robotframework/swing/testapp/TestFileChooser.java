package org.robotframework.swing.testapp;

import java.io.File;

import javax.swing.JFileChooser;

public class TestFileChooser extends JFileChooser {
    public static String selectedFilePath;
    public static boolean cancelled = false;

    public TestFileChooser() {
        super(new File(System.getProperty("java.io.tmpdir")));
        setName("testFileChooser");
        setSelectedFile(new File("someFile.txt"));
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
