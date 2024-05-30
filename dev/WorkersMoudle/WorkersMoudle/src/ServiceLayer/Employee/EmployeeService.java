package WorkersMoudle.WorkersMoudle.src.ServiceLayer.Employee;


import WorkersMoudle.WorkersMoudle.src.Pair;

import java.time.DayOfWeek;

import java.util.List;



public class EmployeeService {

    // Method to sign up a new employee
    public void signUp(Long id, String password) {
        // Implementation goes here
    }

    // Method to log in an existing employee
    public void login(Long id, String password) {
        // Implementation goes here
    }

    // Method to enter time out for an employee
    public void enterTimeOut(Long id, List<Pair<DayOfWeek, Integer>> times) {
        // Implementation goes here
    }

    // Method to exchange shifts between two employees
    public void exchangeShift(Long id1, Long id2, Long shiftId) {
        // Implementation goes here
    }

    // Method to enter shift preferences for an employee
    public void enterPreferences(Long id, List<Long> shiftPreferences) {
        // Implementation goes here
    }
}