package org.robotframework.swing.testapp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

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
        JTable table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    new TablePopupMenu((JTable) e.getSource(), e.getX(), e.getY());
                }
            }
        });
        
        table.setName(name);
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.addColumn("column one", new Object[]{"one/one", "two/one", "three/one", "four/one"});
        model.addColumn("column two", new Object[]{"one/two", "two/two", "three/two", "four/two"});
        model.addColumn("column three", new Object[]{"one/three", "two/three", "three/three", "four/three"});
        model.addColumn("column four", new Object[]{"one/four", "four/four", "three/four", "four/four"});
        TableColumn col = table.getColumnModel().getColumn(0);
        String[] comboBoxValues = new String[] { "one/one", "two/one", "three/one", "four/one"};
        
        col.setCellEditor(new MyComboBoxEditor(comboBoxValues));
        col.setCellRenderer(new MyComboBoxRenderer(comboBoxValues));
        
        return table;
    }
    
    public static class MyComboBoxRenderer extends JComboBox implements TableCellRenderer {
        public MyComboBoxRenderer(String[] items) {
            super(items);
        }
        
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
            setSelectedIndex(row);
            return this;
        }
    }
    
    public static class MyComboBoxEditor extends DefaultCellEditor {
        public MyComboBoxEditor(String[] items) {
            super(new JComboBox(items));
        }
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
