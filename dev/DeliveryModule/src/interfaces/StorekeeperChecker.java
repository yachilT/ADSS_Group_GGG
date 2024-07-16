package interfaces;

import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;

public interface StorekeeperChecker {
    public boolean check(String branchAddress, DayOfTheWeek day, PartOfDay part);
}
