package org.xuanli.daintydoc;

public class DaintyDocException extends RuntimeException {
    public DaintyDocException(String s) {
        super(s);
    }

    public DaintyDocException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DaintyDocException(Throwable throwable) {
        super(throwable);
    }

    public DaintyDocException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
