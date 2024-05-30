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

    public Response trackShipment(int shipmentId){
        try {
            shipmentTrackers.add(new ShipmentTracker(shipmentScheduler.getShipment(shipmentId)));
        }
        catch (NoSuchElementException e){
            //return response error
        }
    }

    public Response changeTruck() {

    }

    public Response removeDestination(SiteToSend site) {

    }

    public Response changeDestination(SiteToSend oldSite, SiteToSend newSite) {

    }

    public Response productsToRemain(List<ProductToSend> remainingProducts) {

    }


}
