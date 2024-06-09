package DomainLayer.Branches;

import DomainLayer.Employees.EmployeeController;
import DomainLayer.Employees.Role;
import ServiceLayer.Response;

import java.util.HashMap;
import java.util.List;

public class BranchController {

    HashMap<Integer, Branch> branches;
    private Integer branchCounter = 1;

    EmployeeController employeeController;

    public BranchController(EmployeeController employeeController) {
        this.employeeController = employeeController;
        branches = new HashMap<>();
    }

    public Integer addBranch(String name, String address){
        branches.put(branchCounter, new Branch(branchCounter, name, address));
        return branchCounter++;
    }

    public String getStringShift(Integer branchId, Integer week, DayOfTheWeek day, PartOfDay partOfDay){
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

    public void exchangeShift(Integer branchId, Integer id1, Integer id2, DayOfTheWeek day, PartOfDay part, Integer week, Role role) throws Exception {
        try{
            branches.get(branchId).exchangeShift(id1, id2, day, part, week, role);
        }catch (Exception e){
            throw e;
        }

    }

    public void addNeededRoles(Integer branchId,DayOfTheWeek day,PartOfDay part,List<Role> list) throws Exception {
        if(!branches.containsKey(branchId))
            throw new Exception("Branch not found");

        if(list.isEmpty())
            throw new Exception("No Roles Were added");

        branches.get(branchId).addNeededRoles(day, part, list);
    }

}
