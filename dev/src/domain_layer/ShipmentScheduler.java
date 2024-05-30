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
    public ShipmentScheduler(DriverFacade driverFacade, TruckFacade truckFacade){
        this.driverFacade = driverFacade;
        this.truckFacade = truckFacade;
        this.shipments = new ArrayList<>();
    }
    public int scheduleShipment(Site origin, List<Destination> destinations) throws NoSuchElementException {
        Pair<Driver, Truck> pair = findTruckAndDriver();
        Driver driver = pair.first;
        Truck truck = pair.second;
        Shipment shipment = new Shipment(shipmentIds++, LocalDateTime.now(), origin, destinations, truck, driver);
        this.shipments.add(shipment);
        return shipment.getShipmentId();
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

    public Shipment getShipment(int shipmentId) {
        return shipments.stream().filter(s -> s.getShipmentId() == shipmentId).findFirst().orElseThrow(NoSuchElementException::new);
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

