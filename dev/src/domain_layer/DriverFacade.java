package domain_layer;

import java.util.ArrayList;
import java.util.List;

public class DriverFacade {
    final private List<Driver> drivers;

    public DriverFacade() {
        drivers = new ArrayList<>();
    }

    public List<Driver> getAvailableDrivers() {
        return drivers.stream().filter(Driver::isAvailable).toList();
    }



}
