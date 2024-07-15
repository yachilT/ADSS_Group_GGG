package service_layer;

import domain_layer.Shipment;

public class ShipmentToSend {
    private final DriverToSend driver;
    private final TruckToSend truck;
    private final SiteToSend origin;

    public ShipmentToSend(DriverToSend driver, TruckToSend truck, SiteToSend origin){
        this.driver = driver;
        this.truck = truck;
        this.origin = origin;
    }

    public ShipmentToSend(Shipment shipment){
        this.driver = new DriverToSend(shipment.getDriver());
        this.truck = new TruckToSend(shipment.getTruck());
        this.origin = new SiteToSend(shipment.getOrigin());
    }

    public DriverToSend getDriver() {
        return driver;
    }

    public TruckToSend getTruck() {
        return truck;
    }

    public SiteToSend getOrigin() {
        return origin;
    }

}
