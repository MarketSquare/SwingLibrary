package org.robotframework.swing.testapp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

public class TestApplication {
    private JPanel panel;
    private JFrame frame;

    public static void main(String[] args) {
        new TestApplication().runTestApplication();
    }

    public void runTestApplication() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createFrame();
                createMainPanel();
                addMenuBar();
                addComponentsToMainPanel();
                addMainPanelToFrame();
                showGUI();
            }
        });
    }

    private void showGUI() {
        frame.pack();
        frame.setVisible(true);
    }

    private void addMainPanelToFrame() {
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(panel, BorderLayout.CENTER);
        contentPane.add(TestDesktopPane.INSTANCE, BorderLayout.NORTH);
    }

    private void createMainPanel() {
        panel = new PopupPanel();
        panel.setName("Main Panel");
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.CENTER);
        panel.setLayout(flowLayout);
    }

    private void addMenuBar() {
        frame.setJMenuBar(new TestMenuBar());
    }

    private void createFrame() {
        frame = new JFrame("Test App") {
            public Dimension getPreferredSize() {
                return new Dimension(1000, 1000);
            }
        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addComponentsToMainPanel() {
        panel.add(new TestTextField());
        panel.add(new TestButton());
        panel.add(new TestList());
        panel.add(new TestList() {{
            setName("listWithRenderer");
            setCellRenderer(new DefaultListCellRenderer() {
                public String getText() {
                    return super.getText().toUpperCase();
                }
            });
        }});
        panel.add(new TestCheckBox("Test Checkbox"));
        panel.add(new TestCheckBox("Test Checkbox 2"));
        panel.add(new TestCheckBox("Test Checkbox 3"));
        panel.add(new TestCheckBox("Test Checkbox 4"));
        panel.add(new TestComboBox());
        panel.add(new TestComboBox() {{
            setName("comboboxWithRenderer");
            setRenderer(new DefaultListCellRenderer() {
                public String getText() {
                    return super.getText().toUpperCase();
                }
            });
        }});
        final ContentChangingCombobox contentChangingComboBox = new ContentChangingCombobox();
        panel.add(new JButton("Reset Content Changing Combobox") {{
            setName("resetContentChangingComboBox");
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    contentChangingComboBox.resetModel();
                }
            });
        }});
        panel.add(contentChangingComboBox);
        panel.add(new TestLabel());

        JTextField tableEventTextField = new JTextField("tableEventTextField");
        tableEventTextField.setName("tableEventTextField");
        tableEventTextField.setText("tableEventTextField");
        panel.add(new TestTable("testTable", getTestTableData(), tableEventTextField));
        panel.add(new TestTable("anotherTable", getTestTableData(), tableEventTextField));
        panel.add(new TestTable("TableWithSingleValue", getFoobarTestTableData(), tableEventTextField));
        
        panel.add(new TestTree());
        panel.add(new TreeWithoutTreeNode());
        TestTextField tabButtonOutputTextField = new TestTextField();
        tabButtonOutputTextField.setName("tabButtonOutputTextField");
        panel.add(new TestTabbedPane("testTabbedPane1", tabButtonOutputTextField));
        panel.add(new TestTabbedPane("testTabbedPane2", tabButtonOutputTextField));
        panel.add(tabButtonOutputTextField);
        panel.add(new TestTextArea());
        panel.add(new TestSpinnerButton());
        panel.add(new TestRadioButton());
        panel.add(new TestToggleButton());
        panel.add(new JButton("Open File Chooser") {{
            setName("openFileChooser");
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new TestFileChooser().showSaveDialog(panel);
                }
            });
        }});
        panel.add(new TestEditorPane());

        panel.add(simpleTable());
        panel.add(tableEventTextField);
        
        panel.add(new JTextField() {{
        	setName("disabledTextField");
        	setEnabled(false);
            setPreferredSize(new Dimension(100, 30));	
        }});
        panel.add(new JTextField() {{
        	setName("uneditableTextField");
        	setEditable(false);
            setPreferredSize(new Dimension(100, 30));	
        }});
    }

    private Object[][] getTestTableData() {
        Object[][] data = {
                {"column one", "one/one", "two/one", "three/one", "four/one"},
                {"column two", "one/two", "two/two", "three/two", "four/two"},
                {"column three", "one/three", "two/three", "three/three", "four/three"},
                {"column four", "one/four", "two/four", "three/four", "four/four"}
        };
        return data;
    }
    
    private Object[][] getFoobarTestTableData() {
        Object[][] data = {
                {"column one", "foo", "bar", "bar", "bar"},
                {"column two", "bar", "foo", "bar", "bar"},
                {"column three", "bar", "bar", "foo", "bar"},
                {"column four", "bar", "bar", "bar", "foo"}
        };
        return data;
    }
    
    private JTable simpleTable() {
    	String[] colNames = {"first col", "second col"};
    	Object[][] data = {
    			{"fooness", "barness"},
    			{"quuxness", "lochness"},
    	};
    	JTable table = new JTable(data, colNames) {  
    		public boolean isCellEditable(int row, int column){  
    	        if (column == 1)
    	        	return false;  
    	        return true;  
	        }
    	};
    	table.setName("simpleTable");
    	table.setCellSelectionEnabled(true);
    	return table;
    }
}

class ContentChangingCombobox extends JComboBox implements ActionListener {
    
    private static final String REMOVABLE_ITEM = "Removable";
    private static final String[] ITEMS = new String[]{"Foo", "Bar", "Quux", REMOVABLE_ITEM};

    public ContentChangingCombobox() {
        setEditable(true);
        setName("contentChangingCombobox");
        addActionListener(this);
        setModel(new DefaultComboBoxModel(ITEMS));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("comboBoxChanged")) {
            String selected = (String) getSelectedItem();
            List<String> items = new ArrayList<String>();
            if (! selected.equals(REMOVABLE_ITEM))
                items.add(selected);
            ComboBoxModel model = getModel();
            for (int i=0, size = model.getSize(); i < size; i++) {
                String item = (String) model.getElementAt(i);
                if (item != selected)
                    items.add(item);
            }
            setModel(new DefaultComboBoxModel(items.toArray()));
            setSelectedIndex(0);
        }
    }  
    
    public void resetModel() {
        setModel(new DefaultComboBoxModel(ITEMS));
    }
}

class PopupPanel extends JPanel {
    private final Operation showName = new Operation() {
        public void perform(Component operatedComponent) {
            JOptionPane.showMessageDialog(operatedComponent, operatedComponent.getName());
        }
    };
    
    private ContextMenu popup = new ContextMenu() {{
        add("Show name", showName);
    }};
    
    
    @Override
    public Component add(Component comp) {
        addPopupToComponentIfRequired(comp);
        return super.add(comp);
    }

    private void addPopupToComponentIfRequired(Component comp) {
        if (isWithPopup(comp)) {
            addPopupToComponent(comp);
        }
    }
    
    private boolean isWithPopup(Component comp) {
        return comp.getClass().isAnnotationPresent(WithPopup.class);
    }

    private void addPopupToComponent(Component comp) {
        comp.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popup.show((Component) e.getSource(), e.getX(), e.getY());
                }
            }
        });
    }
}

class CustomComboBoxRenderer extends JLabel implements ListCellRenderer {
    private Font uhOhFont;
    public CustomComboBoxRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        String pet = "Pet"+index;
        setUhOhText(pet + " (no image)", list.getFont());
        return this;
    }

    protected void setUhOhText(String uhOhText, Font normalFont) {
        if (uhOhFont == null)
            uhOhFont = normalFont.deriveFont(Font.ITALIC);
        setFont(uhOhFont);
        setText(uhOhText);
    }
}