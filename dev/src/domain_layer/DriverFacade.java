package domain_layer;

import dataAccess_layer.DriverDAO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DriverFacade {
    final private Set<Driver> drivers;
    private final DriverDAO driverDAO;
    private boolean persist;

    public DriverFacade(boolean persist, String dbPath) {
        drivers = new HashSet<>();
        driverDAO = new DriverDAO(dbPath);
        this.persist = persist;
    }
    public void loadAll() {
        List<Driver> drivers = driverDAO.readAll();
        this.drivers.addAll(drivers);
    }
    public void addDriver(Driver driver) {
        if (persist)
            driverDAO.create(driver);
        drivers.add(driver);
    }

    public List<Driver> getAvailableDrivers() {
        return drivers.stream().filter(Driver::isAvailable).toList();
    }



}
