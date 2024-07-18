package service_layer;


import ServiceLayer.Driver;

public class DriverToSend {
    private final String driverName;

    public DriverToSend(String driverName) {
        this.driverName = driverName;
    }

    public DriverToSend(Driver driver) {
        this.driverName = driver.getName();
    }

    public String getName() {
        return driverName;
    }

}
