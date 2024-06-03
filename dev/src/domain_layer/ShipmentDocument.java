package domain_layer;

public class ShipmentDocument {
    private int id;
    private String originAddress;
    private String originContactName;
    private String originContactNumber;
    private String shipmentDate;
    private String departureTime;
    private int truckNumber;
    private String driverName;

    public ShipmentDocument(int id, String originAddress, String originContactName, String originContactNumber, String shipmentDate, String departureTime, int truckNumber, String driverName) {
        this.id = id;
        this.originAddress = originAddress;
        this.originContactName = originContactName;
        this.originContactNumber = originContactNumber;
        this.shipmentDate = shipmentDate;
        this.departureTime = departureTime;
        this.truckNumber = truckNumber;
        this.driverName = driverName;
    }

    public int getId() {
        return id;
    }
    public String getOriginContactName() {return originContactName;}

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ShipmentDocument other) {
            return this.id == other.id;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Shipment details:\n" +
                    "\bID: " + id + ", Date: " + shipmentDate + ", Departure time: " + departureTime + "\n" +
                    "\bTruck number: " + truckNumber + ", Driver name: " + driverName + "\n" +
                    "Origin details:\n" +
                    "\bAddress: " + originAddress + ", Contact name: " + originContactName + ", Contact number: " + originContactNumber;
    }
}
