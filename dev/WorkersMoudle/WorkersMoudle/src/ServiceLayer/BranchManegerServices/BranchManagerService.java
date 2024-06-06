package ServiceLayer.BranchManegerServices;

import DomainLayer.Pair;

import java.util.Date;
import java.util.List;

public class BranchManagerService {

    // Method to register an employee with given ID and employee data
    public void empRegister(Long id, int empId, String name, int bankAccountNum, double salary,
                            Date dateJoined, int branchId, List<String> qualification) {
        // Implementation goes here
    }

    // Method to prepare shifts with a list of roles and the number needed for each role
    public void prepareShift(List<Pair<DomainLayer.Employees.Role, Integer>> listOfRoles) {
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

    public ShiftToSend getShift() {// add parameters
        // Implementation goes here
        return null;
    }

}
