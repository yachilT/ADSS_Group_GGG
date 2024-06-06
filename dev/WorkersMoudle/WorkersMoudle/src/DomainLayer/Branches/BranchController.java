package DomainLayer.Branches;

import DomainLayer.Employees.EmployeeController;
import DomainLayer.Employees.Role;

import java.util.HashMap;

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



}
