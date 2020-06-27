package com.task.util;


import com.task.model.Location;

public class TaskUtil {

    private static final double R = 6371.0088; // Radious of the earth
    private static final int SPEED = 60;


    public static Double calculateHaversineDistance(Location StartLoc, Location endLoc){
        double latDistance = toRad(endLoc.getLat()-StartLoc.getLat());
        double lonDistance = toRad(endLoc.getLng()-StartLoc.getLng());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(StartLoc.getLat())) * Math.cos(toRad(endLoc.getLat())) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    public static Double calculateTravelTime(double distance){
        return (distance / SPEED);
    }

    public static Double calculateObjectiveFun(int numberOfAssignedTrips, double totalWastedDistance, double totalWaitingTime){
        return 60 * numberOfAssignedTrips - 3 * totalWastedDistance - totalWaitingTime;
    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }


}
