package domain_layer;

import java.sql.Time;
import java.util.Date;
import java.util.Map;

public class ShipmentDocument {
    private int id;
    private String shipmentDate;
    private String departureTime;
    private int truckNumber;
    private String driverName;

    public ShipmentDocument(int id, String shipmentDate, String departureTime, int truckNumber, String driverName) {
        this.id = id;
        this.shipmentDate = shipmentDate;
        this.departureTime = departureTime;
        this.truckNumber = truckNumber;
        this.driverName = driverName;
    }

    public ShipmentDocument(Shipment shipment) {
        this.id = shipment.getShipmentId();
        this.shipmentDate = shipment.getShipmentDate().toString();
        this.departureTime = shipment.getDepartureTime().toString();
        this.truckNumber = shipment.getTruck().getTruckNumber();
        this.driverName = shipment.getDriver().getName();
    }

}
