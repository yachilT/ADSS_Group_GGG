package DataLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDataManager extends AbstractDataManager<EmployeeDTO>{
    // Constants related to the Users database schema
    public static final String EMPLOYEE_TABLE = "Employee";
    public static final String ID_COLUMN = "Id";
    public static final String NAME_COLUMN = "Name";
    public static final String PASSWORD_COLUMN = "PASSWORD";

    //more columns needed
    public static final int ID_COLUMN_ORDINAL = 1;


    public EmployeeDataManager() {
        super(EMPLOYEE_TABLE);
    }

    @Override
    public boolean insertDTO(EmployeeDTO dto) {
        int result = -1;
        String query = "INSERT INTO " + this.tableName + " (" + ID_COLUMN + ", " + PASSWORD_COLUMN + ") VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(this.connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {

            //statement.setString(1, dto.getEmail());
            //statement.setString(2, dto.getPassword());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result > 0;
    }

    @Override
    protected EmployeeDTO convertReaderToDTO(ResultSet resultSet) throws SQLException {
        Integer Id = resultSet.getInt(ID_COLUMN_ORDINAL);
        String password = resultSet.getString(ID_COLUMN_ORDINAL + 1);
        //return new EmployeeDTO(Id, password);
        return new EmployeeDTO();
    }


}
