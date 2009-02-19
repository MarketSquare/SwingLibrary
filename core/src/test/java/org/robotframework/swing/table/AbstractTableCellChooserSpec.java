package org.robotframework.swing.table;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JTableOperator;

@RunWith(JDaveRunner.class)
public class AbstractTableCellChooserSpec extends Specification<AbstractTableCellChooser> {
    public class Any {
        private String row = "2";
        public AbstractTableCellChooser create() {
            return new AbstractTableCellChooser(row) {
                protected boolean checkColumn(JTableOperator tableOperator, int column) {
                    return true;
                }
            };
        }
        
        public void choosesCellWithMatchingRow() {
            specify(context.checkCell(dummy(JTableOperator.class), 2, 1));
        }
        
        public void doesntChooseCellWhenRowDoesntMatch() {
            specify(!context.checkCell(dummy(JTableOperator.class), 3, 1));
        }
    }
}
