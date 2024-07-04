package DataLayer;

import DomainLayer.Branches.DayOfTheWeek;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Employees.Employee;
import DomainLayer.Employees.Role;
import DomainLayer.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmployeeDataManager extends AbstractDataManager<EmployeeDTO> {
    // Constants related to the Users database schema
    public static final String EMPLOYEE_TABLE = "EmployeeTable";
    public static final String ID_COLUMN = "EID";
    public static final String NAME_COLUMN = "NAME";
    public static final String PASSWORD_COLUMN = "PASSWORD";
    public static final String BANKACCOUNT_COLUMN = "BANKACCOUNT";
    public static final String SALARY_COLUMN = "SALARY";
    public static final String DATEJOINED_COLUMN = "DATEJOINED";
    public static final String BRANCHID_COLUMN = "BID";
    public static final String MANAGER_COLUMN = "MANAGER";

    // More columns needed
    public static final int ID_COLUMN_ORDINAL = 1;

    public EmployeeDataManager() {
        super(EMPLOYEE_TABLE);
    }

    @Override
    public boolean insertDTO(EmployeeDTO dto){
        int result = -1;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String query = "INSERT INTO " + this.tableName + " (EID, BID, NAME, PASSWORD, BANKACCOUNT, SALARY, DATEJOINED, MANAGER) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(this.connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, dto.getId());
            statement.setInt(2, dto.getBranchId());
            statement.setString(3, dto.getName());
            if (dto.getPassword() != null) {
                statement.setString(4, dto.getPassword());
            } else {
                statement.setNull(4, java.sql.Types.VARCHAR);
            }
            statement.setInt(5, dto.getBankAccountNumber());
            statement.setDouble(6, dto.getSalary());
            statement.setString(7, DateEncryptDecrypt.encryptDate(dto.getDateJoined()));
            statement.setInt(8, dto.getManager());  // Set manager

            result = statement.executeUpdate();

            if (result > 0) {
                insertRoles(dto, connection);
                insertPref(dto, connection); // Insert preferences and can't work times
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result > 0;
    }

    private boolean insertRoles(EmployeeDTO employeeDTO, Connection connection) {
        boolean result = true;
        if(employeeDTO.getRoles().isEmpty())
            return result;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String insertRoleQuery = "INSERT INTO RolesTable (EID, ROLE) VALUES (?, ?)";
        try (PreparedStatement insertRoleStatement = connection.prepareStatement(insertRoleQuery)) {
            for (Role role : employeeDTO.getRoles()) {
                insertRoleStatement.setInt(1, employeeDTO.getId());
                insertRoleStatement.setInt(2, role.ordinal());
                insertRoleStatement.addBatch();
            }
            insertRoleStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    private boolean insertPref(EmployeeDTO employeeDTO, Connection connection) {
        boolean result = true;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String insertPrefQuery = "INSERT INTO PreferencesTable (EID, DAY, PARTOFDAY) VALUES (?, ?, ?)";
        String insertCantWorkQuery = "INSERT INTO CantWorkTable (EID, DAY, PARTOFDAY) VALUES (?, ?, ?)";

        try (PreparedStatement insertPrefStatement = connection.prepareStatement(insertPrefQuery);
             PreparedStatement insertCantWorkStatement = connection.prepareStatement(insertCantWorkQuery)) {

            for (Pair<DayOfTheWeek, PartOfDay> pref : employeeDTO.getShiftPreferences()) {
                insertPrefStatement.setInt(1, employeeDTO.getId());
                insertPrefStatement.setInt(2, pref.getKey().ordinal());
                insertPrefStatement.setInt(3, pref.getValue().ordinal());
                insertPrefStatement.addBatch();
            }
            insertPrefStatement.executeBatch();

            for (Pair<DayOfTheWeek, PartOfDay> cantWork : employeeDTO.getShiftCantWork()) {
                insertCantWorkStatement.setInt(1, employeeDTO.getId());
                insertCantWorkStatement.setInt(2, cantWork.getKey().ordinal());
                insertCantWorkStatement.setInt(3, cantWork.getValue().ordinal());
                insertCantWorkStatement.addBatch();
            }
            insertCantWorkStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    public boolean updateDTO(EmployeeDTO dto) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        int result = -1;
        String query = "UPDATE " + this.tableName + " SET " +
                "BID = ?, " +
                "NAME = ?, " +
                "PASSWORD = ?, " +
                "BANKACCOUNT = ?, " +
                "SALARY = ?, " +
                "DATEJOINED = ?, " +
                "MANAGER = ? " +  // Update manager
                "WHERE EID = ?";

        try (Connection connection = DriverManager.getConnection(this.connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, dto.getBranchId());
            statement.setString(2, dto.getName());
            if (dto.getPassword() != null) {
                statement.setString(3, dto.getPassword());
            } else {
                statement.setNull(3, java.sql.Types.VARCHAR);
            }
            statement.setInt(4, dto.getBankAccountNumber());
            statement.setDouble(5, dto.getSalary());
            statement.setString(6, DateEncryptDecrypt.encryptDate(dto.getDateJoined()));
            statement.setInt(7, dto.getManager());  // Set manager
            statement.setInt(8, dto.getId());

            result = statement.executeUpdate();

            if (result > 0) {
                updateRoles(dto, connection);
                updatePref(dto, connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result > 0;
    }

    private boolean updateRoles(EmployeeDTO dto, Connection connection) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        boolean result = true;

        String deleteRolesQuery = "DELETE FROM RolesTable WHERE EID = ?";
        String insertRoleQuery = "INSERT INTO RolesTable (EID, ROLE) VALUES (?, ?)";

        try (PreparedStatement deleteRolesStatement = connection.prepareStatement(deleteRolesQuery);
             PreparedStatement insertRoleStatement = connection.prepareStatement(insertRoleQuery)) {

            // Delete existing roles
            deleteRolesStatement.setInt(1, dto.getId());
            deleteRolesStatement.executeUpdate();

            // Insert new roles
            for (Role role : dto.getRoles()) {
                insertRoleStatement.setInt(1, dto.getId());
                insertRoleStatement.setInt(2, role.ordinal());
                insertRoleStatement.addBatch();
            }
            insertRoleStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    private boolean updatePref(EmployeeDTO dto, Connection connection) {
        boolean result = true;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String deletePrefQuery = "DELETE FROM PreferencesTable WHERE EID = ?";
        String deleteCantWorkQuery = "DELETE FROM CantWorkTable WHERE EID = ?";
        String insertPrefQuery = "INSERT INTO PreferencesTable (EID, DAY, PARTOFDAY) VALUES (?, ?, ?)";
        String insertCantWorkQuery = "INSERT INTO CantWorkTable (EID, DAY, PARTOFDAY) VALUES (?, ?, ?)";

        try (PreparedStatement deletePrefStatement = connection.prepareStatement(deletePrefQuery);
             PreparedStatement deleteCantWorkStatement = connection.prepareStatement(deleteCantWorkQuery);
             PreparedStatement insertPrefStatement = connection.prepareStatement(insertPrefQuery);
             PreparedStatement insertCantWorkStatement = connection.prepareStatement(insertCantWorkQuery)) {

            // Delete existing preferences
            deletePrefStatement.setInt(1, dto.getId());
            deletePrefStatement.executeUpdate();

            // Delete existing can't work times
            deleteCantWorkStatement.setInt(1, dto.getId());
            deleteCantWorkStatement.executeUpdate();

            // Insert new preferences
            for (Pair<DayOfTheWeek, PartOfDay> pref : dto.getShiftPreferences()) {
                insertPrefStatement.setInt(1, dto.getId());
                insertPrefStatement.setInt(2, pref.getKey().ordinal());
                insertPrefStatement.setInt(3, pref.getValue().ordinal());
                insertPrefStatement.addBatch();
            }
            insertPrefStatement.executeBatch();

            // Insert new can't work times
            for (Pair<DayOfTheWeek, PartOfDay> cantWork : dto.getShiftCantWork()) {
                insertCantWorkStatement.setInt(1, dto.getId());
                insertCantWorkStatement.setInt(2, cantWork.getKey().ordinal());
                insertCantWorkStatement.setInt(3, cantWork.getValue().ordinal());
                insertCantWorkStatement.addBatch();
            }
            insertCantWorkStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }

    @Override
    protected EmployeeDTO convertReaderToDTO(ResultSet reader) throws SQLException {
        int id = reader.getInt(ID_COLUMN);
        int branchId = reader.getInt(BRANCHID_COLUMN);
        String name = reader.getString(NAME_COLUMN);
        String password = reader.getString(PASSWORD_COLUMN);
        int bankAccountNumber = reader.getInt(BANKACCOUNT_COLUMN);
        double salary = reader.getDouble(SALARY_COLUMN);
        Date dateJoined = DateEncryptDecrypt.decryptDate(reader.getString(DATEJOINED_COLUMN));
        Integer manager = reader.getObject(MANAGER_COLUMN) != null ? reader.getInt(MANAGER_COLUMN) : null;  // Read manager

        // Fetch roles, shift preferences, and shift can't work times
        List<Role> roles = fetchRoles(id);
        List<Pair<DayOfTheWeek, PartOfDay>> shiftPreferences = fetchShiftPreferences(id);
        List<Pair<DayOfTheWeek, PartOfDay>> shiftCantWork = fetchShiftCantWork(id);

        EmployeeDTO output = new EmployeeDTO(id, name, password, roles, bankAccountNumber, salary, dateJoined, branchId, null, shiftPreferences, shiftCantWork, manager);
        return output;
    }

    private List<Role> fetchRoles(int employeeId) throws SQLException {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT ROLE FROM RolesTable WHERE EID = ?";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(this.connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Role role = Role.values()[resultSet.getInt("ROLE")];
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return roles;
    }

    private List<Pair<DayOfTheWeek, PartOfDay>> fetchShiftPreferences(int employeeId) throws SQLException {
        List<Pair<DayOfTheWeek, PartOfDay>> preferences = new ArrayList<>();
        String query = "SELECT DAY, PARTOFDAY FROM PreferencesTable WHERE EID = ?";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(this.connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DayOfTheWeek day = DayOfTheWeek.values()[resultSet.getInt("DAY")];
                PartOfDay partOfDay = PartOfDay.values()[resultSet.getInt("PARTOFDAY")];
                preferences.add(new Pair<>(day, partOfDay));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return preferences;
    }

    private List<Pair<DayOfTheWeek, PartOfDay>> fetchShiftCantWork(int employeeId) throws SQLException {

        List<Pair<DayOfTheWeek, PartOfDay>> cantWork = new ArrayList<>();
        String query = "SELECT DAY, PARTOFDAY FROM CantWorkTable WHERE EID = ?";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(this.connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DayOfTheWeek day = DayOfTheWeek.values()[resultSet.getInt("DAY")];
                PartOfDay partOfDay = PartOfDay.values()[resultSet.getInt("PARTOFDAY")];
                cantWork.add(new Pair<>(day, partOfDay));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return cantWork;
    }


    public List<EmployeeDTO> loadDatabase() throws Exception{
        List<EmployeeDTO> employees = new ArrayList<>();
        String query = "SELECT * FROM " + EMPLOYEE_TABLE;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(this.connectionString);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                // Convert the main employee data
                EmployeeDTO employeeDTO = convertReaderToDTO(resultSet);

                // Fetch and set roles
                employeeDTO.loadRoles(fetchRoles(employeeDTO.getId()));

                // Fetch and set shift preferences
                employeeDTO.setShiftPreferences(fetchShiftPreferences(employeeDTO.getId()));

                // Fetch and set shift can't work times
                employeeDTO.setShiftCantWork(fetchShiftCantWork(employeeDTO.getId()));

                // Add the fully populated employeeDTO to the list
                employees.add(employeeDTO);
            }
        } catch (Exception e) {
            throw e;
        }

        return employees;
    }

}
