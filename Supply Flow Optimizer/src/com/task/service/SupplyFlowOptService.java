package com.task.service;

import com.task.model.*;
import com.task.util.TaskUtil;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.task.util.TaskUtil.calculateObjectiveFun;
import static java.util.Comparator.*;

public class SupplyFlowOptService {

  private final String inputFilePath;
  private final String OutputFilePath;
  private List<Ride> rides;
  private List<Bus> buses;
  private double totalWastedDistance;
  private double totalWaitingTime;
  private int totalNumberOfAssignedTrip;
  private final int MAXIMUM_NUM_OF_TRIPS = 4;

  public SupplyFlowOptService(String inputFilePath, String OutputFilePath) {
    this.inputFilePath = inputFilePath;
    this.OutputFilePath = OutputFilePath;
    totalWastedDistance = 0d;
    totalWaitingTime = 0d;
    totalNumberOfAssignedTrip = 0;
  }

  public void readFileDate() throws Exception {
    File file = new File(inputFilePath);
    BufferedReader br = new BufferedReader(new FileReader(file));
    int[] firstLine = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
    this.readRidesFromFile(firstLine[0], br);
    this.readBusesFromFile(firstLine[1], br);
    br.close();
  }

  private void readRidesFromFile(int numberOfRides, BufferedReader br) throws Exception {
    rides = new ArrayList<>(numberOfRides);
    for (int i = 0; i < numberOfRides; i++) {
      double[] lineNumbers = Arrays.stream(br.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
      if(lineNumbers[2]>=360 && lineNumbers[4]<1200)
         rides.add(this.fileRideObjFromArray(lineNumbers, i + 1));
    }
    rides.sort(comparingDouble(obj -> obj.getTimeLine().getStartInMinutes()));
  }

  private Ride fileRideObjFromArray(double[] lineNumbers, int id) {
    return new Ride(id,
            new Location(lineNumbers[0], lineNumbers[1]),
            new Location(lineNumbers[3], lineNumbers[4]),
            new Shift(lineNumbers[2], lineNumbers[5]));
  }

  private void readBusesFromFile(int numberOfBuses, BufferedReader br) throws Exception {
    buses = new LinkedList<>();
    for (int i = 0; i < numberOfBuses; i++) {
      double[] lineNumbers = Arrays.stream(br.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
      buses.add(this.fileBusObjFromArray(lineNumbers, i + 1));
    }
  }

  private Bus fileBusObjFromArray(double[] lineNumbers, int id) {
    return new Bus(id,
            new Location(lineNumbers[0], lineNumbers[1]),
            new Shift(360, 1200));
  }

  public void applyOptimizationAlgorithm(){
    rides.forEach(ride -> {
      AvailableBus availableBus = null;
      for(Bus bus: buses){
        if(bus.getReadyTime()< ride.getTimeLine().getStartInMinutes() && bus.getNumberOfTrips()< MAXIMUM_NUM_OF_TRIPS) {
          double distance = TaskUtil.calculateHaversineDistance(bus.getLastLocation(), ride.getStartLocation());
          double waitingTime = 0d;
          if(bus.getReadyTime()==0){
            availableBus =  this.selectBestBus(availableBus, new AvailableBus(bus, distance, waitingTime));
          } else if(bus.getReadyTime()!=0 && bus.getReadyTime() + distance <= ride.getTimeLine().getStartInMinutes()){
            waitingTime  = ride.getTimeLine().getStartInMinutes() - (bus.getReadyTime() + distance);
            availableBus =  this.selectBestBus(availableBus, new AvailableBus(bus, distance, waitingTime));
          }
        }
      }
      this.updateBusAndRideInfo(ride, availableBus);
    });
    this.calculateDistanceToReachGarage();
  }

  private void updateBusAndRideInfo(Ride ride, AvailableBus availableBus) {
    if(availableBus != null) {
      totalWaitingTime += availableBus.getWaitingTime();
      totalWastedDistance += availableBus.getDistanceToRide();
      totalNumberOfAssignedTrip += 1;
      Bus selectedBus = availableBus.getBus();
      int index = buses.indexOf(selectedBus);
      buses.remove(selectedBus);
      selectedBus.setRide(ride);
      selectedBus.setLastLocation(ride.getEndLocation());
      selectedBus.setReadyTime(ride.getTimeLine().getEndInMinutes());
      selectedBus.setNumberOfTrips(selectedBus.getNumberOfTrips()+1);
      buses.add(index, selectedBus);
    }
  }

  private AvailableBus selectBestBus(AvailableBus currentAvailableBus, AvailableBus newAvailableBus){
    if (currentAvailableBus == null) return  newAvailableBus;
    int compare = comparing(AvailableBus::getDistanceToRide)
           .thenComparing(AvailableBus::getWaitingTime)
            .compare(currentAvailableBus, newAvailableBus);
    return  compare > 0 ? newAvailableBus: currentAvailableBus;
  }

  private void calculateDistanceToReachGarage() {
    buses.forEach(bus -> {
      if(bus.getLastLocation().getLat() != bus.getGarageLocation().getLat() || bus.getLastLocation().getLng() != bus.getGarageLocation().getLng()) {
        double distance = TaskUtil.calculateHaversineDistance(bus.getLastLocation(), bus.getGarageLocation());
        totalWastedDistance += distance;
      }
    });
  }

  public void writeResultsToFile() throws Exception{
    File file = new File(OutputFilePath);
    FileWriter fr = new FileWriter(file);
    BufferedWriter br = new BufferedWriter(fr);
    br.write(calculateObjectiveFun(totalNumberOfAssignedTrip, totalWastedDistance, totalWaitingTime)+ System.getProperty("line.separator"));
    for(Bus bus: buses){
     AtomicReference<String> data = new AtomicReference<>(bus.getNumberOfTrips() + " ");
     bus.getRides().forEach(ride -> data.set(data.get() + ride.getId() + " "));
     br.write(data + System.getProperty("line.separator"));
    }
    br.close();
    fr.close();
  }
}
