package service_layer;

import domain_layer.ShipmentHistory;
import domain_layer.ShipmentScheduler;
import domain_layer.ShipmentTracker;
import domain_layer.TruckFacade;

import java.util.*;
import java.util.stream.Collectors;

public class ShipmentTrackerService {
    private Map<Integer, ShipmentTracker> shipmentTrackers;
    private ShipmentScheduler shipmentScheduler;
    private TruckFacade truckFacade;
    private ShipmentHistory shipmentHistory;
    public ShipmentTrackerService(ShipmentScheduler scheduler, TruckFacade truckFacade, ShipmentHistory shipmentHistory){
        this.shipmentScheduler = scheduler;
        this.shipmentTrackers = new HashMap<>();
        this.truckFacade = truckFacade;
        this.shipmentHistory = shipmentHistory;
    }

    public Response<Object> trackShipment(int shipmentId){
        try {
            shipmentTrackers.put(shipmentId, new ShipmentTracker(shipmentScheduler.getShipment(shipmentId), shipmentHistory));
            return new Response<>();
        }
        catch (NoSuchElementException e){
            return new Response<>(e.getMessage());
        }
    }

    public Response<DestinationToSend> nextDestination(int shipmentId) {

        ShipmentTracker tracker = shipmentTrackers.get(shipmentId);
        if (tracker == null) {
            return new Response<>("Error: Shipment not found");
        }
        if (!tracker.hasNext()) {
            return new Response<>("Error: No more destinations");
        }

        return new Response<>(new DestinationToSend(tracker.next()));
    }

    public Response<Boolean> hasNext(int shipmentId) {
        ShipmentTracker tracker = shipmentTrackers.get(shipmentId);
        if (tracker == null) {
            return new Response<>("Error: Shipment not found");
        }
        return new Response<>(tracker.hasNext());
    }
    public Response<Object> updateWeight(int shipmentId, float newWeight) {
        ShipmentTracker tracker = shipmentTrackers.get(shipmentId);
        if (tracker == null) {
            return new Response<>("Error: Shipment not found");
        }
        tracker.setWeight(newWeight);
        return new Response<>();
    }

    public Response<Object> changeTruck(int shipmentId) {
        ShipmentTracker tracker = shipmentTrackers.get(shipmentId);
        if (tracker == null) {
            return new Response<>("Error: Shipment not found");
        }
        try {
            tracker.changeTruck(truckFacade);
            return new Response<>();
        }
        catch (NoSuchElementException e){
            return new Response<>(e.getMessage());
        }
    }

    public Response<Object> removeDestination(int shipmentId) {
        ShipmentTracker tracker = shipmentTrackers.get(shipmentId);
        if (tracker == null) {
            return new Response<>("Error: Shipment not found");
        }
        tracker.removeDestination();
        return new Response<>();

    }

    public Response<Object> changeDestination(int shipmentId, DestinationToSend newDst) {
        ShipmentTracker tracker = shipmentTrackers.get(shipmentId);
        if (tracker == null) {
            return new Response<>("Error: Shipment not found");
        }

        tracker.ChangeDestination(newDst.toDestination());
        return new Response<>();
    }

    public Response<Object> productsToRemain(int shipmentId, List<ProductToSend> remainingProducts) {
        ShipmentTracker tracker = shipmentTrackers.get(shipmentId);
        if (tracker == null) {
            return new Response<>("Error: Shipment not found");
        }

        tracker.productsToRemain(remainingProducts.stream().map(ProductToSend::toProductAmount).toList());
        return new Response<>();
    }
    public List<DestinationToSend> remainingDestinations(int shipmentId){
        return shipmentTrackers.get(shipmentId).getRemainingDestinations().stream().map(DestinationToSend::new).collect(Collectors.toList());
    }



}
