package org.robotframework.swing.util;

import org.netbeans.jemmy.TestOut;

import java.io.InputStream;
import java.io.PrintWriter;


public class StandardOutOutput extends TestOut {

    public final long starterThreadId;


    public StandardOutOutput() {
        super((InputStream)null, (PrintWriter)null, (PrintWriter)null);
        starterThreadId = Thread.currentThread().getId();
    }

    @Override
    public void print(String line) {
        log(line);
    }

    @Override
    public void printLine(String line) {
        log(line);
    }

    @Override
    public void printTrace(String text) {
        log(text);
    }

    @Override
    public void printErrLine(String line) {
        log(line);
    }

    @Override
    public void printError(String text) {
        log(text);
    }

    @Override
    public void printStackTrace(Throwable e) {
        log("Error in Jemmy:");
        e.printStackTrace(System.out);
    }

    private void log(String msg) {
        if (Thread.currentThread().getId() == starterThreadId)
            System.out.println("*DEBUG:"+System.currentTimeMillis()+"* Jemmy: "+ msg);
    }
}
