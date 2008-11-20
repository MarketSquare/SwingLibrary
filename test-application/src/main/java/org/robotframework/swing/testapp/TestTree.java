package org.robotframework.swing.testapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.robotframework.javalib.util.KeywordNameNormalizer;

public class TestTree extends JTree implements ActionListener {
    private int insertedChildrenCounter = 0;
    
    private JPopupMenu popup = new JPopupMenu() {{
        add(new MenuItemWithCommand("Insert a child", "insert"));
        add(new MenuItemWithCommand("Remove this node", "remove"));
        add(new MenuItemWithCommand("Show dialog", "showdialog"));
        add(new MenuItemWithCommand("Hide root node", "hideroot"));
        add(new MenuItemWithCommand("Show root node", "showroot"));
        add(new JMenuItem("Disabled menuitem") {{
            setEnabled(false);
        }});
        add(new JMenu("Submenu") {{
            add(new JMenuItem("Disabled menuitem") {{
                setEnabled(false);
            }});
            add(new JMenuItem("Enabled menuitem"));
        }});

        setOpaque(true);
        setLightWeightPopupEnabled(true);
        setName("popupMenu");
    }};
    
    public TestTree() {
        this(new DefaultMutableTreeNode("The Java Series") {{
                add(new DefaultMutableTreeNode("Books for Java Programmers") {{
                        add(new DefaultMutableTreeNode("The Java Tutorial: A Short Course on the Basics"));
                        add(new DefaultMutableTreeNode("The Java Tutorial Continued: The Rest of the JDK"));
                        add(new DefaultMutableTreeNode("The JFC Swing Tutorial: A Guide to Constructing GUIs"));
                }});

                add(new DefaultMutableTreeNode("Books for Java Implementers") {{
                        add(new DefaultMutableTreeNode("The Java Virtual Machine Specification"));
                        add(new DefaultMutableTreeNode("The Java Language Specification"));
                }});
        }});
    }

    public TestTree(DefaultMutableTreeNode dmtn) {
        super(dmtn);
        setName("testTree");

        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popup.show((JComponent) e.getSource(), e.getX(), e.getY());
                }
            }
        });
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("insert")) {
            getLastPathComponent().add(new DefaultMutableTreeNode("child" + (insertedChildrenCounter++)));
        } else if (ae.getActionCommand().equals("remove")) {
            getLastPathComponent().removeFromParent();
        } else if (ae.getActionCommand().equals("showdialog")) {
            JOptionPane.showMessageDialog(this, "This is an example message");
        } else if (ae.getActionCommand().equals("hideroot")) {
            setRootVisible(false);
        } else if (ae.getActionCommand().equals("showroot")) {
            setRootVisible(true);
        }
        refresh();
    }

    private DefaultMutableTreeNode getLastPathComponent() {
        TreePath selectionPath = getSelectionPath();
        if (selectionPath == null) {
            return null;
        }
        return (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
    }

    private void refresh() {
        ((DefaultTreeModel) getModel()).nodeStructureChanged((TreeNode) getLastPathComponent());
    }

    private class MenuItemWithCommand extends JMenuItem {
        public MenuItemWithCommand(String text, String actionCommand) {
            super(text);
            setName(new KeywordNameNormalizer().normalize(text));
            setActionCommand(actionCommand);
            addActionListener(TestTree.this);
        }
    }
}
