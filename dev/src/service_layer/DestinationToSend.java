package service_layer;

import domain_layer.Destination;
import domain_layer.ProductAmount;
import domain_layer.Site;

import java.util.List;
import java.util.stream.Collectors;

public class DestinationToSend extends SiteToSend {

    private List<ProductToSend> products;
    public DestinationToSend(SiteToSend site, List<ProductToSend> products){
        super(site);
        this.products = products;
    }

    public DestinationToSend(Destination dst) {
        super(dst.getSite());
        this.products = dst.getProducts().stream().map(ProductToSend::new).collect(Collectors.toList());
    }


    public List<ProductToSend> getProducts() {
        return products;
    }

    public Destination toDestination() {
        return null;
    }
}
