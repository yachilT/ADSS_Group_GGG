package ServiceLayer.BranchManegerServices;

import DomainLayer.Branches.BranchController;
import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Employees.EmployeeController;
import DomainLayer.Employees.Role;
import DomainLayer.Pair;
import ServiceLayer.Response;

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
    public Response empRegister(String name, int bankAccountNum, double salary, int branchId, List<Role> qualification) {
        try{
            employeeController.addEmployee(name, qualification, bankAccountNum, salary, branchId);
        }catch (Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    // Method to prepare shifts with a list of roles (add roles to the shift)
    public Response prepareShift(int branchId, DayOfTheWeek day, PartOfDay partOfDay, List<Role> roles) {
        this.branchController.setUpShift(branchId, day, partOfDay, roles);
        return new Response();
    }

    // Method to assign an employee to a shift
    public Response assignToShift(Integer id,Integer branchId, Role role, DayOfTheWeek day, PartOfDay partOfDay) {
        this.branchController.addEmployeeToShift(id, branchId, role, day, partOfDay);
        return new Response();
    }

    // Method to display preferences of all employees (return Response with List<String>)
    public Response displayPreferences(Integer id) {
        return new Response(this.employeeController.displayPreferences(id));
    }

}
