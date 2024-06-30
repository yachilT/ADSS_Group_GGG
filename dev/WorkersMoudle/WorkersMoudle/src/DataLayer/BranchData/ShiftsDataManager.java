package DataLayer.BranchData;

import DataLayer.AbstractDataManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShiftsDataManager extends AbstractDataManager<ShiftsDTO> {



    public ShiftsDataManager(String tableName) {
        super(tableName);
    }

    @Override
    public boolean insertDTO(ShiftsDTO dto) {
        return false;
    }

    @Override
    protected ShiftsDTO convertReaderToDTO(ResultSet resultSet) throws SQLException {
        return null;
    }
}
