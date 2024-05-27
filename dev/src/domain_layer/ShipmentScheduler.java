package domain_layer;

import jdk.jshell.spi.ExecutionControl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ShipmentScheduler {
    int shipmentIds;
    private List<Shipment> shipments;
    private AreaFacade areaFacade;
    private DriverFacade driverFacade;
    private TruckFacade truckFacade;
    public ShipmentScheduler(AreaFacade areaFacade, DriverFacade driverFacade, TruckFacade truckFacade){
        this.areaFacade = areaFacade;
        this.driverFacade = driverFacade;
        this.truckFacade = truckFacade;
        this.shipments = new ArrayList<>();
    }
    public Shipment scheduleShipment(Map<Site, DestinationDocument> destinations) throws Exception {
        Pair<Driver, Truck> pair = findTruckAndDriver();
        Driver driver = pair.first;
        Truck truck = pair.second;
        return new Shipment(shipmentIds++, new Date(), destinations, truck, driver);
    }
    private Pair<Driver,Truck> findTruckAndDriver() throws  Exception{
        for(Truck truck : truckFacade.getAvailableTrucks()){
            for(Driver driver : driverFacade.getAvailableDrivers()){
                if(truck.isCompatible(driver))
                    return new Pair<>(driver, truck);
            }
        }
        throw new Exception("No trucks and driver available.");
    }
    private class Pair<D,B> {
        D first;
        B second;
        public Pair(D first, B second){
            this.first = first;
            this.second = second;
        }
    }
}

