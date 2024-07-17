package DomainLayer.Branches;

import DataLayer.BranchData.BranchDTO;
import DataLayer.BranchData.BranchDataManager;
import DataLayer.BranchData.ShiftsDataManager;
import DomainLayer.Employees.EmployeeController;
import DomainLayer.Employees.Role;
import ServiceLayer.Response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BranchController {

    HashMap<Integer, Branch> branches;
    private Integer branchCounter;

    EmployeeController employeeController;

    public BranchController(EmployeeController employeeController) {
        this.employeeController = employeeController;
        branches = new HashMap<>();
        branchCounter = 0;
    }

    public Integer addBranch(String name, String address){
        branches.put(branchCounter, new Branch(branchCounter, name, address));
        return branchCounter++;
    }

    public String getStringShift(Integer branchId, Integer week, DayOfTheWeek day, PartOfDay partOfDay) throws Exception{
        if(!branches.containsKey(branchId))
            return null;

        return branches.get(branchId).getShiftToString(week,day,partOfDay);
    }

    public void deleteEmployeeFromShift(int branchId, int employeeId, DayOfTheWeek day, PartOfDay partOfDay) throws Exception{
        branches.get(branchId).deleteEmployeeFromShift(employeeId, day, partOfDay);
    }

    public void setUpShift(int branchId, DayOfTheWeek day, PartOfDay partOfDay, List<Role> roles){
        branches.get(branchId).setUpShift(day, partOfDay, roles);
    }

    public void addEmployeeToShift(Integer id,Integer branchId, Role role, DayOfTheWeek day, PartOfDay partOfDay) throws Exception{
        branches.get(branchId).addEmployeeToShift(id, role, day, partOfDay);
    }

    public void exchangeShift(Integer branchId, Integer id1, Integer id2, DayOfTheWeek day, PartOfDay part, Role role) throws Exception {
        try{
            branches.get(branchId).exchangeShift(id1, id2, day, part, role);
        }catch (Exception e){
            throw e;
        }
    }

    public boolean isThereADriver(Integer branchId, Date date, PartOfDay partOfDay) throws Exception{
        if(!branches.containsKey(branchId))
            throw new Exception("Branch not found");

        return branches.get(branchId).isThereADriver(date, partOfDay);
    }

    public void isWeekExists(Integer branchId,Integer week) throws Exception {
        if(branches.containsKey(branchId))
            branches.get(branchId).isWeekExists(week);
        else
            throw new Exception("Branch not found");
    }

    public void addNeededRoles(Integer branchId,DayOfTheWeek day,PartOfDay part,List<Role> list) throws Exception {
        if(!branches.containsKey(branchId))
            throw new Exception("Branch not found");

        if(list.isEmpty())
            throw new Exception("No Roles Were added");

        branches.get(branchId).addNeededRoles(day, part, list);
    }

    public Integer getBranchId(String address) throws Exception{
        for(Branch branch : branches.values()){
            if(branch.getAddress().equals(address))
                return branch.getId();
        }
        throw new Exception("Branch not found");
    }

    public void loadDatabase() {

        BranchDataManager branchDataManager = new BranchDataManager();
        ShiftsDataManager shiftDataManager = new ShiftsDataManager();
        // Load branches from database
        List<BranchDTO> branches = branchDataManager.loadData();
        for (BranchDTO branch : branches) {
            this.branches.put(branch.getId(), new Branch(branch, shiftDataManager.getBranchSifts(branch.getId())));
            branchCounter++;
        }
    }

    public void testMode() {
        BranchDataManager branchDataManager = new BranchDataManager();
        ShiftsDataManager shiftDataManager = new ShiftsDataManager();
        branchDataManager.testMode();
        shiftDataManager.testMode();
    }

    public void deleteData() {
        new BranchDataManager().deleteData();
        new ShiftsDataManager().deleteData();
    }
}