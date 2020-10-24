package com.kupug.kuoauth;

/**
 * <p>
 * KuOauth 异常处理类
 * </p>
 *
 * @author MaoHai.Lv
 * @since 1.0
 */
public class KuOAuthException extends RuntimeException {

    private static final long serialVersionUID = -6773899856243145150L;

    public KuOAuthException(String message) {
        super(message);
    }

    public KuOAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
