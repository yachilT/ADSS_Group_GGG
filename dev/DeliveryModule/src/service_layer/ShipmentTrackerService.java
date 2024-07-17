package service_layer;

import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import domain_layer.*;
import interfaces.StorekeeperChecker;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ShipmentTrackerService {
    private final Map<Integer, ShipmentTracker> shipmentTrackers;
    private final ShipmentScheduler shipmentScheduler;
    private final TruckFacade truckFacade;
    private final ShipmentHistory shipmentHistory;
    private final StorekeeperChecker storekeeperChecker;
    public ShipmentTrackerService(ShipmentScheduler scheduler, TruckFacade truckFacade, ShipmentHistory shipmentHistory, StorekeeperChecker storekeeperChecker) {
        this.shipmentScheduler = scheduler;
        this.shipmentTrackers = new HashMap<>();
        this.truckFacade = truckFacade;
        this.shipmentHistory = shipmentHistory;
        this.storekeeperChecker = storekeeperChecker;
    }

    public Response<ShipmentToSend> trackShipment(int shipmentId){
        try {
            Shipment shipment = shipmentScheduler.departShipment(shipmentId);
            if (shipment.checkStorekeeper(storekeeperChecker)) {
                return new Response<>("Error: Some destinations doesn't have storekeeper assigned");
            }
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

    public Response<List<DestinationToSend>> removeDestination(int shipmentId) {
        ShipmentTracker tracker = shipmentTrackers.get(shipmentId);
        if (tracker == null) {
            return new Response<>("Error: Shipment not found");
        }
        return new Response<>(tracker.removeDestination().stream().map(DestinationToSend::new).toList());

    }

    public Response<List<DestinationToSend>> changeDestination(int shipmentId, int relativeDstIndex) {
        ShipmentTracker tracker = shipmentTrackers.get(shipmentId);
        if (tracker == null) {
            return new Response<>("Error: Shipment not found");
        }

        return new Response<>(tracker.changeDestination(relativeDstIndex).stream().map(DestinationToSend::new).toList());
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
        tracker.finishShipment();
        shipmentTrackers.remove(shipmentId);
        return new Response<>();
    }
}
