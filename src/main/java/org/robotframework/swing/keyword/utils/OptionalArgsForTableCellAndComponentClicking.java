package org.robotframework.swing.keyword.utils;

import org.robotframework.javalib.util.ArrayUtil;

import java.util.HashMap;
import java.util.Map;

public class OptionalArgsForTableCellAndComponentClicking {

    @SuppressWarnings("serial")
    private static final Map<String, String> keyAliases = new HashMap<String, String>() {{
        put("LEFT BUTTON", "BUTTON1_MASK");
        put("RIGHT BUTTON", "BUTTON2_MASK");
        put("CTRL", "CTRL_MASK");
        put("ALT", "ALT_MASK");
        put("ALT GR", "ALT_GRAPH_MASK");
        put("SHIFT", "SHIFT_MASK");
        put("META", "META_MASK");
    }};

    private String[] optionalArgs;

    public OptionalArgsForTableCellAndComponentClicking(String[] optionalArgs) {
        this.optionalArgs = optionalArgs;
    }

    public String clickCount() {
        if (clickCountSpecified())
            return getClickCount();
        return "1";
    }

    private boolean clickCountSpecified() {
        return optionalArgs.length > 0;
    }

    private String getClickCount() {
        return optionalArgs[0];
    }

    public String button() {
        if (buttonSpecified())
            return getButton();
        return "BUTTON1_MASK";
    }

    private boolean buttonSpecified() {
        return optionalArgs.length > 1;
    }

    private String getButton() {
        return keyMask(optionalArgs[1]);
    }

    private String keyMask(String arg) {
        String upperCasedArg = arg.toUpperCase();
        String keyMask = keyAliases.get(upperCasedArg);
        if (keyMask != null)
            return keyMask;
        return upperCasedArg;
    }

    public String[] keyModifiers() {
        if (keymodifiersSpecifiedIn())
            return getKeyModifiers();
        return new String[0];
    }

    private boolean keymodifiersSpecifiedIn() {
        return optionalArgs.length > 2;
    }

    private String[] getKeyModifiers() {
        return replaceAliasesIn(ArrayUtil.copyOfRange(optionalArgs, 2, optionalArgs.length));
    }

    private String[] replaceAliasesIn(String[] keyModifiers) {
        String[] mods = new String[keyModifiers.length];
        for(int i = 0; i < keyModifiers.length; i++)
            mods[i] = keyMask(keyModifiers[i]);
        return mods;
    }
}

