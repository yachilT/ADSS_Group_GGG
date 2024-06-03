package domain_layer;

import java.util.List;

public class Destination {
    private Site site;
    private List<ProductAmount> products;

    private float weight;
    public Destination(Site site, List<ProductAmount> products) {
        this.site = site;
        this.products = products;

        this.weight = -1;
    }

    public DestinationDocument createDocument(int destinationId, int shipmentDocumentId) {
        return new DestinationDocument(this, destinationId, shipmentDocumentId);
    }

    public String getAddress() {
        return this.site.getAddress();
    }

    public String getContactName() {
        return site.getContactName();
    }

    public String getContactNumber() {
        return site.getContactNumber();
    }

    public List<ProductAmount> getProducts() {
        return products;
    }

    public float getWeight() {
        if (weight == -1) {
            throw new IllegalStateException("Weight not set.");
        }
        return weight;
    }

    public void setWeight(float weight) {
        if (this.weight != -1) throw new IllegalStateException("Weight already set.");
        if (weight < 0) throw new IllegalArgumentException("Weight must be Positive.");
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Destination other) {
            return this.site.equals(other.site) && other.products.equals(this.products);
        }
        return false;
    }

    public Site getSite() {
        return site;
    }
}
