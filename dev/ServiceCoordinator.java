
import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Employees.Role;
import ServiceLayer.Response;

import java.awt.geom.Area;

public class ServiceCoordinator {
    public Response addNeededRole(Role role, String branchAddress, Pair<DayOfTheWeek, PartOfDay> shift) {
        throw new UnsupportedOperationException();
    }

    public Response hasRoleInShift(Role role, String branchAddress, Pair<DayOfTheWeek, PartOfDay> shift) {
        throw new UnsupportedOperationException();
    }


    public Response assignDriver(String branchAddress, Pair<DayOfTheWeek, PartOfDay> shift) {
        throw new UnsupportedOperationException();
    }

    public Response addSite (Area a, String siteName, String address, String phone, String contactName, String contactPhone) {
        throw new UnsupportedOperationException();
    }
}
