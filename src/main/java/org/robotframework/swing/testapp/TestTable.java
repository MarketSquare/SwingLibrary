package org.robotframework.swing.testapp;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

@SuppressWarnings("serial")
public class TestTable extends JScrollPane {

    public TestTable(String name, Object[][] data, final JTextField textField) {
        super(createTable(name, data, textField));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 100);
    }

    private static JTable createTable(String name, Object[][] data, final JTextField textField) {
        JTable table = new JTable();
        table.setName(name);
        table.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    new TablePopupMenu((JTable) e.getSource(), e.getX(), e.getY());
                }
            }
        });
        table.setModel(new MyTabeModel(data));
        TableColumn col = table.getColumnModel().getColumn(0);
        String[] comboBoxValues = new String[]{"one/one", "two/one", "three/one", "four/one"};

        col.setCellEditor(new MyComboBoxEditor(comboBoxValues));
        col.setCellRenderer(new MyComboBoxRenderer(comboBoxValues));

        table.setCellSelectionEnabled(true);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                textField.setText(
                        String.format("%s cell clicked %s times, button: %s modEx: %s",
                                e.getComponent().getName(),
                                e.getClickCount(),
                                e.getButton(),
                                e.getModifiersEx())
                );
            }
        });

        TableColumn col2 = table.getColumnModel().getColumn(2);
        col2.setCellRenderer(new CustomRenderer());

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

    static class CustomRenderer extends DefaultTableCellRenderer {
        public void setValue(Object value) {
            String s = value.toString();
            if (s.equals("four/three!!!"))
                setText("four/three");
            else
                setText(s);
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

            add(new MenuItemWithCommand("Replace text in selected") {
                public void actionPerformed(ActionEvent e) {
                    int[] cols = invoker.getSelectedColumns();
                    int[] rows = invoker.getSelectedRows();
                    for (int col : cols)
                        for (int row : rows)
                            invoker.setValueAt("newValue", row, col);
                }
            });

            setOpaque(true);
            setLightWeightPopupEnabled(true);
            setName("popupMenu");
        }
    }

    private static class MyTabeModel extends DefaultTableModel {
        private Object[][] data;

        public MyTabeModel(Object[][] data) {
            this.data = data;
            for (Object[] datarow : data)
                addColumn(datarow[0], Arrays.copyOfRange(datarow, 1, datarow.length));
        }

        public Class<?> getColumnClass(int col) {
            return data[col][1].getClass();

        }

    }
}
