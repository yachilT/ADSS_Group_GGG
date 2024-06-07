package domain_layer;
import service_layer.SiteToSend;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Shipment {
    private final Site origin;
    private final List<Destination> destinations;
    private int shipmentId;
    private LocalDateTime departureDateTime;
    private Truck truck;
    private Driver driver;
    public Shipment(int shipmentId, LocalDateTime departureDateTime, Site origin, List<Destination> destinations, Truck truck, Driver driver){
        this.shipmentId = shipmentId;
        this.departureDateTime = departureDateTime;

        this.origin = origin;
        this.destinations = destinations;

        this.truck = truck;
        this.truck.assignDelivery();

        this.driver = driver;
        this.driver.assignJob();
    }

    public void changeTruck(Truck truck) {
        this.truck.finishDelivery();
        this.truck = truck;
        this.truck.assignDelivery();
    }

    public List<Destination> removeDestination(int destinationIndex) {
        if (destinations.size() <= destinationIndex)
            throw new NoSuchElementException("Couldn't find destination to remove");
        destinations.remove(destinationIndex);
        return destinations.subList(destinationIndex, destinations.size());
    }
    public int getShipmentId() {
        return shipmentId;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public Destination getCurrentDestination(int currentDstIndex) {
        return destinations.get(currentDstIndex);
    }



    public Truck getTruck() {
        return truck;
    }

    public Driver getDriver() {
        return driver;
    }

    public List<Destination> changeDestination(int indexOld, int indexNew) {
        if (destinations.size() <= indexOld || destinations.size() <= indexNew)
            throw new NoSuchElementException("Couldn't find destination to change");

        Destination newDst = destinations.get(indexNew);
        destinations.set(indexNew, destinations.get(indexOld));
        destinations.set(indexOld, newDst);
        return destinations.subList(indexOld, destinations.size());
    }

    public void productsToRemain(List<ProductAmount> products, int currentDstIndex) {
        this.destinations.get(currentDstIndex).getProducts().removeIf(productAmount -> !products.contains(productAmount));
    }

    public int getDestinationsSize() {
        return destinations.size();
    }


    public List<Destination> getDestinationsFrom(int currentDstIndex) {
        return destinations.subList(currentDstIndex + 1, destinations.size());
    }
    public void setWeightForDst(int currentDstIndex, float newWeight) throws IllegalArgumentException {
        if (truck.isOverweight(newWeight))
            throw new IllegalArgumentException("Weight exceeds truck capacity: " + truck.getMaxWeight());
        destinations.get(currentDstIndex).setWeight(newWeight);
    }

    public void finish() {
        truck.finishDelivery();
        driver.finishJob();
    }

    public ShipmentDocument createDocument() {
        return new ShipmentDocument(shipmentId,
                origin.getAddress(), origin.getContactName(), origin.getContactNumber(),
                departureDateTime.toLocalDate().toString(), departureDateTime.toLocalTime().toString(),
                truck.getNumber(), driver.getName());
    }

    public List<DestinationDocument> createDestinationDocuments() {
        List<DestinationDocument> destinationDocuments = new ArrayList<>();
        for (int i = 0; i < destinations.size(); i++) {
            destinationDocuments.add(destinations.get(i).createDocument(i, shipmentId));
        }
        return destinationDocuments;
    }

    public Site getOrigin() {
        return origin;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }
}
