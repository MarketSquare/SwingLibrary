package org.robotframework.swing.tab;

import javax.swing.JTabbedPane;
import java.awt.Component;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.netbeans.jemmy.util.RegExComparator;
import org.robotframework.swing.chooser.ByNameComponentChooser;
import org.robotframework.swing.common.Identifier;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.operator.ComponentWrapper;

public class TabbedPaneOperator extends JTabbedPaneOperator implements ComponentWrapper {
    public TabbedPaneOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }

    public TabbedPaneOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public TabbedPaneOperator(JTabbedPane source) {
        super(source);
    }

    public TabbedPaneOperator(ContainerOperator container) {
        super(container);
    }

    public static TabbedPaneOperator newOperatorFor(String name) {
        Identifier identifier = new Identifier(name);
        ContainerOperator context = (ContainerOperator) Context.getContext();
        if (identifier.isRegExp())
            return new TabbedPaneOperator(context,
                    createRegExpComponentChooser(identifier.asString()));
        return new TabbedPaneOperator(context, new ByNameComponentChooser(name));
    }

    public static ComponentChooser createRegExpComponentChooser(
            String identifier) {
        return new JTabbedPaneOperator.JTabbedPaneFinder(new TabbedPaneOperator.JTabbedPaneChooser(identifier,
                new RegExComparator()));
    }

    public static class JTabbedPaneChooser implements ComponentChooser {
        String name;
        StringComparator comparator;

        public JTabbedPaneChooser(String lb, StringComparator comparator) {
            this.name = lb;
            this.comparator = comparator;
        }

        public boolean checkComponent(Component comp) {
            return comp instanceof JTabbedPane
                    && (comp.getName() != null)
                    && this.comparator.equals(comp.getName(), this.name);
        }

        public String getDescription() {
            return "JTabbedPane with name \"" + this.name;
        }
    }
}
