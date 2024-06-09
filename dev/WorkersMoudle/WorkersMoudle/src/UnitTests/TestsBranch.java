package UnitTests;

import DomainLayer.Branches.Branch;
import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Branches.WeeklyShifts;
import DomainLayer.Employees.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestsBranch {

    private Branch branch;

    @BeforeEach
    public void setUp() {
        branch = new Branch(1, "Main Branch", "123 Main St");
    }

    @Test
    public void testConstructor() {
        assertEquals(1, branch.getId());
        assertEquals("Main Branch", branch.getName());
        assertEquals("123 Main St", branch.getAddress());
        assertNotNull(branch.getCurrentWeek());
        assertEquals(5, branch.getUpcomingweeks().size());
        assertTrue(branch.getPastweeks().isEmpty());
    }

    @Test
    public void testSetUpShift() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.Cashier);
        roles.add(Role.Butcher);
        branch.setUpShift(DayOfTheWeek.Monday, PartOfDay.Morning, roles);
        assertEquals(roles, branch.getUpcomingweeks().get(0).getShift(DayOfTheWeek.Monday, PartOfDay.Morning).getNeededRoles());
    }

    @Test
    public void testAddWeek() {
        Date today = new Date();
        branch.AddWeek(today);
        assertNotNull(branch.getCurrentWeek());
        assertTrue(branch.getUpcomingweeks().size() >= 5);
    }


    @Test
    public void testAddEmployeeToShift() throws Exception {
        Role role = Role.ShiftManager;
        Integer employeeId = 123;
        branch.addEmployeeToShift(employeeId, role, DayOfTheWeek.Wednesday, PartOfDay.Evening);
        assertTrue(branch.getUpcomingweeks().get(0).getShift(DayOfTheWeek.Wednesday, PartOfDay.Evening).getEmployees().contains(employeeId));
    }

    @Test
    public void testAddEmployeeToShiftNullEmployee() {
        Exception exception = assertThrows(Exception.class, () -> {
            branch.addEmployeeToShift(null, Role.ShiftManager, DayOfTheWeek.Wednesday, PartOfDay.Evening);
        });
        assertEquals("Employee is null", exception.getMessage());
    }

    @Test
    public void testExchangeShift() throws Exception {
        Integer id1 = 101;
        Integer id2 = 102;
        Role role = Role.Cashier;
        branch.addEmployeeToShift(id1, role, DayOfTheWeek.Tuesday, PartOfDay.Morning);
        branch.addEmployeeToShift(id2, role, DayOfTheWeek.Tuesday, PartOfDay.Morning);
        branch.exchangeShift(id1, id2, DayOfTheWeek.Tuesday, PartOfDay.Morning, 0, role);

    }
}

