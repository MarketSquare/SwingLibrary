package org.robotframework.swing.testapp;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.robotframework.javalib.util.KeywordNameNormalizer;

public class TestTree extends JTree implements ActionListener {
    private static final String ROOT_NAME = "The Java Series";
    private String rootName = ROOT_NAME;

    public TestTree() {
        this(new MyTreeNode(ROOT_NAME) {{
                add(new DefaultMutableTreeNode("Books for Java Programmers") {{
                        add(new MyTreeNode("The Java Tutorial: A Short Course on the Basics"));
                        add(new MyTreeNode("The Java Tutorial Continued: The Rest of the JDK"));
                        add(new MyTreeNode("The JFC Swing Tutorial: A Guide to Constructing GUIs"));
                }});

                add(new MyTreeNode("Books for Java Implementers") {{
                    add(new MyTreeNode("The Java Virtual Machine Specification") {{
                        add(new MyTreeNode("leafnode1"));
                        add(new MyTreeNode("leafnode2"));
                    }});
                    add(new MyTreeNode("The Java Language Specification") {{
                        add(new MyTreeNode("leafnode3"));
                        add(new MyTreeNode("leafnode4"));
                    }});
                }});
        }});
    }

    public TestTree(DefaultMutableTreeNode dmtn) {
        super(dmtn);
        setName("testTree");
        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    new MyPopup().show((JComponent) e.getSource(), e.getX(), e.getY());
                }
            }

            public void mouseClicked(MouseEvent e) {
                TestTreeResults.saveNodes(getSelectionPaths());
                TestTreeResults.clickCount = e.getClickCount();
            }
        });

        setCellRenderer(new DefaultTreeCellRenderer() {
            public String getText() {
                String nodeText = super.getText();
                if (nodeText.equals(ROOT_NAME)) {
                    return rootName.toLowerCase();
                } else {
                    return nodeText.toLowerCase();
                }
            }
        });
    }

    public void actionPerformed(ActionEvent ae) {
        createActionCommand(ae.getActionCommand()).perform();
    }

    private ActionCommand createActionCommand(String command) {
        if (command.equals("insert")) {
            return insertChild;
        } else if (command.equals("remove")) {
            return removeSelected;
        } else if (command.equals("showdialog")) {
            return showMessage;
        } else if (command.equals("hideroot")) {
            return hideRoot;
        } else if (command.equals("showroot")) {
            return showRoot;
        } else if (command.equals("savenodes")) {
            return saveNodes;
        } else if (command.equals("removerootname")) {
            return removeRootName;
        } else if (command.equals("restorerootname")) {
            return restoreRootName;
        } else {
            return new ActionCommand() {
                protected void operate() {
                    // Do nothing
                }
            };
        }
    }

    private abstract class ActionCommand {
        public void perform() {
            Delay.delay();
            operate();
            refresh();
            updateUI();
        }

        protected abstract void operate();

        private void refresh() {
            ((DefaultTreeModel) getModel()).nodeStructureChanged(getLastPathComponent());
        }

        protected DefaultMutableTreeNode getLastPathComponent() {
            TreePath selectionPath = getSelectionPath();
            if (selectionPath == null) {
                return null;
            }
            return (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
        }
    }

    private final ActionCommand insertChild = new ActionCommand() {
        protected void operate() {
            getLastPathComponent().add(new DefaultMutableTreeNode("child"));
        }
    };

    private final ActionCommand removeSelected = new ActionCommand() {
        protected void operate() {
            TreePath[] selectionPaths = getSelectionPaths();
            for (TreePath treePath : selectionPaths) {
                ((DefaultMutableTreeNode)treePath.getLastPathComponent()).removeFromParent();
            }
        }
    };

    private final ActionCommand showMessage = new ActionCommand() {
        protected void operate() {
            JOptionPane.showMessageDialog(TestTree.this, "This is an example message");
        }
    };

    private final ActionCommand hideRoot = new ActionCommand() {
        protected void operate() {
            setRootVisible(false);
        }
    };

    private final ActionCommand showRoot = new ActionCommand() {
        protected void operate() {
            setRootVisible(true);
        }
    };

    private final ActionCommand saveNodes = new ActionCommand() {
        protected void operate() {
            TestTreeResults.saveNodes(getSelectionPaths());
        }
    };

    private final ActionCommand removeRootName = new ActionCommand() {
        protected void operate() {
            rootName = "";
        }
    };

    private final ActionCommand restoreRootName = new ActionCommand() {
        protected void operate() {
            rootName = ROOT_NAME;
        }
    };

    private class MenuItemWithCommand extends JMenuItem {
        public MenuItemWithCommand(String text, String actionCommand) {
            super(text);
            setName(new KeywordNameNormalizer().normalize(text));
            setActionCommand(actionCommand);
            addActionListener(TestTree.this);
            Delay.delay();
        }
    }

    private class MyPopup extends JPopupMenu {
        public MyPopup() {
            add(new MenuItemWithCommand("Insert a child", "insert"));
            add(new MenuItemWithCommand("Remove", "remove"));
            add(new MenuItemWithCommand("Save node paths", "savenodes"));
            add(new MenuItemWithCommand("Show dialog", "showdialog"));
            add(new MenuItemWithCommand("Hide root node", "hideroot"));
            add(new MenuItemWithCommand("Show root node", "showroot"));
            add(new MenuItemWithCommand("Remove root name", "removerootname"));
            add(new MenuItemWithCommand("Restore root name", "restorerootname"));
            add(new JMenuItem("Disabled menuitem") {{
                setEnabled(false);
            }});
            add(new JMenu("Submenu") {{
                add(new JMenuItem("Disabled menuitem") {{
                    setEnabled(false);
                }});
                add(new JMenuItem("Enabled menuitem"));
            }});
            add(new JMenuItem("Menu Item"));

            setOpaque(true);
            setLightWeightPopupEnabled(true);
            setName("popupMenu");
        }

        @Override
        public void show(Component invoker, int x, int y) {
            Delay.delay();
            super.show(invoker, x, y);
        }
    }

    private static class MyTreeNode extends DefaultMutableTreeNode {
        public MyTreeNode(final String txt) {
            super(new Object() {
                public String toString() {
                    return txt;
                }
            });

            Delay.delay();
        }
    }
}
