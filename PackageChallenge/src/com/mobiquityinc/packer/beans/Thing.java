/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiquityinc.packer.beans;

/**
 * This class is the entity of thing that belongs to a package {@link Package}
 *
 * @author Moustafa
 */
public class Thing {

    private int index;
    private double weight;
    private double cost;

    public Thing() {
        super();
    }

    /**
     * create an object of thing with its properties
     *
     * @param index
     * @param weight
     * @param cost
     */
    public Thing(int index, double weight, double cost) {
        super();
        this.index = index;
        this.weight = weight;
        this.cost = cost;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.index;
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
        final Thing other = (Thing) obj;
        if (this.index != other.index) {
            return false;
        }
        return Double.doubleToLongBits(this.weight) == Double.doubleToLongBits(other.weight);
    }
}
