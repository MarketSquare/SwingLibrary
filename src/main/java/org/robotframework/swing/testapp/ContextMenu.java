package org.robotframework.swing.testapp;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ContextMenu extends JPopupMenu implements ActionListener {
    private static final Map<String, Operation> commands = new HashMap<String, Operation>();
    private volatile Component operatedComponent = null;
    
    public ContextMenu() {
        setOpaque(true);
        setLightWeightPopupEnabled(true);
        setName("contextMenu");
    }
    
    public ContextMenu add(String text, Operation command) {
        add(new ContextMenuItem(text, command));
        return this;
    }

    @Override
    public void show(Component invoker, int x, int y) {
        Delay.delay();
        operatedComponent = invoker;
        super.show(invoker, x, y);
    }
    
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        commands.get(command).perform(operatedComponent);
    }
    
    private class ContextMenuItem extends JMenuItem {
        public ContextMenuItem(String text, Operation command) {
            super(text);
            setName(text.toUpperCase());
            addActionListener(ContextMenu.this);
            setActionCommand(text);
            commands.put(text, command);
        }
    }
}
