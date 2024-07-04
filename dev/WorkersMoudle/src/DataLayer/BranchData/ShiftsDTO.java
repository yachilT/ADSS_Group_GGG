package DataLayer.BranchData;

import DomainLayer.Branches.PartOfDay;
import DomainLayer.Employees.Role;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ShiftsDTO {


    public final static String COLUMN_NAME_BID = "BID";
    public final static String COLUMN_NAME_DATE = "DATE";
    public final static String COLUMN_NAME_PART_OF_DAY = "PARTOFDAY";
    public final static String COLUMN_NAME_EID = "EID";
    public static final String COLUMN_NAME_ROLES = "ROLES";
    public static final String COLUMN_NAME_E_ROLE = "EROLE";


    private int BID;
    private Date date;
    private PartOfDay partOfDay;

    private HashMap<Integer,Role> EIDs;

    private List<Role> neededRoles;

    private ShiftsDataManager shiftsDataManager;




    public ShiftsDTO(int BID, HashMap<Integer, Role> EIDs, Date date, PartOfDay partOfDay, List<Role> neededRoles) {
        this.BID = BID;
        this.EIDs = EIDs;
        this.date = date;
        this.partOfDay = partOfDay;
        this.neededRoles = neededRoles;
        this.shiftsDataManager = new ShiftsDataManager();
    }
    public void insertDTO(){
        shiftsDataManager.insertDTO(this);
    }

    public int getBID() {
        return BID;
    }

    public List<Integer> getEIDs() {
        return EIDs.keySet().stream().toList();
    }

    public Date getDate() {
        return date;
    }

    public PartOfDay getPartOfDay() {
        return partOfDay;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addEmployee(int eID, Role role) {
        EIDs.put(eID, role);

        HashMap<Integer, Role> newEmp = new HashMap<>();
        newEmp.put(eID, role);
        new ShiftsDataManager().addEmployeeToShift(BID, newEmp, date, partOfDay, neededRoles);
    }

    public void addNeededRoles(List<Role> neededRoles) {
        this.neededRoles.addAll(neededRoles);
        shiftsDataManager.addNeededRoles(BID, date, partOfDay, this.neededRoles);
    }

    public void removeEmployee(int eID) {

        HashMap<Integer, Role> removedEmp = new HashMap<>();
        removedEmp.put(eID, EIDs.get(eID));

        EIDs.remove(eID);
        new ShiftsDataManager().deleteEmployeeFromShift(BID, removedEmp, date, partOfDay, neededRoles);
    }

    public ShiftsDTO combineShifts(ShiftsDTO otherShift){
        if(this.BID != otherShift.BID || !this.date.equals(otherShift.date) || this.partOfDay != otherShift.partOfDay )
            throw new IllegalArgumentException("Shifts must be of the same branch, date and part of day to be combined");

        ShiftsDTO shiftsDTO = new ShiftsDTO(this.BID, this.EIDs, this.date, this.partOfDay, this.neededRoles);
        shiftsDTO.EIDs.putAll(otherShift.EIDs);
        return shiftsDTO;
    }


    public List<Role> getNeededRoles() {
        return neededRoles;
    }

    public Role getRole(int eID){
        return EIDs.get(eID);
    }
}
