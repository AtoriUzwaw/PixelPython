package com.atri.util;

/**
 * 自定义异常类，用于处理记录删除相关的异常。
 * 继承自 RuntimeException，表示该异常是运行时异常。
 */
public class RecordDeletionException extends RuntimeException {

    /**
     * 构造方法，初始化异常信息。
     *
     * @param message 异常消息，描述异常的具体内容
     */
    public RecordDeletionException(String message) {
        super(message);
    }

    /**
     * 重写 fillInStackTrace 方法，禁用栈跟踪的填充。
     * 这样可以提高性能，避免在异常发生时生成不必要的栈信息。
     *
     * @return 返回当前异常对象，禁止栈堆跟踪
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        // 禁用栈堆跟踪
        return this;
    }
}

