package UnitTests;

import DataLayer.BranchData.BranchDTO;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import DataLayer.BranchData.BranchDataManager;

import java.sql.*;

public class BranchDataManagerTest {


    private BranchDataManager dataManager;
    private BranchDTO testDTO;

    @BeforeEach
    public void setUp() {
        dataManager = new BranchDataManager();
        dataManager.testMode();

        // Setup a test BranchDTO
        testDTO = new BranchDTO(1, "Test Branch", "123 Test Address");
    }

    @AfterEach
    public void tearDown() {
        dataManager.deleteData();
    }

    @Test
    public void testInsertDTO() {
        boolean result = dataManager.insertDTO(testDTO);
        assertTrue(result);
    }




    @Test
    public void testUpdateDTO() throws SQLException {
        dataManager.insertDTO(testDTO);
        testDTO = new BranchDTO(testDTO.getId(), "Updated Name", "456 Updated Address");
        boolean result = dataManager.insertDTO(testDTO); // Insert should fail, so use a proper update method here.
        assertFalse(result); // Insert should fail due to duplicate primary key, proper update method needed
    }


    @Test
    public void testFetchById() throws SQLException {
        dataManager.insertDTO(testDTO);
        String query = "SELECT * FROM " + BranchDataManager.TABLE_NAME + " WHERE " + BranchDataManager.ID_COLUMN + " = ?";
        try (Connection connection = DriverManager.getConnection(dataManager.getConnectionString());
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, testDTO.getId());
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
        }
    }

    @Test
    public void testFetchAll() throws SQLException {
        dataManager.insertDTO(testDTO);
        String query = "SELECT * FROM " + BranchDataManager.TABLE_NAME;
        try (Connection connection = DriverManager.getConnection(dataManager.getConnectionString());
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
        }
    }

    @Test
    public void testFetchByName() throws SQLException {
        dataManager.insertDTO(testDTO);
        String query = "SELECT * FROM " + BranchDataManager.TABLE_NAME + " WHERE " + BranchDataManager.NAME_COLUMN + " = ?";
        try (Connection connection = DriverManager.getConnection(dataManager.getConnectionString());
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, testDTO.getName());
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
        }
    }

    @Test
    public void testFetchByAddress() throws SQLException {
        dataManager.insertDTO(testDTO);
        String query = "SELECT * FROM " + BranchDataManager.TABLE_NAME + " WHERE " + BranchDataManager.ADDRESS_COLUMN + " = ?";
        try (Connection connection = DriverManager.getConnection(dataManager.getConnectionString());
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, testDTO.getAddress());
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
        }
    }


}
