package org.robotframework.swing.table;

import org.netbeans.jemmy.operators.JTableOperator;


public class ColumnIndexTableCellChooser extends AbstractTableCellChooser {
	private final int expectedColumn;

	ColumnIndexTableCellChooser(int expectedRow, int expectedColumn) {
		super(expectedRow);
		this.expectedColumn = expectedColumn;
	}

	@Override
	protected boolean checkColumn(JTableOperator tableOperator, int column) {
		return expectedColumn == column;
	}
}
