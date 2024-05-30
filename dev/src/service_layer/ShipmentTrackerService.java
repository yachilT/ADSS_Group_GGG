package service_layer;

import domain_layer.ShipmentScheduler;
import domain_layer.ShipmentTracker;
import domain_layer.TruckFacade;

import java.util.*;

public class ShipmentTrackerService {
    private Map<Integer, ShipmentTracker> shipmentTrackers;
    private ShipmentScheduler shipmentScheduler;
    private TruckFacade truckFacade;
    public ShipmentTrackerService(ShipmentScheduler scheduler, TruckFacade truckFacade){
        this.shipmentScheduler = scheduler;
        this.shipmentTrackers = new HashMap<>();
        this.truckFacade = truckFacade;
    }

    public Response<Object> trackShipment(int shipmentId){
        try {
            shipmentTrackers.put(shipmentId, new ShipmentTracker(shipmentScheduler.getShipment(shipmentId)));
            return new Response<>();
        }
        catch (NoSuchElementException e){
            return
        }
    }

    public Response<Object> changeTruck() {

    }

    public Response<Object> removeDestination(SiteToSend site) {

    }

    public Response<Object> changeDestination(SiteToSend oldSite, SiteToSend newSite) {

    }

    public Response<Object> productsToRemain(List<ProductToSend> remainingProducts) {

    }

    public Response<DestinationToSend> nextDestination(int shipmentId) {
        shipmentTrackers.
    }

    public Response<Boolean> hasNext(int shipmentId) {
        shipmentTrackers.getOrDefault()
    }


}
