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

    public int getTruckNumber() {
        return truckNumber;
    }

    public float getMaxWeight() {
        return maxWeight;
    }

    @Override
    public String toString() {
        return "truckNumber: " + truckNumber +
                ", model: '" + model + '\'' +
                ", emptyWeight: " + emptyWeight +
                ", maxWeight: " + maxWeight + "kg";
    }
}
