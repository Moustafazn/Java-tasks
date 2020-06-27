package com.task.model;

public class Shift {

    private final double startInMinutes;
    private double endInMinutes;

    public Shift(double startInMinutes, double endInMinutes) {
        this.startInMinutes = startInMinutes;
        this.endInMinutes = endInMinutes;
    }

    public double getStartInMinutes() {
        return startInMinutes;
    }

    public double getEndInMinutes() {
        return endInMinutes;
    }
}
