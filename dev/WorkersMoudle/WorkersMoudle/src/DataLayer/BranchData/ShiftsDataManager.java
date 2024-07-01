package DataLayer.BranchData;

import DataLayer.AbstractDataManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ShiftsDataManager extends AbstractDataManager<ShiftsDTO> {

    public static final String tableName = "ShiftToEmployeeTable";
    public ShiftsDataManager() {
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

    public List<ShiftsDTO> getBranchSifts(int BID) {
        return this.loadData().stream().filter(shift -> shift.getBID() == BID).toList();
    }
}
