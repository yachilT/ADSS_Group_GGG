package DataLayer;

import java.io.File;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDataManager<T> {
    // The database file name
    public static final String BRANCH_DB = "branch.db";
    // The connection string
    protected String connectionString;
    // The SQL table name
    protected final String tableName;

    // Constructor initializes the connectionString and tableName parameters
    public AbstractDataManager(String tableName) {
        String path = Paths.get(System.getProperty("user.dir"), BRANCH_DB).toAbsolutePath().toString();
        connectionString = "jdbc:sqlite:" + path;
        this.tableName = tableName;
    }

    // Abstract method that should insert a new DTO to the table
    public abstract boolean insertDTO(T dto);

    // Abstract method that should convert a ResultSet to a DTO
    protected abstract T convertReaderToDTO(ResultSet resultSet) throws SQLException;

    // Method that deletes all of the table's rows
    public boolean deleteData() {
        int res = -1;
        String query = "DELETE FROM " + this.tableName + ";";

        try (Connection connection = DriverManager.getConnection(this.connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {
            res = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res > 0;
    }

    // Method that is used to get the data from the DB
    public List<T> loadData() {
        List<T> result = new ArrayList<>();
        String query = "SELECT * FROM " + this.tableName + ";";

        try (Connection connection = DriverManager.getConnection(this.connectionString);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                result.add(convertReaderToDTO(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Method that updates a value in the DB
    public boolean updateDTO(String rowFilteringName, Object rowFilteringValue, String attributeName, Object attributeValue) {
        int res = -1;
        String query = "UPDATE " + this.tableName + " SET " + attributeName + " = ? WHERE " + rowFilteringName + " = ?";

        try (Connection connection = DriverManager.getConnection(this.connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setObject(1, attributeValue);
            statement.setObject(2, rowFilteringValue);
            res = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res > 0;
    }
}


