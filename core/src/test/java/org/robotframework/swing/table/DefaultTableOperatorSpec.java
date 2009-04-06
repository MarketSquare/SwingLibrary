package org.robotframework.swing.table;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JPopupMenu;

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsInstanceOf;
import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTableOperator.TableCellChooser;

@RunWith(JDaveRunner.class)
public class DefaultTableOperatorSpec extends Specification<DefaultTableOperator> {
    private String row = "2";
    private JTableOperator jTableOperator;
    private Object cellValue = new Object();
    private Point coordinates = new Point(2, 3);
    
    public class OperatingOnCellValues {
        public DefaultTableOperator create() {
            jTableOperator = mock(JTableOperator.class);
            return new DefaultTableOperator(jTableOperator);
        }
        
        public void clearsSelection() {
            checking(new Expectations() {{
                one(jTableOperator).clearSelection(); 
            }});
            
            context.clearSelection();
        }
        
        public void getsRowCount() {
            checking(new Expectations() {{
                one(jTableOperator).getRowCount(); will(returnValue(3)); 
            }});
            
            specify(context.getRowCount(), 3);
        }
        
        public void getsColumnCount() {
            checking(new Expectations() {{
                one(jTableOperator).getColumnCount(); will(returnValue(3)); 
            }});
            
            specify(context.getColumnCount(), 3);
        }
        
        public void getsSource() {
            final Component source = dummy(Component.class);
            checking(new Expectations() {{
                one(jTableOperator).getSource(); will(returnValue(source)); 
            }});
            
            specify(context.getSource(), source);
        }
        
        public void selectingNonexistentCellFails() {
        	checking(new Expectations() {{
        		one(jTableOperator).findCell(with(any(TableCellChooser.class))); will(returnValue(new Point(-1, -1)));
        	}});
        	
            specify(new Block() {
                public void run() throws Throwable {
                    context.selectCell("12", "nonexisting");
                }
            }, should.raise(InvalidCellException.class));
        }
    }
    
    public class RetrievingCellValues {
        public DefaultTableOperator create() {
            jTableOperator = mock(JTableOperator.class);
            return new DefaultTableOperator(jTableOperator);
        }

        public void getsSelectedCellValue() {
            checking(new Expectations() {{
                one(jTableOperator).getSelectedRow(); will(returnValue(2));
                one(jTableOperator).getSelectedColumn(); will(returnValue(3));
                one(jTableOperator).getValueAt(2, 3); will(returnValue(cellValue));
            }});
            
            specify(context.getSelectedCellValue(), cellValue);
        }
    }
    
    public class OperatingOnTableWithColumnIndex extends OperatingOnTable {
        protected JTableOperator createMockJTableOperator() {
            column = "2";
            jTableOperator = mock(JTableOperator.class);
            checking(new Expectations() {{
                one(jTableOperator).findCell(with(instanceOf(ColumnIndexTableCellChooser.class))); will(returnValue(coordinates));
            }});
            
            return jTableOperator;
        }
    }
    
    public class OperatingOnTableWithColumnHeaderName extends OperatingOnTable {
        protected JTableOperator createMockJTableOperator() {
            column = "someColumn";
            jTableOperator = mock(JTableOperator.class);
            checking(new Expectations() {{
                one(jTableOperator).findCell(with(instanceOf(ColumnNameTableCellChooser.class))); will(returnValue(coordinates));
            }});
            
            return jTableOperator;
        }
    }
    
    @SuppressWarnings("unchecked")
    private <T> Matcher<T> instanceOf(Class<T> type) {
        return (Matcher<T>) new IsInstanceOf(type);
    }
    
    public abstract class OperatingOnTable {
        protected String column;
        protected abstract JTableOperator createMockJTableOperator();
        
        public DefaultTableOperator create() {
            return new DefaultTableOperator(createMockJTableOperator());
        }
        
        public void getsCellValue() {
            checking(new Expectations() {{
                one(jTableOperator).getValueAt(coordinates.y, coordinates.x); will(returnValue(cellValue));
            }});
            
            specify(context.getCellValue(row, column), cellValue);
        }
        
        public void checksCellIsSelected() {
            checking(new Expectations() {{
                one(jTableOperator).isCellSelected(coordinates.y, coordinates.x); will(returnValue(true));
            }});
            
            specify(context.isCellSelected(row, column));
        }
        
        public void selectsCell() {
            checking(new Expectations() {{
                one(jTableOperator).selectCell(coordinates.y, coordinates.x);
            }});
            
            context.selectCell(row, column);
        }
        
        public void setsCellValue() {
            final Object newValue = new Object();
            checking(new Expectations() {{
                one(jTableOperator).setValueAt(newValue, coordinates.y, coordinates.x);
            }});
            
            context.setCellValue(newValue, row, column);
        }
        
        public void typesIntoCell() {
            final Object newValue = new Object();
            checking(new Expectations() {{
                one(jTableOperator).changeCellObject(coordinates.y, coordinates.x, newValue);
            }});
            
            context.typeIntoCell(newValue, row, column);
        }
        
        public void clearsCell() {
            checking(new Expectations() {{
                one(jTableOperator).changeCellObject(coordinates.y, coordinates.x, "");
            }});
            
            context.clearCell(row, column);
        }
        
        public void callsPopupOnCell() {
            final JPopupMenu popupMenu = dummy(JPopupMenu.class);
            checking(new Expectations() {{
                one(jTableOperator).callPopupOnCell(coordinates.y, coordinates.x); will(returnValue(popupMenu));
            }});
            
            specify(context.callPopupOnCell(row, column).getSource(), popupMenu);
        }
    }
}
