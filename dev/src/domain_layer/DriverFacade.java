package domain_layer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DriverFacade {
    final private Set<Driver> drivers;

    public DriverFacade() {
        drivers = new HashSet<>();
    }
    public void addDriver(Driver driver) {
        drivers.add(driver);
    }

    public List<Driver> getAvailableDrivers() {
        return drivers.stream().filter(Driver::isAvailable).toList();
    }



}
