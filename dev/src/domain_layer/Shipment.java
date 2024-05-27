package domain_layer;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Shipment {
    private int shipmentId;
    private Date shipmentDate;
    private Time departureTime;
    private Map<Site, Float> weights;
    private Map<Site, DestinationDocument> destinations;
    public Shipment(int shipmentId, Date shipmentDate, Time departureTime, Map<Site, DestinationDocument> destinations){
        this.shipmentId = shipmentId;
        this.shipmentDate = shipmentDate;
        this.departureTime = departureTime;
        this.weights = new HashMap<>();
        this.destinations = new HashMap<>();
    }
    public void add(Site site){

    }
}
