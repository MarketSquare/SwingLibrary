package org.robotframework.swing.chooser;

import java.awt.Component;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.chooser.ByNameComponentChooser;

@RunWith(JDaveRunner.class)
public class ByNameComponentChooserSpec extends Specification<ByNameComponentChooser> {
    private String componentName = "someComponent";
    private ByNameComponentChooser byNameComponentChooser;

    public void create() {
        byNameComponentChooser = new ByNameComponentChooser(componentName);
    }

    public class WithDescription {
        public ByNameComponentChooser create() {
            return byNameComponentChooser;
        }

        public void usesNameAsDescription() {
            specify(context.getDescription(), must.equal(componentName));
        }
    }

    public class WhenChoosing {
        private Component component;

        public ByNameComponentChooser create() {
            component = mock(Component.class);
            return byNameComponentChooser;
        }

        public void choosesIfNameMatches() {
            checking(new Expectations() {{
                one(component).getName(); will(returnValue(componentName));
            }});

            specify(context.checkComponent(component), must.equal(true));
        }

        public void doesntChooseIfNameDoesntMatch() {
            checking(new Expectations() {{
                one(component).getName(); will(returnValue("somethingElse"));
            }});

            specify(context.checkComponent(component), must.equal(false));
        }

        public void doesntChooseIfNameIsNull() {
            checking(new Expectations() {{
                one(component).getName(); will(returnValue(null));
            }});

            specify(context.checkComponent(component), must.equal(false));
        }
    }
}
