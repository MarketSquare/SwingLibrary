package org.robotframework.swing.testapp;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class TestNonModalDialog {
	
	private Component parent;
	
	public TestNonModalDialog() {}
	
	public void showDialog() {
		JOptionPane pane = new JOptionPane("Push the OK button!", JOptionPane.INFORMATION_MESSAGE);
		JDialog dialog = pane.createDialog(parent, "Non-modal Dialog");
		dialog.setModal(false);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);
	}
}
