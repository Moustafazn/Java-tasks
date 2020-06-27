package com.task.model;

public class AvailableBus {
    private final Bus bus;
    private final double distanceToRide;
    private final double waitingTime;

    public AvailableBus(Bus bus, double distanceToRide, double travelTime) {
        this.bus = bus;
        this.distanceToRide = distanceToRide;
        this.waitingTime = travelTime;
    }

    public Bus getBus() {
        return bus;
    }

    public double getDistanceToRide() {
        return distanceToRide;
    }

    public double getWaitingTime() {
        return waitingTime;
    }
}
