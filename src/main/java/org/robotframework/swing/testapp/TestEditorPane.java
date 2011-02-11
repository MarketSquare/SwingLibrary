package org.robotframework.swing.testapp;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class TestEditorPane extends JEditorPane {
    public static boolean clicked = false;
    public static String eventDescription;
    
    private static String HTML = "<html>" +
        " <head>" +
        "  </head>" +
        " <body>" +
        "   Apply this rule after an event arrives<br>&#160;&#160;&#160;&#160;&#160;&#160; from <a href=\"DeviceName\">Network" +
        "   Elements</a>" +
        " </body>" +
        "</html>";
    
    public TestEditorPane() {
        setName("testEditorPane");
        setEditable(false);
        setContentType("text/html");
        setText(HTML);
        addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    clicked = true;
                    eventDescription = e.getDescription();
                }
            }
        });
    }
}
