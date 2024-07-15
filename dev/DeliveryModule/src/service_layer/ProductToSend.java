package service_layer;

import domain_layer.ProductAmount;

public class ProductToSend {
    private String productName;
    private int amount;
    public ProductToSend(String productName, int amount){
        this.productName = productName;
        this.amount = amount;
    }

    public ProductToSend(ProductAmount productAmount){
        this.productName = productAmount.getProductName();
        this.amount = productAmount.getAmount();
    }

    public ProductAmount toProductAmount(){
        return new ProductAmount(productName, amount);
    }
    public String getProductName(){
        return productName;
    }
}
