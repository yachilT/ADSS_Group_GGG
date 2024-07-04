package DataLayer.BranchData;

import DataLayer.AbstractDataManager;
import DomainLayer.Branches.WeeklyShifts;

import java.sql.*;
import java.util.List;

public class BranchDataManager extends AbstractDataManager<BranchDTO> {

    public static final String TABLE_NAME = "BranchTable";
    public static final String WEEKLY_SHIFTS_TABLE_NAME = "ShiftToEmployeeTable";
    public static final String ID_COLUMN = "ID";
    public static final String NAME_COLUMN = "NAME";
    public static final String ADDRESS_COLUMN = "ADDRESS";
    public static final int ID_COLUMN_ORDINAL = 1;


    public BranchDataManager() {
        super(TABLE_NAME);
    }


    @Override
    public boolean insertDTO(BranchDTO dto) {
        int result = -1;
        String query = "INSERT INTO " + this.tableName + " (" + ID_COLUMN + ", " + NAME_COLUMN + ", "+ ADDRESS_COLUMN + ") VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(this.connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, dto.getId());
            statement.setString(2, dto.getName());
            statement.setString(3, dto.getAddress());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result > 0;
    }

    @Override
    protected BranchDTO convertReaderToDTO(ResultSet resultSet) throws SQLException {
        Integer Id = resultSet.getInt(ID_COLUMN_ORDINAL);
        String name = resultSet.getString(ID_COLUMN_ORDINAL + 1);
        String address = resultSet.getString(ID_COLUMN_ORDINAL + 2);


        return new BranchDTO(Id, name, address);

    }


}
