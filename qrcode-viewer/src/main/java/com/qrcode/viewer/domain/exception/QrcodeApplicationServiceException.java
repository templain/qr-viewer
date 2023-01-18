package com.qrcode.viewer.domain.exception;

public class QrcodeApplicationServiceException extends RuntimeException {
    public QrcodeApplicationServiceException(String message) {
        super(message);
    }

    public QrcodeApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
