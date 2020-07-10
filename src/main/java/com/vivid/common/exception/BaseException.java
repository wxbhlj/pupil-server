package com.vivid.common.exception;

import com.vivid.common.exception.handler.ErrorHandler;

public class BaseException extends RuntimeException {


    /**
     * 自定义异常码
     **/
    private String errorCode;

    private String errorMsg;

    public BaseException() {
    }

    public BaseException(String errorCode) {
        super(ErrorHandler.getErrMsg(errorCode));
        this.errorCode = errorCode;
        this.errorMsg = ErrorHandler.getErrMsg(errorCode);
    }

    public BaseException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BaseException(Throwable exception) {
        super(exception);
    }

    public BaseException(String errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


}
 