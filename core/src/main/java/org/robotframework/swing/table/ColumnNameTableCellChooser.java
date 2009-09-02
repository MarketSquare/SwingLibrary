package org.robotframework.swing.table;

import javax.swing.table.TableColumnModel;

import org.netbeans.jemmy.operators.JTableOperator;
import org.springframework.util.ObjectUtils;

public class ColumnNameTableCellChooser extends AbstractTableCellChooser {
	private final String expectedColumnName;

	public ColumnNameTableCellChooser(int row, String expectedColumnName) {
		super(row);
		this.expectedColumnName = expectedColumnName;
	}

	@Override
	protected boolean checkColumn(JTableOperator tableOperator, int column) {
		TableColumnModel columnModel = tableOperator.getColumnModel();
		Object actualColumnName = columnModel.getColumn(column).getHeaderValue();
		return ObjectUtils.nullSafeEquals(expectedColumnName, actualColumnName);
	}
}
