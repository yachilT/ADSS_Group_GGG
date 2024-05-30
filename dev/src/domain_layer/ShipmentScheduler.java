package domain_layer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class ShipmentScheduler {
    int shipmentIds;
    private List<Shipment> shipments;
    private AreaFacade areaFacade;
    private final DriverFacade driverFacade;
    private final TruckFacade truckFacade;
    public ShipmentScheduler(AreaFacade areaFacade, DriverFacade driverFacade, TruckFacade truckFacade){
        this.areaFacade = areaFacade;
        this.driverFacade = driverFacade;
        this.truckFacade = truckFacade;
        this.shipments = new ArrayList<>();
    }
    public Shipment scheduleShipment(Site origin, List<Destination> destinations) throws NoSuchElementException {
        Pair<Driver, Truck> pair = findTruckAndDriver();
        Driver driver = pair.first;
        Truck truck = pair.second;
        return new Shipment(shipmentIds++, LocalDateTime.now(), origin, destinations, truck, driver);
    }
    private Pair<Driver,Truck> findTruckAndDriver() throws  NoSuchElementException{
        for(Truck truck : truckFacade.getAvailableTrucks()){
            for(Driver driver : driverFacade.getAvailableDrivers()){
                if(truck.isCompatible(driver))
                    return new Pair<>(driver, truck);
            }
        }
        throw new NoSuchElementException("No trucks and driver available.");
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

