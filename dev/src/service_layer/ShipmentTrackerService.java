package service_layer;

import domain_layer.ShipmentScheduler;
import domain_layer.ShipmentTracker;
import domain_layer.TruckFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class ShipmentTrackerService {
    private List<ShipmentTracker> shipmentTrackers;
    private ShipmentScheduler shipmentScheduler;
    private TruckFacade truckFacade;
    public ShipmentTrackerService(ShipmentScheduler scheduler, TruckFacade truckFacade){
        this.shipmentScheduler = scheduler;
        this.shipmentTrackers = new ArrayList<>();
        this.truckFacade = truckFacade;
    }

    public Response<Object> trackShipment(int shipmentId){
        try {
            shipmentTrackers.add(new ShipmentTracker(shipmentScheduler.getShipment(shipmentId)));
        }
        catch (NoSuchElementException e){
            //return response error
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


}
