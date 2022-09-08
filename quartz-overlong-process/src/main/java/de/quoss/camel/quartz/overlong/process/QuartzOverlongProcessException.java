package de.quoss.camel.quartz.overlong.process;

public class QuartzOverlongProcessException extends RuntimeException {

    public QuartzOverlongProcessException(final String s) {
        super(s);
    }

    public QuartzOverlongProcessException(final Throwable t) {
        super(t);
    }

    public QuartzOverlongProcessException(final String s, final Throwable t) {
        super(s, t);
    }

}
