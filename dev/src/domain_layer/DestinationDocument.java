package domain_layer;

import java.util.List;

public class DestinationDocument {
    final private int destinationDocId;
    final private int shipmentDocumentId;
    private List<ProductAmount> products;
    public DestinationDocument(int destinationDocId, int shipmentDocumentId, List<ProductAmount> products) {
        this.destinationDocId = destinationDocId;
        this.shipmentDocumentId = shipmentDocumentId;
        this.products = products;
    }

    public void remove(String productName) {
        products.removeIf(p -> p.getProductName().equals(productName));
    }

}
