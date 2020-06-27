/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiquityinc.packer.exceptionHandlers;

import com.mobiquityinc.packer.constants.ErrorCode;

/**
 * This class is used to create a custom runtime exception
 *
 * @author Moustafa
 */
public class APIUncheckedException extends RuntimeException {

   
    //private final static Long serialVersionUID = 1L;
    // is an instance of ErrorCode enum
    private final ErrorCode code;

    /**
     * a constructor to create {@link APIUncheckedException}
     *
     * @param message shows the meaning of the exception
     * @param code is an instance of {@link ErrorCode}
     */
    public APIUncheckedException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    /**
     * a constructor to create {@link APIUncheckedException}
     *
     * @param message shows the meaning of the exception
     * @param cause is instance of {@link Throwable}
     * @param code is an instance of {@link ErrorCode}
     */
    public APIUncheckedException(String message, Throwable cause, ErrorCode code) {
        super(message, cause);
        this.code = code;
    }

    /**
     *
     * @return the error code {@link ErrorCode}
     */
    public ErrorCode getCode() {
        return code;
    }
}
