package service_layer;

import domain_layer.Site;

import java.util.List;

public class DestinationToSend extends SiteToSend {

    private List<ProductToSend> products;
    public DestinationToSend(String address, String contactName,String contactNumber, List<ProductToSend> products){
        this.adress = address;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.products = products;
    }

}
