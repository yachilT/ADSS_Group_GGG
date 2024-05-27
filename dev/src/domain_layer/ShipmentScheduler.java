package domain_layer;

import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public class ShipmentScheduler {
    int shipmentIds;
    private AreaFacade areaFacade;
    private DriverFacade driverFacade;
    private TruckFacade truckFacade;
    public ShipmentScheduler(AreaFacade areaFacade, DriverFacade driverFacade, TruckFacade truckFacade){
        this.areaFacade = areaFacade;
        this.driverFacade = driverFacade;
        this.truckFacade = truckFacade;
    }
    public Shipment scheduleShipment() throws Exception {
        Pair<Driver, Truck> pair = findTruckAndDriver();
        Driver driver = pair.first;
        Truck truck = pair.second;
        return null;

    }
    private Pair<Driver,Truck> findTruckAndDriver() throws  Exception{
        for(Truck truck : truckFacade.getAvailableTrucks()){
            for(Driver driver : driverFacade.getAvailableDrivers()){
                if(truck.isCompatible(driver))
                    return new Pair(driver, truck);
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

