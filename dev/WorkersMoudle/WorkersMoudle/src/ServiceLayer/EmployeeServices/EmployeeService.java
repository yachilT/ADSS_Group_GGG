package ServiceLayer.EmployeeServices;


import DomainLayer.Branches.BranchController;
import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Employees.Employee;
import DomainLayer.Employees.EmployeeController;
import DomainLayer.Employees.Role;
import DomainLayer.Pair;
import ServiceLayer.Response;


import java.util.List;



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
    public Response enterTimeOut(Integer id, List<Pair<DayOfTheWeek, PartOfDay>> times) {
        try {
            employeeController.enterTimeOut(id, times);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
        return new Response();
    }

    // Method to exchange shifts between two employees
    public Response exchangeShift(Integer branchId, Integer id1, Integer id2, DayOfTheWeek day,PartOfDay part, Integer week, Role role) {
        if (this.employeeController.isBranchEMP(id1, branchId) && this.employeeController.isBranchEMP(id2, branchId)) {
            try {
                branchController.exchangeShift(branchId, id1, id2, day, part, week, role);
            } catch (Exception e) {
                return new Response(e.getMessage());
            }
            return new Response();
        }
        return new Response("One or both of the employees are not in the branch");
    }

    // Method to enter shift preferences for an employee
    public Response enterPreferences(Integer id, List<Pair<DayOfTheWeek, PartOfDay>> shiftPreferences) {
        try {
            employeeController.enterPreferences(id, shiftPreferences);
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
            return new Response(employeeController.printEmployeesPref(branchId));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response isEmployeeNew(Integer id) {
        try {
            boolean isNew = employeeController.isEmployeeNew(id);

            if(isNew)
                return new Response(true);

            return new Response(false);
        }catch (Exception e){
            return new Response(e.getMessage());
        }


    }

    public Response getBranchId(Integer id) {
        try {
            return new Response(employeeController.getBranchId(id));
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
}