package domain_layer;

import ServiceLayer.Driver;

public class Truck {
    private int truckNumber;
    private String model;
    private float emptyWeight;
    private float maxWeight;
    private boolean available;


    public Truck(int truckNumber, String model, float emptyWeight, float maxWeight) {
        this.truckNumber = truckNumber;
        this.model = model;
        this.emptyWeight = emptyWeight;
        this.maxWeight = maxWeight;
        this.available = true;
    }

    public boolean isCompatible(Driver driver) {
         return maxWeight <= driver.getLicense().allowedWeight;
    }

    public boolean isOverweight(float currentWeight) { return currentWeight > maxWeight; }

    public boolean isAvailable() { return available; }

    public void assignDelivery() {
        available = false;
    }

    public void finishDelivery() {
        available = true;
    }

    public int getTruckNumber() {
        return truckNumber;
    }

    public String getModel() {
        return model;
    }

    public float getEmptyWeight() {
        return emptyWeight;
    }

    public float getMaxWeight() {
        return maxWeight;
    }

    public int getNumber() {
        return truckNumber;
    }


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Truck))
            return false;
        Truck tObj = (Truck)obj;
        return truckNumber == tObj.truckNumber && model.equals(tObj.model) && emptyWeight == tObj.emptyWeight && maxWeight == tObj.maxWeight;
    }
}
