package ServiceLayer.EmployeeServices;


import DomainLayer.Barnches.DayOfTheWeek;
import DomainLayer.Barnches.PartOfDay;
import DomainLayer.Employees.EmployeeController;
import DomainLayer.Pair;


import java.util.List;



public class EmployeeService {

    private EmployeeController employeeController;

    // Method to sign up a new employee
    public void signUp(Integer id, String password) {
        // Implementation goes here
    }

    // Method to log in an existing employee
    public void login(Integer id, String password) {
        try {
            employeeController.login(id, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to log out an existing employee
    public void logout(Integer id) {
        try {
            employeeController.logout(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to enter time out for an employee
    public void enterTimeOut(Integer id, List<Pair<DayOfTheWeek, PartOfDay>> times) {
        // Implementation goes here
        try {
            employeeController.enterTimeOut(id, times);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Method to exchange shifts between two employees
    public void exchangeShift(Integer id1, Integer id2, Integer shiftId) {
        // Implementation goes here
    }

    // Method to enter shift preferences for an employee
    public void enterPreferences(Integer id, List<Pair<DayOfTheWeek, PartOfDay>> shiftPreferences) {
        try {
            employeeController.enterPreferences(id, shiftPreferences);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}