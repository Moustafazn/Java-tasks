/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiquityinc.packer;

import com.mobiquityinc.packer.packageServices.FileParser;
import com.mobiquityinc.packer.packageServices.PackageHandler;

/**
 * This class is used to run the algorithm. This problem is a knapsack problem
 * can be solved by dynamic programming or greedy algorithm
 *
 * @author Moustafa
 */
public class Packer {

    public static String pack(String filePath) throws Exception {
        try {
            FileParser.readFileandFillPackages(filePath);
            return PackageHandler.getInstance().getOptimalPackage();
        } catch (Exception e) {
           throw e;
        }
    }

}
