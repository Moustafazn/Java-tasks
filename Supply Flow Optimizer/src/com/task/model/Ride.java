package com.task.model;

import java.util.Objects;

public class Ride{
    private int id;
    private Location startLocation;
    private Location endLocation;
    private Shift timeLine;

    public Ride(int id, Location startLocation, Location endLocation, Shift timeLine) {
        this.id = id;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.timeLine = timeLine;
    }

    public int getId() {
        return id;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public Shift getTimeLine() {
        return timeLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ride ride = (Ride) o;
        return id == ride.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
