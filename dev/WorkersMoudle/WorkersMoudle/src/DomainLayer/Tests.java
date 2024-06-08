package DomainLayer;

import DomainLayer.Branches.BranchController;
import DomainLayer.Employees.EmployeeController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {


    private EmployeeController employeeController;
    private BranchController branchController;
    //requirement tests
    @BeforeEach
    public void setUpTests() {
        employeeController = new EmployeeController();
        branchController = new BranchController(employeeController);
    }

    //1	HR	Functional	The system must support registration
    //of new employees.

    @Test
    public void testRegisterEmployee() {
        try {
            employeeController.addEmployee("Employee", null, 123456, 10000, 0);
            assertNotEquals(employeeController.getEmployees().size(), 0);
        } catch (Exception e) {
            fail();
        }
    }

    //The system must support registration
    //of the HR manager
    @Test
    public void testRegisterHRManager() {
        try {
            employeeController.setHrManager("HRManager", "password", 123456, 10000, 0);
            assertNotNull(employeeController.getHrManager());
        } catch (Exception e) {
            fail();
        }
    }
    //The system must  allow employees to enter the times when they won’t be available to work.
    @Test
    public void testEmployeeUnavailableTimes() {
        try {
            employeeController.addEmployee("Employee", null, 123456, 10000, 0);
            employeeController.removeCantWork();
            assertNotEquals(employeeController.getEmployees().get(0).getUnavailableTimes().size(), 0);
        } catch (Exception e) {
            fail();
        }
    }

    //The system  must have shift assignment screen which will enable the manager to assign shifts while taking in consideration  the role of each employee.


    //The system must save the past shifts.


    //The system must allow the HR manager to choose which roles are needed in each shift

    //The system can allow employees to exchange shifts.


    //The system can allow employees to enter their preferences for shifts.

}
