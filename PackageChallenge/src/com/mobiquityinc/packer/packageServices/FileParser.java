/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiquityinc.packer.packageServices;

import com.mobiquityinc.packer.Packer;
import com.mobiquityinc.packer.utils.CommonUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.mobiquityinc.packer.beans.Package;
import com.mobiquityinc.packer.beans.Thing;
import com.mobiquityinc.packer.constants.Constraints;
import com.mobiquityinc.packer.constants.ErrorCode;
import com.mobiquityinc.packer.exceptionHandlers.APICheckedException;
import com.mobiquityinc.packer.exceptionHandlers.APIUncheckedException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class id developed to read file and parse the file content into package
 * and things
 *
 * @author Moustafa
 */
public class FileParser {

    /**
     * read file content and call the singleton object of {@link PackageHandler}
     * functions to insert packages
     *
     * @param filePath is the file path received by the {@link Packer} class
     * @throws Exception APICheckedException or APIUncheckedException
     */
    public static void readFileandFillPackages(String filePath) throws Exception {
        if (filePath != null || !filePath.isEmpty()) {
            File file = new File(filePath);
            if (file.exists() && file.length() > 0) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Package packageDetails = parsePackageDetails(line.split(" : "));
                        if (packageDetails.getPackageThinges() != null && !packageDetails.getPackageThinges().isEmpty()) {
                            PackageHandler.getInstance().insertPackage(packageDetails);
                        }
                    }
                    reader.close();
                } catch (Exception ex) {
                    if (ex instanceof APICheckedException || ex instanceof APIUncheckedException) {
                        throw ex;
                    } else {
                        throw new APICheckedException("Error in reading the file", ex, ErrorCode.INVALID_FILE_CONTENT);
                    }
                }
            } else {
                throw new APICheckedException("The file does not exsit or empty ", ErrorCode.FILE_EMPTY);
            }
        } else {
            throw new APICheckedException("The file path is not valid", ErrorCode.INVALID_FILE_PATH);
        }
    }

    /**
     * private method is developed to parse package string to {@link: Paxkage}
     *
     * @param packageInfoStr a variable contains package information
     * @return {@link: Package}
     * @throws Exception APIUncheckedException
     */
    private static Package parsePackageDetails(String[] packageInfoStr) throws Exception {
        Package packageDetails = new Package();
        double packageWeight = CommonUtil.getValidDouble(packageInfoStr[0]);
        if (packageWeight <= Constraints.PACKAGE_MAX_WEIGHT) {
            packageDetails.setPakageWeight(packageWeight);
            String[] thingInfoStr = packageInfoStr[1].split(" ");
            if (thingInfoStr.length > 0 && thingInfoStr.length <= Constraints.NO_OF_ITEMS) {
                packageDetails.setPackageThinges(parseThings(thingInfoStr));
            } else {
                throw new APIUncheckedException("The number of things exceeds the maximum limit or less than one", ErrorCode.INVALID_ITEMS_NO);
            }
        } else {
            throw new APIUncheckedException("package weight exceeds the maximum limit", ErrorCode.INVALID_PACKAGE_WEIGHT);
        }
        return packageDetails;
    }

    /**
     * private method is developed to parse things from lines
     *
     * @param thingInfoStr array of {@link String} of things data
     * @return list of {@link: Thing}
     * @throws Exception APIUncheckedException
     */
    private static List<Thing> parseThings(String[] thingInfoStr) throws Exception {
        List<Thing> packThings = new ArrayList<>();
        for (String elem : thingInfoStr) {
            String[] strArr = elem.substring(1, elem.length() - 1).split(",");
            int index = CommonUtil.getValidInteger(strArr[0]);
            double weight = CommonUtil.getValidDouble(strArr[1]);
            double cost = CommonUtil.getValidDouble(strArr[2].substring(1, strArr[2].length()));
            if (weight > Constraints.THING_MAX_WEIGHT) {
                throw new APIUncheckedException("Thing weight exceeds the maximum limit", ErrorCode.INVALID_THING_WEIGHT);
            } else if (cost > Constraints.THING_MAX_COST) {
                throw new APIUncheckedException("Thing cost exceeds the maximum limit", ErrorCode.INVALID_THING_SIZE);
            } else {
                packThings.add(new Thing(index, weight, cost));
            }
        }
        return packThings;
    }
}
