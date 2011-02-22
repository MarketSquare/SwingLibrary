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

    @SuppressWarnings("serial")
    private Map<Class<? extends Component>, Integer> indexesByType = new HashMap<Class<? extends Component>, Integer>() {{
        put(JButton.class, null);
        put(JCheckBox.class, null);
        put(JRadioButton.class, null);
        put(JToggleButton.class, null);
        put(JFileChooser.class, null);
        put(JTable.class, null);
        put(JList.class, null);
        put(JTextField.class, null);
        put(JTextArea.class, null);
        put(JTree.class, null);
        put(JInternalFrame.class, null);
        put(JFrame.class, null);
        put(JPanel.class, null);
        put(JComboBox.class, null);
        put(JSpinner.class, null);
        put(JLabel.class, null);
        put(JTabbedPane.class, null);
        put(JRootPane.class, null);
        put(JLayeredPane.class, null);
        put(CellRendererPane.class, null);
        put(JScrollPane.class, null);
        put(JMenuBar.class, null);
        put(JViewport.class, null);
        put(JScrollBar.class, null);
        put(JTableHeader.class, null);
    }};
    
    public Integer count(Component component) {
        for (Class<? extends Component> clazz: indexesByType.keySet())
            if (clazz.isInstance(component))
                return indexOfComponentOfClass(clazz);
        return null;
    }

    private Integer indexOfComponentOfClass(Class<? extends Component> clazz) {
        Integer index = null;
        if (indexesByType.containsKey(clazz)) {
            index = indexesByType.get(clazz);
            if (index == null)
                index = new Integer(0);
            else
                index = new Integer(index + 1);
            indexesByType.put(clazz, index);
        }
        return index;
    }
}
