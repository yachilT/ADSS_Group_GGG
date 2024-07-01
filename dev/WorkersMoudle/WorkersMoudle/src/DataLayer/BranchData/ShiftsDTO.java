package DataLayer.BranchData;

import DomainLayer.Employees.Role;

import java.util.HashMap;
import java.util.List;

public class ShiftsDTO {


    private int BID;
    private String date;
    private int partOfDay;

    private HashMap<Role,Integer> EIDs;

    private List<Role> neededRoles;



    public ShiftsDTO(int BID, HashMap<Role,Integer> EIDs, String date, int partOfDay, List<Role> neededRoles) {
        this.BID = BID;
        this.EIDs = EIDs;
        this.date = date;
        this.partOfDay = partOfDay;
        this.neededRoles = neededRoles;
    }

    public int getBID() {
        return BID;
    }

    public List<Integer> getEIDs() {
        return EIDs.values().stream().toList();
    }

    public String getDate() {
        return date;
    }

    public int getPartOfDay() {
        return partOfDay;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addEmployee(int eID, Role role) {
        EIDs.put(role, eID);
        new ShiftsDataManager().addEmployeeToShift(this.BID, eID, this.date, this.partOfDay);
        //TODO: add role to neededRoles
    }

    public void removeEmployee(int eID) {
        EIDs.remove(eID);
        new ShiftsDataManager().deleteEmployeeFromShift(this.BID, eID, this.date, this.partOfDay);
        //TODO: remove role from neededRoles
    }

}
