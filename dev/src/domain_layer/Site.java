package domain_layer;

public class Site {
    private String address;
    private String contactName;
    private String contactNumber;
    public Site(String address, String contactName, String contactNumber){
        this.address = address;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
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
}
