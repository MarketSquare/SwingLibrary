package org.robotframework.swing.testapp;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TestTable extends JScrollPane {
    private static Object[] COLUMN_HEADERS = new Object[] { "column one", "column two", "column three", "column four" };

    private static Object[][] TABLE_MODEL = new Object[][] {
        new Object[] { "one/one", "one/two", "one/three", "one/four" },
        new Object[] { "two/one", "two/two", "two/three", "two/four" },
        new Object[] { "three/one", "three/two", "three/three", "three/four" },
        new Object[] { "four/one", "four/two", "four/three", "four/four" }
    };

    public TestTable(String name) {
        super(createTable(name));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 100);
    }

    private static JTable createTable(String name) {
        JTable table = new JTable(TABLE_MODEL, COLUMN_HEADERS);
        table.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    new TablePopupMenu((JTable) e.getSource(), e.getX(), e.getY());
                }
            }
        });
        table.setName(name);
        return table;
    }
    
    
    private static class TablePopupMenu extends JPopupMenu {
        private final JTable invoker;

        public TablePopupMenu(JTable invoker, int x, int y) {
            this.invoker = invoker;
            addMenu(getCellCoordinates(x, y));
            show(invoker, x, y);
        }

        private Point getCellCoordinates(int x, int y) {
            int columnCount = invoker.getModel().getColumnCount();
            int rowCount = invoker.getModel().getRowCount();
            int cellX = x / (invoker.getWidth() / columnCount);
            int cellY = y / (invoker.getHeight() / rowCount);
            return new Point(cellX, cellY);
        }

        private void addMenu(final Point cellCoordinates) {
            add(new MenuItemWithCommand("Replace text") {
                public void actionPerformed(ActionEvent e) {
                    invoker.setValueAt("newValue", cellCoordinates.y, cellCoordinates.x);
                }
            });
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
        }
    }
}
