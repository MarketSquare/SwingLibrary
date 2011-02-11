package org.robotframework.swing.keyword.testing;

import org.robotframework.javalib.util.StdStreamRedirecter;

public class RecordStdOut extends RunnerKeyword {
    protected Object executeKeyword() {
        StdStreamRedirecter stdStreamRedirecterImpl = new StdStreamRedirecter();
        stdStreamRedirecterImpl.redirectStdStreams();

        runKeyword();

        String stdout = stdStreamRedirecterImpl.getStdOutAsString();
        stdStreamRedirecterImpl.resetStdStreams();

        return stdout;
    }
}
