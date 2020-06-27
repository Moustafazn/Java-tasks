/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiquityinc.packer.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to save package details (its weight and things) I used a
 * {@link List} as a data structure because it's a an efficient data structure
 * in adding and getting values
 *
 * @author Moustafa
 */
public class Package {

    private double pakageWeight;
    private List<Thing> packageThinges;

    public Package() {
        // initialize the list of things
        packageThinges = new ArrayList<>();
    }

    public double getPakageWeight() {
        return pakageWeight;
    }

    public void setPakageWeight(double pakageWeight) {
        this.pakageWeight = pakageWeight;
    }

    /**
     *
     * @return {@link List} of {@link Thing}
     */
    public List<Thing> getPackageThinges() {
        return packageThinges;
    }

    /**
     * set list of things {@link Thing}
     *
     * @param packageThinges
     */
    public void setPackageThinges(List<Thing> packageThinges) {
        this.packageThinges = packageThinges;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (int) (Double.doubleToLongBits(this.pakageWeight) ^ (Double.doubleToLongBits(this.pakageWeight) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Package other = (Package) obj;
        return Double.doubleToLongBits(this.pakageWeight) == Double.doubleToLongBits(other.pakageWeight);
    }

}
