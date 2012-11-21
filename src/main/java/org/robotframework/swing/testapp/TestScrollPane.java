package org.robotframework.swing.testapp;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

public class TestScrollPane extends JScrollPane {

    public TestScrollPane() {
        super();
        setName("testList");
        setPreferredSize(new Dimension(100, 100));
        java.util.List<JComboBox> comboList = getCombos(10);
        JPanel combos = new JPanel(new GridLayout(0,1));
        for (JComboBox combo:comboList)
            combos.add(combo);
        setViewportView(combos);
        setName("ScrollPaneWithComboBoxes");
    }

    private List<JComboBox> getCombos(int count) {
        List<JComboBox> results = new ArrayList<JComboBox>(count);
        for (int i=0;i<count;i++) {
            JComboBox c = new JComboBox(new String[]{"combo_"+i, "b", "c"});
            c.setName("combo_"+i);
            results.add(c);
        }
        return results;
    }
}
