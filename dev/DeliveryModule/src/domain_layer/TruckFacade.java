package domain_layer;

import dataAccess_layer.TruckDAO;

import java.util.ArrayList;
import java.util.List;

public class TruckFacade {

    private final TruckRepository truckRepository;

    public TruckFacade(boolean persist, String dbPath) {
        truckRepository = new TruckRepository(persist, dbPath);
    }

    public void addTruck(int truckNumber, String model, float emptyWeight, float maxWeight) {
        truckRepository.addTruck(new Truck(truckNumber, model, emptyWeight, maxWeight));
    }

    public List<Truck> getAvailableTrucks() {
        return truckRepository.getAvailableTrucks().stream().toList();
    }
    public List<Truck> getTrucks() {return truckRepository.getTrucks();}

    public void loadAll() {
        truckRepository.loadAll();
    }
}
