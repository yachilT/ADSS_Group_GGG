package domain_layer;

public class Site {
    private String address;
    private String contactName;
    private String contactNumber;

    private int weight;
    public Site(String address, String contactName, String contactNumber, int weight) {
        this.address = address;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.weight = weight;
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

}
