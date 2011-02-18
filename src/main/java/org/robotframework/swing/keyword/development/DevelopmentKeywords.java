/*
 * Copyright 2008 Nokia Siemens Networks Oyj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.robotframework.swing.keyword.development;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.operator.ComponentWrapper;

@RobotKeywords
public class DevelopmentKeywords {
    private List<String> resultComponentList = new ArrayList<String>();

    @RobotKeyword("Prints components (their types and their internal names) from the selected context.\n"
        + "The internal name is set with component's setName method: http://java.sun.com/j2se/1.4.2/docs/api/java/awt/Component.html#setName(java.lang.String).\n"
        + "See keywords, `Select Window`, `Select Dialog` and `Select Context` for details about context.\n\n"
        + "Example:\n"
        + "| Select Main Window         |\n"
        + "| List Components In Context |\n")
    public String listComponentsInContext() {
        ComponentWrapper operator = Context.getContext();
        new ContainerIteratorForListing((Container) operator.getSource()).iterate();
        return resultComponentList.toString();
    }

    private void printSpacesToFormatOutputAsTree(int level) {
        for (int i = 0; i < level; i++)
            System.out.print("   ");
    }

    private String componentToString(Component component) {
        String componentString = component.toString();
        int indexToStartOfDetails = componentString.indexOf('[');
        if (indexToStartOfDetails == -1)
            return componentString;
        return componentString.substring(0, indexToStartOfDetails);
    }
    
    private class ContainerIteratorForListing extends ContainerIterator {
        public ContainerIteratorForListing(Container container) {
            super(container);
        }
        
        public void operateOnComponent(Component component, int level) {
            printSpacesToFormatOutputAsTree(level);
            String componentName = componentToString(component);
            System.out.println(level + " " + componentName + " " + indexOfTypeOf(component) + ": " + component.getName());
            resultComponentList.add(componentName);
        }
    }
    
    private static abstract class ContainerIterator {
        private int level;
        private Container container;
        
        public ContainerIterator(Container container) {
            this.container = container;
        }
        
        public void iterate() {
            processComponent(container);
        }
        
        public abstract void operateOnComponent(Component component, int level);
        
        private void processComponent(Component component) {
            operateOnComponent(component, level);
            level++;
            if (component instanceof Container) {
                Component[] subComponents = ((Container) component).getComponents();
                for (int i = 0; i < subComponents.length; i++) {
                    processComponent(subComponents[i]);
                    level--;
                }
            }
        }
    }

    private String indexOfTypeOf(Component component) {
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
        return "N/A";
    }
    
    private Map<Class<? extends Component>, Integer> indexesByType = new HashMap<Class<? extends Component>, Integer>();
    private String indexOfComponentOfClass(Class<? extends Component> clazz) {
        int index;
        if (indexesByType.containsKey(clazz))
            index = indexesByType.get(clazz) + 1;
        else
            index = 0;
        indexesByType.put(clazz, new Integer(index));
        return ""+index;
    }
}
