package com.kkb.Exception;

/**
 * 用于处理，在赛程信息更新时，输入了不存在的球队中文名而导致的错误
 */
public class NotFoundTeamChineseNameExcption extends Exception {

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public NotFoundTeamChineseNameExcption() {
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NotFoundTeamChineseNameExcption(String message) {
        super(message);
    }
}
