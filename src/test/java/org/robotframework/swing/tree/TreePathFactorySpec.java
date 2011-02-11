package org.robotframework.swing.tree;

import javax.swing.tree.TreePath;

import jdave.Block;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.robotframework.jdave.mock.MockSupportSpecification;


@RunWith(JDaveRunner.class)
public class TreePathFactorySpec extends MockSupportSpecification<TreePathFactory> {
    private String nodePath = "some|node";

    public class CreatingTreePath {
        private TreeOperator treeOperator;
        private TreePath treePath = dummy(TreePath.class);

        public TreePathFactory create() {
            treeOperator = mock(TreeOperator.class);
            return new TreePathFactory(treeOperator);
        }

        public void createsTreePathWithNodePath() {
            checking(new Expectations() {{
                one(treeOperator).findPath(nodePath); will(returnValue(treePath));
            }});

            specify(context.createTreePath("some|node"), must.equal(treePath));
        }

        public void createsTreePathWithRowIndex() {
            checking(new Expectations() {{
                one(treeOperator).getPathForRow(2); will(returnValue(treePath));
            }});

            specify(context.createTreePath("2"), must.equal(treePath));
        }

        public void treePathCreationFailsIfRowIndexCannotBeFound() {
            checking(new Expectations() {{
                one(treeOperator).getPathForRow(2); will(returnValue(null));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.createTreePath("2");
                }
            }, must.raiseExactly(TimeoutExpiredException.class, "Couldn't find tree path for row '2'"));
        }
    }
}
