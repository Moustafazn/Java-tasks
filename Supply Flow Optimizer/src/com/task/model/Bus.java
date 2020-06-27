package com.task.model;


import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Bus{
    private int id;
    private final Location garageLocation;
    private Location lastLocation;
    private double readyTime;
    private final Shift shift;
    private final Set<Ride> rides = new LinkedHashSet<>();
    private int numberOfTrips;

    public Bus(int id, Location garageLocation, Shift shift) {
        this.id = id;
        this.garageLocation = garageLocation;
        this.lastLocation = new Location(garageLocation.getLat(), garageLocation.getLng());
        this.shift = shift;
        this.readyTime = 0d;
    }

    public void setRide(Ride ride) {
        rides.add(ride);
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public double getReadyTime() {
        return readyTime;
    }

    public void setReadyTime(double readyTime) {
        this.readyTime = readyTime;
    }

    public void setNumberOfTrips(int numberOfTrips) {  this.numberOfTrips = numberOfTrips; }

    public int getNumberOfTrips() {
        return rides.size();
    }

    public Location getGarageLocation() {
        return garageLocation;
    }

    public Set<Ride> getRides() {
        return rides;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return id == bus.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
