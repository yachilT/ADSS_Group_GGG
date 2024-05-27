package domain_layer;

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
        this.shipmentDate = shipment.getDepartureDateTime().toString();
        this.departureTime = shipment.getDepartureTime().toString();
        this.truckNumber = shipment.getTruck().getTruckNumber();
        this.driverName = shipment.getDriver().getName();
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ShipmentDocument other) {
            return this.id == other.id;
        }
        return false;
    }
}
