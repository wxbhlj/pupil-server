package com.vivid.common.exception.handler;


import org.apache.commons.lang3.StringUtils;

import java.util.Properties;


public class ErrorHandler {

  
    private static Properties errorCodesProps;

    private ErrorHandler() {
    }


 
    public static String getErrMsg(String errId) {

        String errMsg = "";

        if (errorCodesProps != null) {
            errMsg = errorCodesProps.getProperty(errId);
        }

        if (StringUtils.isBlank(errMsg)) {
            errMsg = errId;
        }

        return errMsg;
    }


    public static String getErrMsg(String errId, Object... args) {

        String errMsg = "";

        if (errorCodesProps != null) {
            errMsg = errorCodesProps.getProperty(errId);
        }

        if (StringUtils.isBlank(errMsg)) {
            errMsg = errId;
        } else {
            errMsg = String.format(errMsg, args);
        }

        return errMsg;
    }

    public static void loadErrorCodes(Properties props) {
        errorCodesProps = props;
    }
}
