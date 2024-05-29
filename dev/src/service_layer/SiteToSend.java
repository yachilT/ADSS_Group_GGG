package service_layer;

import domain_layer.Site;

public class SiteToSend {

    protected String adress;
    protected String contactName;
    protected String contactNumber;

    public SiteToSend(Site site){
        this.adress = site.getAddress();
        this.contactName = site.getContactName();
        this.contactNumber = site.getContactNumber();
    }
    public SiteToSend(SiteToSend site){
        this.adress = site.adress;
        this.contactName = site.contactName;
        this.contactNumber = site.contactNumber;
    }
    public String getAdress() {
        return adress;
    }
}
