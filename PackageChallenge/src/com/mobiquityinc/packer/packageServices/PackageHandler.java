/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiquityinc.packer.packageServices;

import java.util.List;
import com.mobiquityinc.packer.beans.Package;
import com.mobiquityinc.packer.beans.Thing;
import com.mobiquityinc.packer.exceptionHandlers.APIUncheckedException;
import com.mobiquityinc.packer.utils.CommonUtil;
import java.util.ArrayList;

/**
 * This class is used to handle the solution of problem which represents the
 * core of the project
 *
 * @author Moustafa
 */
public class PackageHandler {

    //early initialization for an object of {@link PackageHandler} is used to implement the singleton design pattern
    private static PackageHandler packageHandler = new PackageHandler();
    private List<Package> packages;

    /**
     * a private constructor is used to protect the class from initializing an
     * object in any other class and implements Singleton design pattern
     */
    private PackageHandler() {
        super();
        packages = new ArrayList<>();
    }

    /**
     * this method is used to access {@link PackageHandler} and this the only
     * way to access it.
     *
     * @return the initialized instance of {@link PackageHandler}
     */
    public static PackageHandler getInstance() {
        return packageHandler;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void insertPackage(Package packageDetails) {
        packages.add(packageDetails);
    }

    /**
     * get optimal things for each package.
     *
     * @return list of the selected solution
     */
    public String getOptimalPackage() throws APIUncheckedException {
        StringBuilder result = new StringBuilder();
        packages.forEach((pack) -> {
            double maxWeight = 0, maxCost = 0;
            String tempStr = "";
            List<Thing> things = pack.getPackageThinges();
            for (int i = 1; i <= things.size(); i++) {
                String returnedRes = getOptimalThing(things, i, pack.getPakageWeight());
                String[] res = returnedRes.split(",");
                double cost = CommonUtil.getValidDouble(res[res.length - 2]);
                double weight = CommonUtil.getValidDouble(res[res.length - 1]);
                if ((cost == maxCost && weight < maxWeight) || cost > maxCost) {
                    maxCost = cost;
                    maxWeight = weight;
                    tempStr = returnedRes;
                }
            }
            String[] finalStrArr = tempStr.split(",");
            String bestChoice = "";
            for (int i = 0; i < finalStrArr.length - 2; i++) {
                bestChoice += things.get(CommonUtil.getValidInteger(finalStrArr[i])).getIndex() + ",";
            }
            if ("".equals(bestChoice)) {
                result.append("-").append(System.getProperty("line.separator"));
            } else {
                result.append(bestChoice.substring(0, bestChoice.length() - 1)).append(System.getProperty("line.separator"));
            }
        });
        return result.toString();
    }

    /**
     * check the possible and the best combination for all thing in package
     *
     * @param things
     * @param r
     * @param PackWeight
     * @return the optimal solution of things weight and cost.
     */
    private String getOptimalThing(List<Thing> things, int r, double PackWeight) {
        String returnedRes = "";
        double maxCost = 0;
        double maxWeight = 0;
        int selectedIndex = 0;
        int[] data = new int[r];
        int[] arr = new int[things.size()];
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < things.size(); i++) {
            arr[i] = i;
        }
        getCombinations(arr, data, res, 0, 0);
        for (int i = 0; i <= res.size() - r; i += r) {
            double selectedWeight = 0;
            double selectedCost = 0;
            for (int j = 0; j < r; j++) {
                selectedWeight += things.get(res.get(j + i)).getWeight();
                selectedCost += things.get(res.get(j + i)).getCost();
            }
            if (selectedWeight <= PackWeight) {
                if (selectedCost > maxCost || ((selectedCost == maxCost) && (selectedWeight <= maxWeight))) {
                    selectedIndex = i;
                    maxWeight = selectedWeight;
                    maxCost = selectedCost;
                }
            }
        }
        for (int k = selectedIndex; k < r + selectedIndex; k++) {
            returnedRes += res.get(k) + ",";
        }
        return returnedRes + maxCost + "," + maxWeight;
    }

    /**
     * get all combinations for elements in things arrays
     *
     * @param arr
     * @param data
     * @param res
     * @param index
     * @param start
     */
    private static void getCombinations(int arr[], int data[], List<Integer> res, int start, int index) {
        if (index == data.length) {
            for (int j = 0; j < data.length; j++) {
                res.add(data[j]);
            }
            return;
        }
        for (int i = start; i < arr.length && arr.length - i >= data.length - index; i++) {
            data[index] = arr[i];
            getCombinations(arr, data, res, i + 1, index + 1);
        }
    }
}
