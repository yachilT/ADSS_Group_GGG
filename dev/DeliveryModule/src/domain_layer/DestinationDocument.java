package domain_layer;

import java.util.List;

public class DestinationDocument {

    final private int destinationDocId;
    final private int shipmentDocumentId;
    final private String address;
    final private String contactName;
    final private String contactNumber;
    private List<ProductAmount> products;

    final private float weight;

    public DestinationDocument(int destinationDocId, int shipmentDocumentId, String address, String contactName, String contactNumber, float weight) {
        this.destinationDocId = destinationDocId;
        this.shipmentDocumentId = shipmentDocumentId;
        this.address = address;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.products = null;
        this.weight = weight;
    }

    public DestinationDocument(Destination dst, int destinationDocId, int shipmentDocumentId, float weight) {
        this.destinationDocId = destinationDocId;
        this.shipmentDocumentId = shipmentDocumentId;
        this.address = dst.getAddress();
        this.contactName = dst.getContactName();
        this.contactNumber = dst.getContactNumber();
        this.products = dst.getProducts();
        this.weight = weight;
    }



    @Override
    public String toString() {
        return "-------------------------------------------\nDestination details: " + destinationDocId + "\n" +
                "address: " + address + " | contactName: " + contactName + " | contactNumber: " + contactNumber +
                "\n-------------------------------------------\nproducts:\n" + products.stream().map(Object::toString).reduce("", (acc, p) -> acc + p + "\n")
                + "Weight: " + weight + "\n-------------------------------------------\n";
    }

    public void setProducts(List<ProductAmount> products) {
        this.products = products;
    }
    public int getDestinationDocId() {
        return destinationDocId;
    }

    public String getAddress() {
        return address;
    }

    public int getShipmentDocId() {
        return shipmentDocumentId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public List<ProductAmount> getProducts() {
        return products;
    }

    public float getWeight() {
        return weight;
    }

    public int getId() {
        return destinationDocId;
    }
}
