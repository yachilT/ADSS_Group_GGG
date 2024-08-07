package ServiceLayer.EmployeeServices;


import DomainLayer.Branches.BranchController;
import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Employees.Employee;
import DomainLayer.Employees.EmployeeController;
import DomainLayer.Employees.Role;
import DomainLayer.Pair;
import ServiceLayer.Driver;
import ServiceLayer.Response;


import java.util.List;
import java.util.function.Predicate;


public class EmployeeService {

    private EmployeeController employeeController;
    private BranchController branchController;

    public EmployeeService(EmployeeController employeeController, BranchController branchController) {
        this.employeeController = employeeController;
        this.branchController = branchController;
    }

    // Method to sign up a new employee
    public Response signUp(Integer id, String password) {
        try {
            employeeController.signUp(id, password);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }

        return new Response();
    }

    // Method to log in an existing employee
    public Response login(Integer id, String password) {
        try {
            employeeController.login(id, password);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    // Method to log out an existing employee
    public Response logout(Integer id) {
        try {
            employeeController.logout(id);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    // Method to enter time out for an employee
    public Response enterTimeOut(Integer id, DayOfTheWeek day, PartOfDay part) {
        try {
            employeeController.enterTimeOut(id, day, part);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    // Method to exchange shifts between two employees
    public Response exchangeShift(Integer branchId, Integer id1, Integer id2, DayOfTheWeek day,PartOfDay part, Role role) {
        if (this.employeeController.isBranchEMP(id1, branchId) && this.employeeController.isBranchEMP(id2, branchId)) {
            try {
                branchController.exchangeShift(branchId, id1, id2, day, part, role);
            } catch (Exception e) {
                return new Response(e.getMessage());
            }
            return new Response();
        }
        return new Response("One or both of the employees are not in the branch");
    }

    // Method to enter shift preferences for an employee
    public Response enterPreferences(Integer id, DayOfTheWeek day, PartOfDay part) {
        try {
            employeeController.enterPreferences(id, day, part);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response removeEmployee(Integer id) {
        try {
            employeeController.removeEmployee(id);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response isManager(Integer id) {
        if(employeeController.isManager(id))
            return new Response();

        return new Response("Employee is not a manager");
    }

    public Response isHR(Integer id) {
        if(employeeController.isHR(id))
            return new Response();

        return new Response("Employee is not a HR manager");
    }

    public Response printEmployeesPref(Integer branchId){
        try {
            return new Response(employeeController.printEmployeesPref(branchId),null);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response checkEmployeeRole(Integer id, Role role){
        try {
            employeeController.checkEmployeeRole(id, role);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }
    public Response isEmployeeNew(Integer id) {
        try {
            return new Response(employeeController.isEmployeeNew(id));
        }catch (Exception e){
            return new Response(e.getMessage());
        }


    }

    public Response isEmployeeExist(Integer id, Integer branchId){
        try {
            employeeController.isEmployeeExist(id, branchId);
        }catch (Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response isHRExist(){
        try{
            employeeController.isHrManagerExist();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response getBranchId(Integer id) {
        try {
            return new Response(employeeController.getBranchId(id));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response assignDriver(Predicate<Driver> driverPred, DayOfTheWeek day, PartOfDay part, String address){
        try{
            Integer bId = branchController.getBranchId(address);
            Driver driver = employeeController.assignDriver(driverPred, day, part, bId);
            branchController.addEmployeeToShift(driver.getId(),bId,Role.Driver,day,part);
            return new Response(driver);
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response loadDatabase() {
        try {
            employeeController.loadDatabase();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    public Response testMode(){
        employeeController.testMode();
        branchController.testMode();
        return new Response();
    }

    public Response isDeliveryManager(int id) {
        try {
            if(employeeController.isDeliveryManager(id))
                return new Response(true);
            return new Response("Employee is not a delivery manager");
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
}