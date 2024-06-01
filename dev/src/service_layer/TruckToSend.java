package service_layer;

import domain_layer.Truck;

public class TruckToSend {
    private int truckNumber;
    private String model;
    private float emptyWeight;
    private float maxWeight;

    public TruckToSend(Truck truck) {
        this.truckNumber = truck.getTruckNumber();
        this.model = truck.getModel();
        this.emptyWeight = truck.getEmptyWeight();
        this.maxWeight = truck.getMaxWeight();
    }
}
