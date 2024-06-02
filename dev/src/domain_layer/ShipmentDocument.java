package domain_layer;
import java.time.LocalDateTime;
public class ShipmentDocument {
    private int id;
    private String originAddress;
    private String originName;
    private String originNumber;
    private String shipmentDate;
    private String departureTime;
    private int truckNumber;
    private String driverName;

    public ShipmentDocument(int id, String originAddress, String originName, String originNumber, String shipmentDate, String departureTime, int truckNumber, String driverName) {
        this.id = id;
        this.originAddress = originAddress;
        this.originName = originName;
        this.originNumber = originNumber;
        this.shipmentDate = shipmentDate;
        this.departureTime = departureTime;
        this.truckNumber = truckNumber;
        this.driverName = driverName;
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
