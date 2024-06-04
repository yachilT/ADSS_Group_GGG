package service_layer;

import domain_layer.Site;

public class SiteToSend {

    protected String address;
    protected String contactName;
    protected String contactNumber;

    public SiteToSend(Site site){
        this.address = site.getAddress();
        this.contactName = site.getContactName();
        this.contactNumber = site.getContactNumber();
    }
    public SiteToSend(SiteToSend site){
        this.address = site.address;
        this.contactName = site.contactName;
        this.contactNumber = site.contactNumber;
    }
    public String getAddress() {
        return address;
    }
}
