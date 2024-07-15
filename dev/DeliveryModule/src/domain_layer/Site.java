package domain_layer;

import java.util.Objects;

public class Site {
    private String address;
    private String contactName;
    private String contactNumber;
    private int weight;


    public Site(String address, String contactName, String contactNumber) {
        this.address = address;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.weight = -1;
    }

    public String getAddress() {
        return address;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Site site = (Site) o;
        return weight == site.weight && Objects.equals(address, site.address) && Objects.equals(contactName, site.contactName) && Objects.equals(contactNumber, site.contactNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, contactName, contactNumber, weight);
    }
}
