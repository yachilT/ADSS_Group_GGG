package domain_layer;

public class Driver {
    private int id;
    private String driverName;
    private License driversLicense;

    private boolean available;

    public Driver(int id, String driverName, License license) {
        this.id = id;
        this.driverName = driverName;
        this.driversLicense = license;
    }

    public License getLicense() {
        return driversLicense;
    }

    public boolean isAvailable() {
        return available;
    }

    public void assignJob() {
        available = true;
    }

    public void finishJob() {
        available = false;
    }

    public String getName() {
        return driverName;
    }
}

