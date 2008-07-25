package org.robotframework.swing.tree;

import javax.swing.tree.TreePath;

import jdave.Block;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.tree.EnhancedTreeOperator;
import org.robotframework.swing.tree.TreePathFactory;


@RunWith(JDaveRunner.class)
public class TreePathFactorySpec extends MockSupportSpecification<TreePathFactory> {
    private String nodePath = "some|node";

    public class Any {
        public TreePathFactory create() {
            return new TreePathFactory();
        }

        public void hasContextVerifier() {
            specify(context, satisfies(new FieldIsNotNullContract("contextVerifier")));
        }
    }

    public class CreatingTreePath {
        private EnhancedTreeOperator treeOperator;
        private TreePath treePath = dummy(TreePath.class);

        public TreePathFactory create() {
            TreePathFactory treePathFactory = new TreePathFactory();
            final IContextVerifier contextVerifier = injectMockTo(treePathFactory, IContextVerifier.class);
            checking(new Expectations() {{
                one(contextVerifier).verifyContext();
            }});

            treeOperator = mock(EnhancedTreeOperator.class);
            Context.setContext(treeOperator);

            return treePathFactory;
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
