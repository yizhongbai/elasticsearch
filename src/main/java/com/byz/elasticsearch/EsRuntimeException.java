package com.byz.elasticsearch;

import java.io.Serializable;

/**
 * @author baiyizhong
 * @Description:
 * @date 2020-10-21 14:04
 */
public class EsRuntimeException extends RuntimeException implements Serializable {
    public EsRuntimeException() {
        super();
    }

    public EsRuntimeException(String message) {
        super(message);
    }

    public EsRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EsRuntimeException(Throwable cause) {
        super(cause);
    }

    protected EsRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
