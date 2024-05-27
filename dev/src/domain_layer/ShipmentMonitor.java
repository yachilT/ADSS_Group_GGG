package domain_layer;

public class ShipmentMonitor {
    private Shipment shipment;

    public ShipmentMonitor(Shipment shipment) {
        this.shipment = shipment;
    }

    public boolean updateWeight(float currentWeight) {
        if (shipment.getTruck().isOverweight(currentWeight)) {
            shipment.getTruck().finishDelivery();
            return false;
        }
        return true;
    }
}
