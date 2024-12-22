package com.atri.util;

public class RecordDeletionException extends RuntimeException {
    public RecordDeletionException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        // 禁用栈堆跟踪
        return this;
    }
}
