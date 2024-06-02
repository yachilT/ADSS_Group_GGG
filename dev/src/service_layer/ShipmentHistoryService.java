package service_layer;

import domain_layer.ShipmentDocument;
import domain_layer.ShipmentHistory;

import java.util.ArrayList;
import java.util.List;


public class ShipmentHistoryService {
    private final ShipmentHistory shipmentHistory;
    public ShipmentHistoryService(ShipmentHistory shipmentHistory){
        this.shipmentHistory = shipmentHistory;
    }
    public List<ShipmentDocument> getDocuments(){
        return new ArrayList<>(shipmentHistory.getShipmentDocs());
    }

}
