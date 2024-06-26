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
        this.available = true;
    }

    public License getLicense() {
        return driversLicense;
    }

    public boolean isAvailable() {
        return available;
    }

    public void assignJob() {
        available = false;
    }

    public void finishJob() {
        available = true;
    }

    public String getName() {
        return driverName;
    }

    public int getId() {
        return id;
    }

    public String getDriverName() {
        return driverName;
    }

}

