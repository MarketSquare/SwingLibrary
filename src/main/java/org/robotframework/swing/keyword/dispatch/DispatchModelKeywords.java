package org.robotframework.swing.keyword.dispatch;

import org.netbeans.jemmy.JemmyProperties;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

import java.util.Arrays;

@RobotKeywords
public class DispatchModelKeywords {

    public enum DispatchModel {
        QUEUE(JemmyProperties.QUEUE_MODEL_MASK),
        QUEUE_SHORTCUT(JemmyProperties.QUEUE_MODEL_MASK | JemmyProperties.SHORTCUT_MODEL_MASK),
        ROBOT(JemmyProperties.ROBOT_MODEL_MASK),
        ROBOT_SMOOTH(JemmyProperties.ROBOT_MODEL_MASK | JemmyProperties.SMOOTH_ROBOT_MODEL_MASK);

        public final int model;

        private DispatchModel(int model) {
            this.model = model;
        }

        public static DispatchModel fromInt(int model) {
            for (DispatchModel value: DispatchModel.values()) {
                if (value.model == model)
                    return value;
            }
            throw new RuntimeException("Unknown dispatch model "+model);
        }

        public static DispatchModel fromString(String model) {
            try {
                return DispatchModel.valueOf(model);
            } catch (IllegalArgumentException iae) {
                throw new IllegalArgumentException("Unknown Jemmy dispatch model "+model+".\n"
                +"Supported models are " + Arrays.toString(DispatchModel.values()));
            }
        }
    }

    @RobotKeyword("Sets the jemmy dispatching model.\n"
            + "The event dispatching is explained in https://jemmy.java.net/tutorial.html#robot\n"
            + "Possible models are ``QUEUE``, ``QUEUE_SHORTCUT`` (default), ``ROBOT``, ``ROBOT_SMOOTH``.\n"
            + "Returns the old dispatching model.\n\n"
            + "Example:\n"
            + "| `Set Jemmy Dispatch Model` | ROBOT |\n"
            + "| ${old model}= | `Set Jemmy Dispatch Model` | ROBOT_SMOOTH |\n")
    @ArgumentNames({"dispatch model"})
    public String setJemmyDispatchModel(String model) {
        DispatchModel oldModel = DispatchModel.fromInt(JemmyProperties.getCurrentDispatchingModel());
        DispatchModel newModel = DispatchModel.fromString(model);
        JemmyProperties.setCurrentDispatchingModel(newModel.model);
        return oldModel.name();
    }


}
