package domain_layer;

public class ProductAmount {
    private final String productName;
    private final int amount;
    public ProductAmount(String productName,int amount ){
        this.productName = productName;
        this. amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ProductAmount productAmount)) {
            return false;
        }
        return productAmount.productName.equals(productName) && productAmount.amount == amount;
    }

    @Override
    public String toString() {
        return "Product: " + productName + " | Amount: " + amount;
    }
}
