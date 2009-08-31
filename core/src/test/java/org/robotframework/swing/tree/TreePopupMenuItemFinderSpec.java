package org.robotframework.swing.tree;

import javax.swing.JMenuItem;
import javax.swing.JTree;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.arguments.IdentifierHandler;
import org.robotframework.jdave.mock.MockSupportSpecification;

import abbot.finder.BasicFinder;
import abbot.finder.matchers.JMenuItemMatcher;
import abbot.tester.ComponentTester;
import abbot.tester.JTreeLocation;


@RunWith(JDaveRunner.class)
public class TreePopupMenuItemFinderSpec extends MockSupportSpecification<TreePopupMenuItemFinder> {
    public class Any {
        private JTree tree = dummy(JTree.class);
        private JMenuItem menuItem = dummy(JMenuItem.class);

        public TreePopupMenuItemFinder create() {
            return new TreePopupMenuItemFinder(tree);
        }

        public void findsPopupMenuItemWithTreeRowIndexAndMenuPath() throws Exception {
            injectMockTreeLocationFactory();

            final BasicFinder basicFinder = injectMockToContext(BasicFinder.class);

            checking(new Expectations() {{
                one(basicFinder).find(with(any(JMenuItemMatcher.class)));
                will(returnValue(menuItem));
            }});

            specify(context.findMenu("some|node", "some|menu"), must.equal(menuItem));
        }

        private void injectMockTreeLocationFactory() {
            final IdentifierHandler treeLocationFactory = injectMockToContext("treeLocationFactory", IdentifierHandler.class);
            final JTreeLocation treeLocation = mock(JTreeLocation.class);
            final ComponentTester componentTester = injectMockToContext("componentTester", ComponentTester.class);

            checking(new Expectations() {{
                one(treeLocationFactory).parseArgument("some|node");
                will(returnValue(treeLocation));
                one(componentTester).actionShowPopupMenu(tree, treeLocation);
            }});
        }
    }
}
