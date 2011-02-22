package org.robotframework.swing.keyword.development;

import java.awt.Component;
import java.util.LinkedHashMap;
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
    private Map<Class<? extends Component>, Integer> indexesByType = new LinkedHashMap<Class<? extends Component>, Integer>() {{
        put(JButton.class, -1);
        put(JCheckBox.class, -1);
        put(JRadioButton.class, -1);
        put(JToggleButton.class, -1);
        put(JFileChooser.class, -1);
        put(JTable.class, -1);
        put(JList.class, -1);
        put(JTextField.class, -1);
        put(JTextArea.class, -1);
        put(JTree.class, -1);
        put(JInternalFrame.class, -1);
        put(JFrame.class, -1);
        put(JPanel.class, -1);
        put(JComboBox.class, -1);
        put(JSpinner.class, -1);
        put(JLabel.class, -1);
        put(JTabbedPane.class, -1);
        put(JRootPane.class, -1);
        put(JLayeredPane.class, -1);
        put(CellRendererPane.class, -1);
        put(JScrollPane.class, -1);
        put(JMenuBar.class, -1);
        put(JViewport.class, -1);
        put(JScrollBar.class, -1);
        put(JTableHeader.class, -1);
    }};
    
    public Integer countIndexOf(Component component) {
        for (Class<? extends Component> clazz: indexesByType.keySet()) {
            if (clazz.isInstance(component)) {
                Integer index = indexesByType.get(clazz) + 1;
                indexesByType.put(clazz, index);
                return index;
            }
        }
        return null;
    }
}
