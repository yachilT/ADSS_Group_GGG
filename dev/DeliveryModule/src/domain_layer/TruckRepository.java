package domain_layer;

import dataAccess_layer.TruckDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TruckRepository {
    private final Set<Truck> trucks;
    private final TruckDAO truckDAO;
    private boolean persist;
    public TruckRepository(boolean persist, String dbPath){
        this.trucks = new HashSet<>();
        this.truckDAO = new TruckDAO(dbPath);
        this.persist = persist;
    }
    public void addTruck(Truck truck) {

        if (persist)
            truckDAO.create(truck);
        trucks.add(truck);
    }

    public List<Truck> getAvailableTrucks() {
        return trucks.stream().filter(Truck::isAvailable).toList();
    }
    public List<Truck> getTrucks() {return trucks.stream().toList();}

    public void loadAll() {
        List<Truck> trucks = truckDAO.readAll();
        this.trucks.addAll(trucks);
    }

}
