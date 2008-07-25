package org.robotframework.swing.keyword.component;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.keyword.component.ComponentKeywords;
import org.robotframework.swing.util.IComponentConditionResolver;


@RunWith(JDaveRunner.class)
public class ComponentKeywordsSpec extends MockSupportSpecification<ComponentKeywords> {
    public class Any {
        public ComponentKeywords create() {
            return new ComponentKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasOperatorFactory() {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }

        public void hasComponentExistenceResolver() {
            specify(context, satisfies(new FieldIsNotNullContract("componentExistenceResolver")));
        }

        public void hasComponentShouldNotExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("componentShouldNotExist")));
        }

        public void hasComponentShouldExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("componentShouldExist")));
        }
    }

    public class Operating {
        private String componentIdentifier = "someComponent";
        private IComponentConditionResolver componentExistenceResolver;
        private ComponentKeywords componentKeywords;

        public ComponentKeywords create() {
            componentKeywords = new ComponentKeywords();
            injectMockComponentExistenceResolverToKeywords();
            return componentKeywords;
        }

        public void componentShouldNotExistPassesIfComponentIsNotFound() throws Throwable {
            checking(new Expectations() {{
                one(componentExistenceResolver).satisfiesCondition(componentIdentifier);
                will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.componentShouldNotExist(componentIdentifier);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void componentShouldNotExistFailsIfComponentIsFound() throws Throwable {
            checking(new Expectations() {{
                one(componentExistenceResolver).satisfiesCondition(componentIdentifier);
                will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.componentShouldNotExist(componentIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Component '" + componentIdentifier + "' exists"));
        }

        public void componentShouldExistPassesIfComponentIsFound() throws Throwable {
            checking(new Expectations() {{
                one(componentExistenceResolver).satisfiesCondition(componentIdentifier);
                will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.componentShouldExist(componentIdentifier);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void componentShouldExistFailsIfComponentIsNotFound() {
            checking(new Expectations() {{
                one(componentExistenceResolver).satisfiesCondition(componentIdentifier);
                will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.componentShouldExist(componentIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Component '" + componentIdentifier + "' does not exist"));
        }

        private void injectMockComponentExistenceResolverToKeywords() {
            componentExistenceResolver = injectMockTo(componentKeywords, "componentExistenceResolver", IComponentConditionResolver.class);
        }
    }
}
