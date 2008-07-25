package org.robotframework.swing.tree;

import javax.swing.tree.TreePath;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.tree.JTreeLocationFactory;


import abbot.tester.JTreeLocation;

@RunWith(JDaveRunner.class)
public class JTreeLocationFactorySpec extends Specification<JTreeLocationFactory> {
    public class Any {
        public JTreeLocationFactory create() {
            return new JTreeLocationFactory();
        }

        public void createsJTreeLocationWithRowIndex() {
            specify(context.parseArgument("0"), must.equal(new JTreeLocation(0)));
        }

        public void createsJTreeLocationWithTreePath() {
            String treePath = "some|path";
            specify(context.parseArgument(treePath), must.equal(new JTreeLocation(new TreePath(treePath.split("\\|")))));
        }
    }
}
