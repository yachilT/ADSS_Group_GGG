package domain_layer;


import dataAccess_layer.ShipmentDocumentDAO;

import java.util.*;

public class ShipmentHistory {
    private final Map<ShipmentDocument, List<DestinationDocument>> shipments;

    private ShipmentDocumentDAO shipDocDAO;
    private DestinationDocumentRepository destDocRepo;

    public ShipmentHistory() {
        this.shipments = new HashMap<>();
        this.shipDocDAO = new ShipmentDocumentDAO();
        this.destDocRepo = new DestinationDocumentRepository();
    }

    public void loadAll() {
        List<ShipmentDocument> shipDocs = shipDocDAO.readAll();
        shipDocs.forEach(shipDoc -> {
            List<DestinationDocument> destDocs = destDocRepo.getDestDocsOfShipment(shipDoc.getShipmentId());
            add(shipDoc, destDocs);
        });
    }

    public boolean add(ShipmentDocument shipment, List<DestinationDocument> destinations) {
        if (shipments.containsKey(shipment)) {
            return false;
        }

        shipDocDAO.create(shipment);
        destinations.forEach(destDoc -> destDocRepo.create(destDoc));

        shipments.put(shipment, destinations);
        return true;
    }

    public Set<ShipmentDocument> getShipmentDocs() {
        return shipments.keySet();
    }

    public List<DestinationDocument> getDestinations(int id) {
        Optional<List<DestinationDocument>> destinations = shipments.entrySet().stream().filter(e -> e.getKey().getShipmentId() == id).map(Map.Entry::getValue).findFirst();
        if (destinations.isEmpty()) {
            throw new NoSuchElementException("Shipment with " + id + " not found.");
        }
        return destinations.get();
    }

    public List<DestinationDocument> getDestinations(ShipmentDocument shipmentDoc) {
        return shipments.get(shipmentDoc);
    }

}
