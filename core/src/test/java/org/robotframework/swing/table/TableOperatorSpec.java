package org.robotframework.swing.table;

import java.awt.Point;

import javax.swing.JTable;

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.netbeans.jemmy.ComponentChooser;

@RunWith(JDaveRunner.class)
public class TableOperatorSpec extends Specification<Void> {
	private String row = "12";
	private String column = "21";
    private Point cell = new Point(Integer.parseInt(column), Integer.parseInt(row));

    public class WhenOperatingOnCell {
    	private FakeEnhancedTableOperator tableOperator;

		public void create() {
    		tableOperator = new FakeEnhancedTableOperator();
    	}

        public void usesGivenCoordinatesToSelectCell() {
            tableOperator.selectCell(row, column);

            specifyGivenCoordinatesAreUsed();
        }

        public void usesGivenCoordinatesToCheckIfCellIsSelected() {
        	tableOperator.isCellSelected(row, column);

        	specifyGivenCoordinatesAreUsed();
        }

        public void usesGivenCoordinatesToGetValue() {
        	tableOperator.getValueAt(row, column);

        	specifyGivenCoordinatesAreUsed();
        }

        public void usesGivenCoordinatesToSetValue() {
            tableOperator.setValueAt("someValue", row, column);

            specifyGivenCoordinatesAreUsed();
        }

        public void usesGivenCoordinatesToChangeCellObject() {
            tableOperator.changeCellObject(row, column, "someValue");

            specifyGivenCoordinatesAreUsed();
        }
        
        private void specifyGivenCoordinatesAreUsed() {
        	specify(cell.x == tableOperator.column);
        	specify(cell.y == tableOperator.row);
        }
    }

    public class HandlingErrorScenarios {
        private TableOperator tableOperator;

        public void create() {
            tableOperator = new TableOperator(dummy(JTable.class)) {
                public Point findCell(ComponentChooser chooser) {
                    return new Point(-1, -1);
                }
            };
        }

        public void findingNonexistentCellFails() {
            specify(new Block() {
                public void run() throws Throwable {
                    tableOperator.selectCell("12", "nonexisting");
                }
            }, should.raise(InvalidCellException.class));
        }
    }

    private class FakeEnhancedTableOperator extends TableOperator {
        private int row, column;

        public FakeEnhancedTableOperator() {
            super(dummy(JTable.class));
        }

        @Override
        public void selectCell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean isCellSelected(int row, int column) {
        	this.row = row;
        	this.column = column;
        	return super.isCellSelected(row, column);
        }

        @Override
        public Object getValueAt(int row, int column) {
        	this.row = row;
        	this.column = column;
        	return super.getValueAt(row, column);
        }

        @Override
        public void setValueAt(Object newValue, int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public Point findCell(String row, String columnIdentifier) {
            return cell;
        }
        
        @Override
        public void changeCellObject(int row, int column, Object newValue) {
            this.row = row;
            this.column = column;
        }
    }
}
