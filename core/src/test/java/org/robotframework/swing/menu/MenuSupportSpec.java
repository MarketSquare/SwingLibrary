package org.robotframework.swing.menu;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.comparator.EqualsStringComparator;


@RunWith(JDaveRunner.class)
public class MenuSupportSpec extends MockSupportSpecification<MenuSupport> {
    public class Any {
        private JMenuBarOperator menuBarOperator;

        public MenuSupport create() {
            menuBarOperator = mock(JMenuBarOperator.class);
            return new MenuSupport() {
                protected JMenuBarOperator menubarOperator() {
                    return menuBarOperator;
                }
            };
        }
        
        public void showsMenuItem() {
            final Sequence avoidInstability = sequence("avoidingInstability");
            
            final JMenuItemOperator menuItemOperator = mock(JMenuItemOperator.class);
            final EventTool eventTool = injectMockToContext(EventTool.class);
            final String menuPath = "some|menu";
            
            checking(new Expectations() {{
                one(menuBarOperator).showMenuItem(menuPath);
                will(returnValue(menuItemOperator)); inSequence(avoidInstability);
                one(menuItemOperator).setComparator(with(any(EqualsStringComparator.class))); inSequence(avoidInstability);
                one(eventTool).waitNoEvent(200); inSequence(avoidInstability);
                one(menuItemOperator).grabFocus(); inSequence(avoidInstability);
                one(eventTool).waitNoEvent(200); inSequence(avoidInstability);
            }});
            
            context.showMenuItem(menuPath);
        }
    }
}
