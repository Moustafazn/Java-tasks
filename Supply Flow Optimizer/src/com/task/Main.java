package com.task;

import com.task.service.SupplyFlowOptService;

public class Main {

    public static void main(String[] args) {
        SupplyFlowOptService  supplyFlowOptService = new SupplyFlowOptService("/home/moustafa/Hard Data/Moustafa files/Moustaf Zein - Task 2/Part Three/inputs/s6.in",
                "/home/moustafa/Hard Data/Moustafa files/Moustaf Zein - Task 2/Part Three/outputs/s6.out");
        try {
            supplyFlowOptService.readFileDate();
            supplyFlowOptService.applyOptimizationAlgorithm();
            supplyFlowOptService.writeResultsToFile();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
