package domain_layer;

import dataAccess_layer.ShipmentDocumentDAO;

public class ShipmentDocumentRepository {
    private ShipmentDocumentDAO shipmentDocumentDAO;


    public ShipmentDocumentRepository() {
        shipmentDocumentDAO = new ShipmentDocumentDAO();
    }

    public ShipmentDocument get(int id) {
        return shipmentDocumentDAO.read(id);
    }
}

