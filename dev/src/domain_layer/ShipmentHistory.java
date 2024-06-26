package domain_layer;


import java.util.*;

public class ShipmentHistory {
    private final Map<ShipmentDocument, List<DestinationDocument>> shipments;

    public ShipmentHistory() {
        this.shipments = new HashMap<>();
    }

    public boolean add(ShipmentDocument shipment, List<DestinationDocument> destinations) {
        if (shipments.containsKey(shipment)) {
            return false;
        }
        shipments.put(shipment, destinations);
        return true;
    }

    public Set<ShipmentDocument> getShipmentDocs() {
        return shipments.keySet();
    }

    public List<DestinationDocument> getDestinations(int id) {
        Optional<List<DestinationDocument>> destinations = shipments.entrySet().stream().filter(e -> e.getKey().getId() == id).map(Map.Entry::getValue).findFirst();
        if (destinations.isEmpty()) {
            throw new NoSuchElementException("Shipment with " + id + " not found.");
        }
        return destinations.get();
    }

    public List<DestinationDocument> getDestinations(ShipmentDocument shipmentDoc) {
        return shipments.get(shipmentDoc);
    }


}
