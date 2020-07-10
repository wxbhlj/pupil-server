package com.vivid.common.exception;

public class ServiceException extends BaseException {

    private static final long serialVersionUID = -360277845666981697L;

    public ServiceException() {
        super();
    }

    public ServiceException(String errorMsg) {
        super(errorMsg);
    }

    public ServiceException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

}
