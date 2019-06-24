package com.shima.kalah.exception;

public class KalahException extends RuntimeException {
    public KalahException() {
    }

    public KalahException(String message) {
        super(message);
    }

    public KalahException(String message, Throwable cause) {
        super(message, cause);
    }

    public KalahException(Throwable cause) {
        super(cause);
    }

    public KalahException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
