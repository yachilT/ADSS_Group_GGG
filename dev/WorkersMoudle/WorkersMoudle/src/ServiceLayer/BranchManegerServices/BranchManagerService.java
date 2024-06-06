package ServiceLayer.BranchManegerServices;

import DomainLayer.Branches.BranchController;
import DomainLayer.Employees.EmployeeController;
import DomainLayer.Employees.Role;
import DomainLayer.Pair;

import java.util.Date;
import java.util.List;

public class BranchManagerService {

    BranchController branchController;
    EmployeeController employeeController;

    public BranchManagerService(BranchController branchController,EmployeeController employeeController){
        this.branchController = branchController;
        this.employeeController = employeeController;
    }

    // Method to register an employee with given ID and employee data
    public void empRegister(String name, String password, int bankAccountNum, double salary, int branchId, List<Role> qualification) {
        try{
            employeeController.addEmployee(name, password, qualification, bankAccountNum, salary, branchId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Method to prepare shifts with a list of roles and the number needed for each role
    public void prepareShift(List<Role> roles) {
        // Implementation goes here
    }

    // Method to assign an employee to a shift
    public void assignToShift(Long id, Long shiftId) {
        // Implementation goes here
    }

    // Method to finalize a shift and send messages to all employees
    public void finaliseShift(Long shiftId) {
        // Implementation goes here
    }

    // Method to display preferences of all employees
    public void displayPreferences() {
        // Implementation goes here
    }

}
