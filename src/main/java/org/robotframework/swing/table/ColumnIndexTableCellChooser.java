package org.robotframework.swing.table;

import org.netbeans.jemmy.operators.JTableOperator;


public class ColumnIndexTableCellChooser extends AbstractTableCellChooser {
	private final int expectedColumn;

	public ColumnIndexTableCellChooser(int expectedRow, String expectedColumn) {
		super(expectedRow);
		this.expectedColumn = Integer.parseInt(expectedColumn);
	}

	@Override
	protected boolean checkColumn(JTableOperator tableOperator, int column) {
		return expectedColumn == column;
	}
}
