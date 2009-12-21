package org.robotframework.swing.testapp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TestTable extends JScrollPane {

    public TestTable(String name, Object[][] data) {
        super(createTable(name, data));
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 100);
    }

    private static JTable createTable(String name, Object[][] data) {
        JTable table = new JTable();
        table.setName(name);
        table.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    new TablePopupMenu((JTable) e.getSource(), e.getX(), e.getY());
                }
            }
        });
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        for (Object[] datarow: data) {
            model.addColumn(datarow[0], copyOfRange(datarow, 1, datarow.length));
        }
        
        TableColumn col = table.getColumnModel().getColumn(0);
        String[] comboBoxValues = new String[] { "one/one", "two/one", "three/one", "four/one"};
        
        col.setCellEditor(new MyComboBoxEditor(comboBoxValues));
        col.setCellRenderer(new MyComboBoxRenderer(comboBoxValues));
        
        return table;
    }
    
    private static Object[] copyOfRange(Object[] datarow, int fromIndex, int toIndex) {
        List<Object> range = new ArrayList<Object>();
        for (int i = fromIndex; i < toIndex ; i++)
            range.add(datarow[i]);
        return range.toArray();
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
