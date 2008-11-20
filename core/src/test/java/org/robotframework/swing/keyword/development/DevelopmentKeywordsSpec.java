package org.robotframework.swing.keyword.development;

import java.awt.Component;
import java.awt.Container;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.javalib.util.StdStreamRedirecter;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.operator.IOperator;


@RunWith(JDaveRunner.class)
public class DevelopmentKeywordsSpec extends Specification<DevelopmentKeywords> {
    public class Any {
        public DevelopmentKeywords create() {
            return new DevelopmentKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasListComponentsInContextKeyword() {
            specify(context, satisfies(new RobotKeywordContract("listComponentsInContext")));
        }
    }

    public class Debugging {
        public void printsComponentHierarchy() {
            final IOperator containerOperator = mock(IOperator.class);
            final Container container = mock(Container.class);
            final Component component1 = mock(Component.class, "comp1");
            final Component component2 = mock(Component.class, "comp2");
            checking(new Expectations() {{
                one(component1).getName(); will(returnValue("component1"));
                one(component2).getName(); will(returnValue(null));
                one(container).getName(); will(returnValue("root"));
                one(container).getComponents(); will(returnValue(new Component[] { component1, component2 }));
                one(containerOperator).getSource(); will(returnValue(container));
            }});

            Context.setContext(containerOperator);

            specifyComponentListingRepresentsComponentHierarchy(new DevelopmentKeywords());
        }

        private void specifyComponentListingRepresentsComponentHierarchy(DevelopmentKeywords devKeywords) {
            StdStreamRedirecter streamRedirecter = new StdStreamRedirecter();
            try {
               streamRedirecter.redirectStdStreams();

               String componentsAsString = devKeywords.listComponentsInContext();
               specify(componentsAsString, "[container, comp1, comp2]");

               String[] expectedOutputLines = new String[] { "0 container: root", "   1 comp1: component1", "   1 comp2: null" };
               String[] outputLines = streamRedirecter.getStdOutAsString().split("\n");

               for (int i = 0; i < outputLines.length; i++) {
                   specify(outputLines[i], must.equal(expectedOutputLines[i]));
               }
            } finally {
                streamRedirecter.resetStdStreams();
            }
        }
    }
}

