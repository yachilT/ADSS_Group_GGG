package UnitTests;

import DataLayer.EmployeeDTO;
import DataLayer.EmployeeDataManager;
import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Employees.Role;
import DomainLayer.Pair;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class EmployeeDataManagerTest {

    private EmployeeDataManager dataManager;
    private EmployeeDTO testDTO;

    @AfterEach
    public void tearDown() {
        dataManager.deleteData();
    }

    @BeforeEach
    public void setUp() {
        dataManager = new EmployeeDataManager();
        dataManager.testMode();



        // Setup a test EmployeeDTO
        List<Role> roles = Arrays.asList(Role.Butcher, Role.Cashier);
        List<Pair<DayOfTheWeek, PartOfDay>> shiftPreferences = Arrays.asList(new Pair<>(DayOfTheWeek.Monday, PartOfDay.Morning));
        List<Pair<DayOfTheWeek, PartOfDay>> shiftCantWork = Arrays.asList(new Pair<>(DayOfTheWeek.Tuesday, PartOfDay.Evening));

        testDTO = new EmployeeDTO(1, "Test Name", "password", roles, 12345, 50000, new Date(), 1, null, shiftPreferences, shiftCantWork, 2, null);
    }

    @Test
    public void testInsertDTO() {
        boolean result = dataManager.insertDTO(testDTO);
        assertTrue(result);
    }


    @Test
    public void testFetchRoles() throws SQLException {
        dataManager.insertDTO(testDTO);
        assertEquals(2, dataManager.getNumOfRoles(testDTO.getId()));
    }

    @Test
    public void testFetchShiftPreferences() throws SQLException {
        dataManager.insertDTO(testDTO);

        assertEquals(1, dataManager.getNumOfShiftsPreferences(testDTO.getId()));
    }

    @Test
    public void testFetchShiftCantWork() throws SQLException {
        dataManager.insertDTO(testDTO);

        assertEquals(1, dataManager.getNumOfShiftsCantWork(testDTO.getId()));
    }

    @Test
    public void testLoadDatabase() throws Exception {
        dataManager.insertDTO(testDTO);
        List<EmployeeDTO> employees = dataManager.loadDatabase();
        assertFalse(employees.isEmpty());
    }

    @Test
    public void testDeleteEmployee() throws SQLException {
        dataManager.insertDTO(testDTO);
        String deleteQuery = "DELETE FROM EmployeeTable WHERE EID = ?";

        try (Connection connection = DriverManager.getConnection(dataManager.getConnectionString());
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, testDTO.getId());
            int rowsAffected = statement.executeUpdate();
            assertEquals(1, rowsAffected);
        }
    }

}
