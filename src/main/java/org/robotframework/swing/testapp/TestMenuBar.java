package org.robotframework.swing.testapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.robotframework.javalib.util.KeywordNameNormalizer;

public class TestMenuBar extends JMenuBar {
    private static KeywordNameNormalizer textNormalizer = new KeywordNameNormalizer();

    public TestMenuBar() {
        setName("testMenuBar");
        add(new TestMenu("Test Menu") {{
            add(new TestMenuItem("Show Test Dialog - do not open this") {
                public void actionPerformed(ActionEvent e) {
                    throw new RuntimeException("This menu item shouldn not be opened");
                }
            });
            add(new TestMenuItem("Show Test Dialog") {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(this, "This is an example message");
                }});
            add(new TestMenuItem("Show Non-Modal Dialog") {
                public void actionPerformed(ActionEvent e) {
                	new TestNonModalDialog().showDialog();
                }
            });
            add(new TestMenuItem("Show Internal Frame") {
                public void actionPerformed(ActionEvent e) {
                    TestDesktopPane.INSTANCE.showInternalFrame();
                }
            });

            add(new TestMenuItem("Mutable Menu") {
                public void actionPerformed(ActionEvent e) {
                    setText("Menu Item Was Pushed");
                }
            });

            add(new TestMenuItem("Disabled Menu Item").setDisabled());

            add(new TestMenuItem("Show Test Window") {
                public void actionPerformed(ActionEvent e) {
                    new JFrame("Test Window") {{
                        setName("testWindow");
                        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        setSize(400,400);
                        setVisible(true);
                    }};
                }
            });

            add(new JCheckBoxMenuItem("Test menu checkbox"));
            add(new TestMenuItem("Menu Item").setDisabled());
        }});
        add(new TestMenu("Test Menu2") {{
            add(new TestMenuItem("placeholder item 1") {});
            add(new TestMenu("Sub Menu1") {{
                add(new TestMenuItem("item 1") {});
                add(new TestMenuItem("item 2") {});
                add(new TestMenuItem("item 3") {});
            }});
            add(new TestMenuItem("placeholder item 2") {
            });
            add(new TestMenu("empty menu") {});
        }});
    }

    private static class TestMenu extends JMenu {
        public TestMenu(String text) {
            super(text);
            setName(textNormalizer.normalize(text));
        }
    }

    private static class TestMenuItem extends JMenuItem implements ActionListener {
        public TestMenuItem(String text) {
            super(text);
            setName(textNormalizer.normalize(text));
            setActionCommand(textNormalizer.normalize(text));
            addActionListener(this);
        }

        public JMenuItem setDisabled() {
            setEnabled(false);
            return this;
        }

        public void actionPerformed(ActionEvent e) {
        }
    }
}

