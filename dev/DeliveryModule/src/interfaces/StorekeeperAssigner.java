package interfaces;

import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;

public interface StorekeeperAssigner {
    public void assign(String branchAddress, DayOfTheWeek day, PartOfDay part);
}
