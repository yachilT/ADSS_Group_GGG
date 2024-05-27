package domain_layer;

import java.sql.Time;
import java.util.Date;
import java.util.Map;

public class Shipment {
    private int shipmentId;
    private Date shipmentDate;
    private Time departureTime;

    private Truck truck;
    private Driver driver;
    private Map<Site, Float> weights;
    private Map<Site, DestinationDocument> destinations;
    public Shipment(int shipmentId, Date shipmentDate, Time departureTime, Map<Site, Float> weights, Map<Site, DestinationDocument> destinations, Truck truck, Driver driver){
        this.shipmentId = shipmentId;
        this.shipmentDate = shipmentDate;
        this.departureTime = departureTime;
        this.weights = weights;
        this.destinations = destinations;
        this.truck = truck;
        this.driver = driver;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public Date getShipmentDate() {
        return shipmentDate;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public Truck getTruck() {
        return truck;
    }

    public Driver getDriver() {
        return driver;
    }
}
