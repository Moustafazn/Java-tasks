/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiquityinc.packer.utils;

import com.mobiquityinc.packer.constants.ErrorCode;
import com.mobiquityinc.packer.exceptionHandlers.APIUncheckedException;

/**
 *
 * @author Moustafa This class is used to validate the number format incase of
 * integer or double value;
 */
public class CommonUtil {

    /**
     * This function is used to parse a string value to integer
     *
     * @param numStr is the value that will be checked
     * @return the parsed integer value
     * @throws APIUncheckedException if the value is not valid
     */
    public static int getValidInteger(String numStr) throws APIUncheckedException {
        try {
            return Integer.parseInt(numStr);
        } catch (NumberFormatException ex) {
            throw new APIUncheckedException("Invalid Integer Number", ex, ErrorCode.INVALID_NO_FORMAT);
        }
    }

    /**
     * This function is used to parse a string value to double
     *
     * @param numStr is the value that will be checked
     * @return the parsed double value
     * @throws APIUncheckedException if the value is not valid
     */
    public static double getValidDouble(String numStr) throws APIUncheckedException {
        try {
            return Double.parseDouble(numStr);
        } catch (NumberFormatException ex) {
            throw new APIUncheckedException("Invalid Double Number", ex, ErrorCode.INVALID_NO_FORMAT);
        }
    }

}
