package DomainLayer.Branches;

import DomainLayer.Employees.EmployeeController;
import DomainLayer.Employees.Role;

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

    public int addBranch(String name, String address){
        branches.put(branchCounter, new Branch(branchCounter, name, address));
        return branchCounter++;
    }

    public void addEmployeeToShift(int branchId, int employeeId, Role role, DayOfTheWeek day, PartOfDay partOfDay) throws Exception{
        branches.get(branchId).addEmployeeToShift(employeeId, role, day, partOfDay);
    }

    public void deleteEmployeeFromShift(int branchId, int employeeId, DayOfTheWeek day, PartOfDay partOfDay){
        branches.get(branchId);
    }

    public void setUpShift(int branchId, DayOfTheWeek day, PartOfDay partOfDay, List<Role> roles){
        branches.get(branchId).setUpShift(day, partOfDay, roles);
    }

    public void addEmployeeToShift(Integer id,Integer branchId, Role role, DayOfTheWeek day, PartOfDay partOfDay){
        try {
            branches.get(branchId).addEmployeeToShift(id, role, day, partOfDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exchangeShift(Integer branchId, Integer id1, Integer id2, DayOfTheWeek day, PartOfDay part, Integer week, Role role) throws Exception {
        try{
            branches.get(branchId).exchangeShift(id1, id2, day, part, week, role);
        }catch (Exception e){
            throw e;
        }

    }

}
