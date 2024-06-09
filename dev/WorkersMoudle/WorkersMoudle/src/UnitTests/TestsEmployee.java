package UnitTests;

import DomainLayer.Branches.BranchController;
import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Employees.Employee;
import DomainLayer.Employees.EmployeeController;
import DomainLayer.Employees.Role;
import DomainLayer.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestsEmployee {

    private Employee employee;
    private List<Role> roles;

    @BeforeEach
    public void setUp() throws Exception {
        roles = new ArrayList<>();
        roles.add(Role.Butcher);
        employee = new Employee(1, "John Doe", roles, 123456, 50000, 1);
    }

    @Test
    public void testConstructor() throws Exception {
        assertEquals(1, employee.getId());
        assertEquals("John Doe", employee.getName());
        assertEquals(roles, employee.getRoles());
        assertEquals(123456, employee.getBankAccountNumber());
        assertEquals(50000, employee.getSalary());
        assertEquals(1, employee.getBranchId());
        assertNotNull(employee.getDateJoined());
        assertNull(employee.getDateLeft());
        assertTrue(employee.getShiftPreferences().isEmpty());
        assertTrue(employee.getShiftCantWork().isEmpty());
    }

    @Test
    public void testConstructorNegativeSalary() {
        Exception exception = assertThrows(Exception.class, () -> {
            new Employee(2, "Jane Doe", roles, 123456, -5000, 1);
        });
        assertEquals("Salary must be positive", exception.getMessage());
    }

    @Test
    public void testSetPassword() {
        employee.setPassword("password123");
        assertTrue(employee.checkPassword("password123"));
        assertFalse(employee.checkPassword("wrongpassword"));
    }

    @Test
    public void testSetAndGetMethods() {
        employee.setName("Jane Doe");
        assertEquals("Jane Doe", employee.getName());

        employee.setBankAccountNumber(654321);
        assertEquals(654321, employee.getBankAccountNumber());

        employee.setSalary(60000);
        assertEquals(60000, employee.getSalary());

        Date newDate = new Date();
        employee.setDateJoined(newDate);
        assertEquals(newDate, employee.getDateJoined());

        employee.setBranchId(2);
        assertEquals(2, employee.getBranchId());

        Date leaveDate = new Date();
        employee.setDateLeft(leaveDate);
        assertEquals(leaveDate, employee.getDateLeft());
    }

    @Test
    public void testShiftPreferences() throws Exception {
        Pair<DayOfTheWeek, PartOfDay> shift = new Pair<>(DayOfTheWeek.Monday, PartOfDay.Morning);
        employee.addShiftPreference(shift);
        assertTrue(employee.getShiftPreferences().contains(shift));

        employee.removeShiftPreference(shift);
        assertFalse(employee.getShiftPreferences().contains(shift));

        Exception exception = assertThrows(Exception.class, () -> {
            employee.removeShiftPreference(shift);
        });
        assertEquals("Shift not found in preferences", exception.getMessage());
    }

    @Test
    public void testShiftCantWork() throws Exception {
        Pair<DayOfTheWeek, PartOfDay> shift = new Pair<>(DayOfTheWeek.Friday, PartOfDay.Evening);
        employee.addShiftCantWork(shift);
        assertTrue(employee.getShiftCantWork().contains(shift));

        employee.removeShiftCantWork(shift);
        assertFalse(employee.getShiftCantWork().contains(shift));

        Exception exception = assertThrows(Exception.class, () -> {
            employee.removeShiftCantWork(shift);
        });
        assertEquals("Shift not found in cant work shifts", exception.getMessage());
    }



}
