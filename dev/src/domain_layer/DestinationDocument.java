package domain_layer;

import java.util.List;

public class DestinationDocument {

    final private int destinationDocId;
    final private int shipmentDocumentId;
    final private String address;
    final private String contactName;
    final private String contactNumber;
    private List<ProductAmount> products;

    public DestinationDocument(Destination dst, int destinationDocId, int shipmentDocumentId) {
        this.destinationDocId = destinationDocId;
        this.shipmentDocumentId = shipmentDocumentId;
        this.address = dst.getAddress();
        this.contactName = dst.getContactName();
        this.contactNumber = dst.getContactNumber();
        this.products = dst.getProducts();
    }

}
