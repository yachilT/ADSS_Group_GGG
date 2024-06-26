package domain_layer;

import dataAccess_layer.DestinationDocumentDAO;
import dataAccess_layer.ProductDAO;

import java.util.List;

public class DestinationDocumentRepository {
    DestinationDocumentDAO destinationDocumentDAO;
    ProductDAO productDAO;

    public DestinationDocumentRepository() {
        destinationDocumentDAO = new DestinationDocumentDAO();
        productDAO = new ProductDAO();
    }

    public DestinationDocument get(int id) {
        DestinationDocument destDoc = destinationDocumentDAO.read(id);
        destDoc.setProducts(productDAO.getProductsByDstId(id));
        return destDoc;
    }

    public List<DestinationDocument> getDestDocsOfShipment(int shipmentId) {
        List<Integer> destIds = destinationDocumentDAO.readDstIdByShipmentId(shipmentId);
        return destIds.stream().map(this::get).toList();
    }

    public void create(DestinationDocument destDoc) {
        destinationDocumentDAO.create(destDoc);
        destDoc.getProducts().forEach(p -> productDAO.create(p, destDoc.getId()));
    }
}
