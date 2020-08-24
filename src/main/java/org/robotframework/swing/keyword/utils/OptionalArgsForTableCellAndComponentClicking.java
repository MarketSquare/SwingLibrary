package org.robotframework.swing.keyword.utils;

import java.util.Arrays;
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

    private final String clickCountString;
    private final String buttonClick;
    private final String[] optionalArgs;

    public OptionalArgsForTableCellAndComponentClicking(String clickCountString, String buttonClick, String[] optionalArgs) {
        this.clickCountString = clickCountString;
        this.buttonClick = buttonClick;
        this.optionalArgs = optionalArgs;
    }

    public String clickCount() {
        return this.clickCountString;
    }

    public String button() {
        return keyMask(this.buttonClick);
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
        return optionalArgs.length > 0;
    }

    private String[] getKeyModifiers() {
        return replaceAliasesIn(Arrays.copyOfRange(optionalArgs, 0, optionalArgs.length));
    }

    private String[] replaceAliasesIn(String[] keyModifiers) {
        String[] mods = new String[keyModifiers.length];
        for(int i = 0; i < keyModifiers.length; i++)
            mods[i] = keyMask(keyModifiers[i]);
        return mods;
    }
}

