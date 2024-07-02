package domain_layer;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return id == driver.id && available == driver.available && Objects.equals(driverName, driver.driverName) && driversLicense.equals(driver.driversLicense);
    }

}

