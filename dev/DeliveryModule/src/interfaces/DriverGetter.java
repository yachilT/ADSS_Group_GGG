package interfaces;

import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import domain_layer.Driver;

import java.util.function.Predicate;

public interface DriverGetter {
    public Driver getDriver(String branchAddress, DayOfTheWeek day, PartOfDay part, Predicate<Driver> driverPred);
}
