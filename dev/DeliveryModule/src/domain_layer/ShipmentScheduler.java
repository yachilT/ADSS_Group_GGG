package domain_layer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ShipmentScheduler {
    int shipmentIds;
    private List<Shipment> shipments;
    private AreaFacade areaFacade;
    private final DriverFacade driverFacade;
    private final TruckFacade truckFacade;
    private final Function<Predicate<Driver>, Driver> driverGetter;
    private final Consumer<String> storekeeperAssigner;
    public ShipmentScheduler(DriverFacade driverFacade, TruckFacade truckFacade, int shipmentIds, Function<Predicate<Driver>, Driver> driverGetter, Consumer<String> storekeeperAssigner) {
        this.driverFacade = driverFacade;
        this.truckFacade = truckFacade;
        this.shipments = new ArrayList<>();
        this.shipmentIds = shipmentIds;
        this.driverGetter = driverGetter;
        this.storekeeperAssigner = storekeeperAssigner;
    }
    public int scheduleShipment(Site origin, List<Destination> destinations) throws NoSuchElementException {
        Pair<Driver, Truck> pair = findTruckAndDriver();
        Driver driver = pair.first;
        Truck truck = pair.second;
        destinations.forEach(d -> storekeeperAssigner.accept(d.getSite().getAddress()));

        Shipment shipment = new Shipment(shipmentIds++, LocalDateTime.now(), origin, destinations, truck, driver);
        this.shipments.add(shipment);
        return shipment.getShipmentId();
    }
    private Pair<Driver,Truck> findTruckAndDriver() throws  NoSuchElementException{

        for(Truck truck : truckFacade.getAvailableTrucks()){
            Driver driver = driverGetter.apply(truck::isCompatible);
            if (driver != null){
                return new Pair<>(driver, truck);
            }
        }
        throw new NoSuchElementException("No trucks and driver available.");
    }

    public Shipment getShipment(int shipmentId) {

        return shipments.stream().filter(s -> s.getShipmentId() == shipmentId).findFirst().orElseThrow(NoSuchElementException::new);
    }

    public Shipment departShipment(int shipmentId ) {
        Shipment shipment = getShipment(shipmentId);
        shipments.remove(shipment);
        return shipment;

    }

    public List<Integer> getShipmentIds() {
        return shipments.stream().map(Shipment::getShipmentId).toList();
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

