package org.robotframework.swing.common;

import org.netbeans.jemmy.Timeouts;
import org.netbeans.jemmy.operators.JComponentOperator;

public class TimeoutCopier {

    private JComponentOperator operator;
    private String timeout;

    public TimeoutCopier(JComponentOperator operator, String timeout) {
        this.operator = operator;
        this.timeout = timeout;
    }
    
    public Timeouts getTimeouts() {
        Timeouts timeouts = operator.getTimeouts();
        Timeouts times = timeouts.cloneThis();
        times.setTimeout("Waiter.WaitingTime", timeouts.getTimeout(timeout));
        return times;
    }
}
