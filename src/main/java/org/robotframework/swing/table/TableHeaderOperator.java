package org.robotframework.swing.table;

import java.awt.Point;
import java.util.Enumeration;

import javax.swing.table.TableColumn;

import org.netbeans.jemmy.operators.JTableHeaderOperator;
import org.robotframework.swing.common.IdentifierSupport;
import org.springframework.util.ObjectUtils;

public class TableHeaderOperator extends IdentifierSupport {

    private JTableHeaderOperator headerOperator;

    public TableHeaderOperator(JTableHeaderOperator jTableHeaderOperator) {
        headerOperator = jTableHeaderOperator;
    }

    public void clickColumn(String identifier) {
        click(coordinatesOfTableHeaderWith(identifier));
    }

    private Point coordinatesOfTableHeaderWith(String identifier) {
        return headerOperator.getPointToClick(columnIndex(identifier));
    }

    private void click(Point point) {
        headerOperator.clickMouse(point.x, point.y, 1);
    }

    private int columnIndex(String identifier) {
        if (isIndex(identifier) && validIndex(asIndex(identifier)))
            return asIndex(identifier);
        return indexOfColumnLabel(identifier);
    }

    public int indexOfColumnLabel(String label) {
        Enumeration<TableColumn> columns = columns();
        for (int i=0; columns.hasMoreElements(); i++)
            if (ObjectUtils.nullSafeEquals(nextHeaderValue(columns), label))
                return i;
        throw new RuntimeException("The specified column identifier '" + label + "' is invalid.");
    }

    public Object nextHeaderValue(Enumeration<TableColumn> columns) {
        return columns.nextElement().getHeaderValue();
    }

    public Enumeration<TableColumn> columns() {
        return headerOperator.getColumnModel().getColumns();
    }

    private boolean validIndex(int index) {
        return index >= 0 && index < headerOperator.getColumnModel().getColumnCount();
    }

}
