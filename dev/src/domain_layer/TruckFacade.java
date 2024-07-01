package domain_layer;

import dataAccess_layer.TruckDAO;

import java.util.ArrayList;
import java.util.List;

public class TruckFacade {
    private final List<Truck> trucks;
    private final TruckDAO truckDAO;

    public TruckFacade(String dbPath) {
        trucks = new ArrayList<>();
        truckDAO = new TruckDAO(dbPath);
    }

    public void addTruck(int truckNumber, String model, float emptyWeight, float maxWeight) {
        Truck truck = new Truck(truckNumber, model, emptyWeight, maxWeight);
        truckDAO.create(truck);
        trucks.add(truck);
    }

    public List<Truck> getAvailableTrucks() {
        return trucks.stream().filter(Truck::isAvailable).toList();
    }

    public void loadAll() {
        List<Truck> trucks = truckDAO.readAll();
        this.trucks.addAll(trucks);
    }
}
