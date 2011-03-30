package org.robotframework.swing.table;

import java.awt.Component;

import javax.swing.AbstractButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.laughingpanda.jretrofit.AllMethodsNotImplementedException;
import org.laughingpanda.jretrofit.Retrofit;
import org.netbeans.jemmy.operators.JTableOperator;
import org.robotframework.swing.chooser.WithText;
import org.robotframework.swing.common.SmoothInvoker;

public class CellValueExtractor {
    private JTableOperator jTableOperator;

    public CellValueExtractor(JTableOperator jTableOperator) {
        this.jTableOperator = jTableOperator;
    }

    public String textOf(int row, int col) {
        try {
            Component cellRendererComponent = getCellRendererComponent(row, col);
            if (isCheckboxRenderer(cellRendererComponent))
                return new Boolean(((AbstractButton) cellRendererComponent).isSelected()).toString();
            return coerceToWithText(cellRendererComponent).getText();
        } catch (AllMethodsNotImplementedException e) {
            return wrapElementToWithText(row, col).getText();
        }
    }

    public Component getCellRendererComponent(int row, int column) {
        TableCellRenderer renderer = jTableOperator.getCellRenderer(row, column);
        JTable table = (JTable) jTableOperator.getSource();
        Object value = jTableOperator.getValueAt(row, column);
        boolean isSelected = jTableOperator.isCellSelected(row, column);
        boolean hasFocus = jTableOperator.hasFocus();
        return getTableCellRendererComponentSmoothly(row, column, renderer, table, value, isSelected, hasFocus);
    }

    private boolean isCheckboxRenderer(Component cellRendererComponent) {
        TableCellRenderer defaultCheckboxRenderer = ((JTable) jTableOperator.getSource()).getDefaultRenderer(Boolean.class);
        return (defaultCheckboxRenderer.getClass().isInstance(cellRendererComponent));
    }

    private WithText coerceToWithText(Object element) {
        return (WithText) Retrofit.complete(element, WithText.class);
    }

    private WithText wrapElementToWithText(final int rowIndex, final int columnIndex) {
        return new WithText() {
            public String getText() {
                return jTableOperator.getModel()
                        .getValueAt(rowIndex, columnIndex)
                        .toString();
            }
        };
    }

    private Component getTableCellRendererComponentSmoothly(final int row, final int column, final TableCellRenderer renderer,
                                                            final JTable table, final Object value, final boolean isSelected, final boolean hasFocus) {
        return new SmoothInvoker<Component>() {
            public Object work() {
                return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        }.invoke();
    }
}
