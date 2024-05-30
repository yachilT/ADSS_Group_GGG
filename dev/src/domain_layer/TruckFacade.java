package domain_layer;

import java.util.ArrayList;
import java.util.List;

public class TruckFacade {
    private final List<Truck> trucks;

    public TruckFacade() {
        trucks = new ArrayList<>();
    }

    public void addTruck(int truckNumber, String model, float emptyWeight, float maxWeight) {
        trucks.add(new Truck(truckNumber, model, emptyWeight, maxWeight));
    }

    public List<Truck> getAvailableTrucks() {
        return trucks.stream().filter(Truck::isAvailable).toList();
    }
}
