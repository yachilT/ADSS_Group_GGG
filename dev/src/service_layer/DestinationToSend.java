package service_layer;

import domain_layer.ProductAmount;
import domain_layer.Site;

import java.util.List;

public class DestinationToSend extends SiteToSend {

    private List<ProductToSend> products;
    public DestinationToSend(SiteToSend site, List<ProductToSend> products){
        super(site);
        this.products = products;
    }


    public List<ProductToSend> getProducts() {
        return products;
    }
}
