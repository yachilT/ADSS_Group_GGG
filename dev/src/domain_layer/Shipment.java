package domain_layer;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Shipment {
    private int shipmentId;
    private Date departureDateTime;
    private Truck truck;
    private Driver driver;
    private Map<Site, Float> weights;
    private Map<Site, DestinationDocument> destinations;
    public Shipment(int shipmentId, Date departureDateTime, Map<Site, DestinationDocument> destinations, Truck truck, Driver driver){
        this.shipmentId = shipmentId;
        this.departureDateTime = departureDateTime;
        this.weights = new HashMap<>();
        this.destinations = new HashMap<>();

        this.truck = truck;
        this.truck.assignDelivery();

        this.driver = driver;
        this.driver.assignJob();
    }
    public void addSite(Site site) {

    }
    public int getShipmentId() {
        return shipmentId;
    }

    public Date getDepartureDateTime() {
        return departureDateTime;
    }


    public Truck getTruck() {
        return truck;
    }

    public Driver getDriver() {
        return driver;
    }
}
