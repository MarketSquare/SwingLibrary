package org.robotframework.swing.testapp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

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
        panel.add(new TestLabel());
        panel.add(new TestTable("testTable"));
        panel.add(new TestTable("anotherTable"));
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