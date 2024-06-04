package service_layer;

import domain_layer.*;

import java.util.*;
import java.util.stream.Collectors;

public class ShipmentTrackerService {
    private final Map<Integer, ShipmentTracker> shipmentTrackers;
    private final ShipmentScheduler shipmentScheduler;
    private final TruckFacade truckFacade;
    private final ShipmentHistory shipmentHistory;
    public ShipmentTrackerService(ShipmentScheduler scheduler, TruckFacade truckFacade, ShipmentHistory shipmentHistory){
        this.shipmentScheduler = scheduler;
        this.shipmentTrackers = new HashMap<>();
        this.truckFacade = truckFacade;
        this.shipmentHistory = shipmentHistory;
    }

    public Response<ShipmentToSend> trackShipment(int shipmentId){
        try {
            Shipment shipment = shipmentScheduler.getShipment(shipmentId);
            ShipmentTracker tracker = new ShipmentTracker(shipment, shipmentHistory);

            shipmentTrackers.put(shipmentId, tracker);
            return new Response<>(new ShipmentToSend(shipment));
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

        try {
            tracker.setWeight(newWeight);
        } catch (IllegalArgumentException e) {
            return new Response<>("Error: " + e.getMessage());
        }
        return new Response<>();
    }

    public Response<TruckToSend> changeTruck(int shipmentId, float newWeight) {
        ShipmentTracker tracker = shipmentTrackers.get(shipmentId);
        if (tracker == null) {
            return new Response<>("Error: Shipment not found");
        }
        try {
            return new Response<>(new TruckToSend(tracker.changeTruck(truckFacade, newWeight)));
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


    public Response<Object> finishTracking(int shipmentId) {
        ShipmentTracker tracker = shipmentTrackers.get(shipmentId);
        if (tracker == null) {
            return new Response<>("Error: Shipment not found");
        }
        tracker.tryFinishShipment();
        shipmentTrackers.remove(shipmentId);
        return new Response<>();
    }
}
