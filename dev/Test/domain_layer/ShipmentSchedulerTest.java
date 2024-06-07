package domain_layer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

class ShipmentSchedulerTest {

    DriverFacade driverFacade;
    TruckFacade truckFacade;
    ShipmentScheduler shipmentScheduler;
    @BeforeEach
    void setUp() {
        driverFacade = new DriverFacade();
        truckFacade = new TruckFacade();
        shipmentScheduler = new ShipmentScheduler(driverFacade, truckFacade);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void scheduleShipment_OK() {
        driverFacade.addDriver(new Driver(0, "Rami Hen", new License(2100)));
        driverFacade.addDriver(new Driver(1, "Yossi Cohen", new License(1100)));

        truckFacade.addTruck(0, "Toyota", 100, 1000);
        truckFacade.addTruck(1, "Mitsubishi", 200, 2000);

        Site origin = new Site("HaGanim 1, Haifa", "Yossi Cohen", "0586943858");
        List<Destination> destinations = List.of(
                new Destination(new Site("Arlozerov 23, Afula", "Yehuda Levi", "05058939955"), List.of(new ProductAmount("Milk", 10))),
                new Destination(new Site("Hertzel 12, Haifa", "Ben Zalman", "0546583385"), List.of(new ProductAmount("Bread", 20)))
        );
        int shipmentId = shipmentScheduler.scheduleShipment(origin, destinations);
        Shipment shipment = shipmentScheduler.getShipment(shipmentId);
        assert shipment.getShipmentId() == shipmentId;
        assert shipment.getOrigin().getAddress().equals("HaGanim 1, Haifa");
        assert shipment.getDestinations().size() == 2;
        assert shipment.getDestinations().get(0).getAddress().equals("Arlozerov 23, Afula");
        assert shipment.getDestinations().get(1).getAddress().equals("Hertzel 12, Haifa");

        assert shipment.getTruck().isCompatible(shipment.getDriver());
    }

    @Test
    void scheduleShipment_NoTruckAndDriver() {
        driverFacade.addDriver(new Driver(0, "Rami Hen", new License(100)));
        truckFacade.addTruck(0, "Toyota", 100, 1000);
        Site origin = new Site("HaGanim 1, Haifa", "Yossi Cohen", "0586943858");
        List<Destination> destinations = List.of(
                new Destination(new Site("Arlozerov 23, Afula", "Yehuda Levi", "05058939955"), List.of(new ProductAmount("Milk", 10))),
                new Destination(new Site("Hertzel 12, Haifa", "Ben Zalman", "0546583385"), List.of(new ProductAmount("Bread", 20)))
        );
        try {
            shipmentScheduler.scheduleShipment(origin, destinations);
            assert false;
        } catch (NoSuchElementException e) {
            assert true;
        }
    }
}