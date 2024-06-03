package domain_layer;
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

    public void removeDestination(int destinationIndex) {
        if (destinations.size() <= destinationIndex)
            throw new NoSuchElementException("Couldn't find destination to remove");
        destinations.remove(destinationIndex);
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

    public void changeDestination(int indexToChange, Destination dst) {
        if (destinations.size() <= indexToChange)
            throw new NoSuchElementException("Couldn't find destination to change");
        if (destinations.contains(dst))
            throw new IllegalArgumentException("Destination already exists in the shipment");
        destinations.set(indexToChange, dst);
    }

    public void productsToRemain(List<ProductAmount> products, int currentDstIndex) {
        this.destinations.get(currentDstIndex).getProducts().removeIf(productAmount -> !products.contains(productAmount));
    }

    public int getDestinationsSize() {
        return destinations.size();
    }


    public List<Destination> getDestinationsFrom(int currentDstIndex) {
        return destinations.subList(currentDstIndex, destinations.size());
    }
    public void setWeightForDst(int currentDstIndex, float newWeight) {
        if (truck.isOverweight(newWeight))
            throw new IllegalArgumentException("Weight exceeds truck capacity");
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
}
