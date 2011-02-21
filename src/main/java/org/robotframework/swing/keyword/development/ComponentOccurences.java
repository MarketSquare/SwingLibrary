package org.robotframework.swing.keyword.development;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.CellRendererPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.table.JTableHeader;

public class ComponentOccurences {

    private Map<Class<? extends Component>, Integer> indexesByType = new HashMap<Class<? extends Component>, Integer>();
    
    public Integer count(Component component) {
        if (component instanceof JButton) return indexOfComponentOfClass(JButton.class);
        if (component instanceof JCheckBox) return indexOfComponentOfClass(JCheckBox.class);
        if (component instanceof JRadioButton) return indexOfComponentOfClass(JRadioButton.class);
        if (component instanceof JToggleButton) return indexOfComponentOfClass(JToggleButton.class);
        if (component instanceof JFileChooser) return indexOfComponentOfClass(JFileChooser.class);
        if (component instanceof JTable) return indexOfComponentOfClass(JTable.class);
        if (component instanceof JList) return indexOfComponentOfClass(JList.class);
        if (component instanceof JTextField) return indexOfComponentOfClass(JTextField.class);
        if (component instanceof JTextArea) return indexOfComponentOfClass(JTextArea.class);
        if (component instanceof JTree) return indexOfComponentOfClass(JTree.class);
        if (component instanceof JInternalFrame) return indexOfComponentOfClass(JInternalFrame.class);
        if (component instanceof JFrame) return indexOfComponentOfClass(JFrame.class);
        if (component instanceof JPanel) return indexOfComponentOfClass(JPanel.class);
        if (component instanceof JComboBox) return indexOfComponentOfClass(JComboBox.class);
        if (component instanceof JSpinner) return indexOfComponentOfClass(JSpinner.class);
        if (component instanceof JLabel) return indexOfComponentOfClass(JLabel.class);
        if (component instanceof JTabbedPane) return indexOfComponentOfClass(JTabbedPane.class);
        if (component instanceof JRootPane) return indexOfComponentOfClass(JRootPane.class);
        if (component instanceof JLayeredPane) return indexOfComponentOfClass(JLayeredPane.class);
        if (component instanceof CellRendererPane) return indexOfComponentOfClass(CellRendererPane.class);
        if (component instanceof JScrollPane) return indexOfComponentOfClass(JScrollPane.class);
        if (component instanceof JMenuBar) return indexOfComponentOfClass(JMenuBar.class);
        if (component instanceof JViewport) return indexOfComponentOfClass(JViewport.class);
        if (component instanceof JScrollBar) return indexOfComponentOfClass(JScrollBar.class);
        if (component instanceof JTableHeader) return indexOfComponentOfClass(JTableHeader.class);
        return null;
    }
    
    private int indexOfComponentOfClass(Class<? extends Component> clazz) {
        int index;
        if (indexesByType.containsKey(clazz))
            index = indexesByType.get(clazz) + 1;
        else
            index = 0;
        indexesByType.put(clazz, new Integer(index));
        return index;
    }
}
