package domain_layer;
import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import ServiceLayer.Driver;
import interfaces.DriverGetter;
import interfaces.StorekeeperAssigner;

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
   ;
    private final TruckFacade truckFacade;
    private final DriverGetter driverGetter;
    private final StorekeeperAssigner storekeeperAssigner;
    public ShipmentScheduler(TruckFacade truckFacade, int shipmentIds, DriverGetter driverGetter, StorekeeperAssigner storekeeperAssigner) {

        this.truckFacade = truckFacade;
        this.shipments = new ArrayList<>();
        this.shipmentIds = shipmentIds;
        this.driverGetter = driverGetter;
        this.storekeeperAssigner = storekeeperAssigner;
    }
    public int scheduleShipment(Site origin, List<Destination> destinations, DayOfTheWeek day, PartOfDay part) throws NoSuchElementException {
        Pair<Driver, Truck> pair = findTruckAndDriver(origin, day, part);
        Driver driver = pair.first;
        Truck truck = pair.second;
        destinations.forEach(d -> storekeeperAssigner.assign(d.getSite().getAddress(), day, part));

        Shipment shipment = new Shipment(shipmentIds++, LocalDateTime.now(), origin, destinations, truck, driver, day, part);
        this.shipments.add(shipment);
        return shipment.getShipmentId();
    }
    private Pair<Driver,Truck> findTruckAndDriver(Site origin, DayOfTheWeek day, PartOfDay part) throws  NoSuchElementException{

        for(Truck truck : truckFacade.getAvailableTrucks()){
            Driver driver = driverGetter.getDriver(origin.getAddress(), day, part, truck::isCompatible);
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

