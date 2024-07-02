package domain_layer;

import dataAccess_layer.TruckDAO;

import java.util.ArrayList;
import java.util.List;

public class TruckFacade {
    private final List<Truck> trucks;
    private final TruckDAO truckDAO;
    private boolean persist;

    public TruckFacade(boolean persist, String dbPath) {
        trucks = new ArrayList<>();
        truckDAO = new TruckDAO(dbPath);
        this.persist = persist;
    }

    public void addTruck(int truckNumber, String model, float emptyWeight, float maxWeight) {
        Truck truck = new Truck(truckNumber, model, emptyWeight, maxWeight);

        if (persist)
            truckDAO.create(truck);
        trucks.add(truck);
    }

    public List<Truck> getAvailableTrucks() {
        return trucks.stream().filter(Truck::isAvailable).toList();
    }
    public List<Truck> getTrucks() {return trucks;}

    public boolean loadAll() {
        List<Truck> trucks = truckDAO.readAll();
        this.trucks.addAll(trucks);
        return false;
    }
}
