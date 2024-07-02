package domain_layer;

import dataAccess_layer.DriverDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DriverFacade {
    private final Set<Driver> drivers;
    private final DriverDAO driverDAO;

    public DriverFacade(String dbPath) {
        drivers = new HashSet<>();
        driverDAO = new DriverDAO(dbPath);
    }
    public void loadAll() {
        List<Driver> drivers = driverDAO.readAll();
        this.drivers.addAll(drivers);
    }
    public void addDriver(Driver driver) {
        driverDAO.create(driver);
        drivers.add(driver);
    }
    public Set<Driver> getDrivers(){
        return drivers;
    }
    public List<Driver> getAvailableDrivers() {
        return drivers.stream().filter(Driver::isAvailable).toList();
    }



}
