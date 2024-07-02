package domain_layer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentTrackerTest {
    ShipmentTracker shipmentTracker;



    @BeforeEach
    public void setUp() {
        shipmentTracker = new ShipmentTracker(new Shipment(0, LocalDateTime.now(), new Site("HaGanim 1, Haifa", "Yossi Cohen", "0586943858"), new LinkedList<>(Arrays.asList(
                new Destination(new Site("Arlozerov 23, Afula", "Yehuda Levi", "05058939955"), new LinkedList<>(Arrays.asList(new ProductAmount("Milk", 10), new ProductAmount("Eggs", 20)))),
                new Destination(new Site("Hertzel 12, Haifa", "Ben Zalman", "0546583385"), new LinkedList<>(Arrays.asList(new ProductAmount("Bread", 20)))))
        ), new Truck(0, "Toyota", 100, 1000), new Driver(0, "Rami Hen", new License(2100))), new ShipmentHistory());

    }


    @Test
    void changeTruck_OK() {
        try {
            shipmentTracker.next();
            TruckFacade truckFacade = new TruckFacade(false, "");
            truckFacade.addTruck(1, "Mitsubishi", 200, 1300);
            shipmentTracker.changeTruck(truckFacade, 1200);
            assert true;
        } catch (NoSuchElementException e) {
            fail("Should not throw an exception");
        }
    }

    @Test
    void changeTruck_NoRelevantTruck() {
        try {
            shipmentTracker.next();
            TruckFacade truckFacade = new TruckFacade(false, "");
            truckFacade.addTruck(1, "Mitsubishi", 200, 1300);
            shipmentTracker.changeTruck(truckFacade, 1500);
            fail("Should throw a NoSuchElementException");
        } catch (NoSuchElementException e) {
            assert true;
        }
    }

    @Test
    void removeDestination() {
        shipmentTracker.next();
        int oldSize = shipmentTracker.getAllDestinations().size();
        shipmentTracker.removeDestination();
        assertEquals(oldSize - 1, shipmentTracker.getAllDestinations().size());
    }

    @Test
    void changeDestination() {
        shipmentTracker.next();
        Destination dstToChange = shipmentTracker.getAllDestinations().get(1);
        shipmentTracker.changeDestination(0);
        assertEquals(shipmentTracker.getAllDestinations().get(0), dstToChange);
    }

    @Test
    void productsToRemain() {
        shipmentTracker.next();
        shipmentTracker.productsToRemain(List.of(new ProductAmount("Eggs", 20)));

        assertEquals(List.of(new ProductAmount("Eggs", 20)), shipmentTracker.getAllDestinations().get(0).getProducts());
    }

    @Test
    void setWeight_OK() {
        try {
            shipmentTracker.next();
            shipmentTracker.setWeight(800);
            assert true;
        } catch (IllegalArgumentException e) {
            fail("Should not throw an exception");
        }
    }

    @Test
    void setWeight_Overweight() {
        try {
            shipmentTracker.next();
            shipmentTracker.setWeight(1200);
            fail("Should throw an exception");
        } catch (IllegalArgumentException e) {
            assert true;
        }
    }
}