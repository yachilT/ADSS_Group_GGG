package domain_layer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentHistoryTest {
    ShipmentHistory shipmentHistory;

    @BeforeEach
    void setup() {
        shipmentHistory = new ShipmentHistory();
    }
    @Test
    void getDestinations() {
        ShipmentDocument shipmentDocument = new ShipmentDocument(1, "HaGanim 1, Haifa", "Yossi Cohen", "0586943858", LocalDate.now().toString(), LocalTime.now().toString(), 0, "Rami Hen");
        DestinationDocument destinationDocument = new DestinationDocument(new Destination(new Site("Arlozerov 23, Afula", "Yehuda Levi", "05058939955"), List.of(new ProductAmount("Milk", 10))), 0, 1, 100);
        shipmentHistory.add(shipmentDocument, List.of(destinationDocument));
        assertEquals(shipmentHistory.getDestinations(shipmentDocument).get(0), destinationDocument);
    }
}