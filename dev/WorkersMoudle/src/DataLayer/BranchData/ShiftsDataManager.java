package DataLayer.BranchData;

import DataLayer.AbstractDataManager;
import DataLayer.DateEncryptDecrypt;
import DomainLayer.Branches.PartOfDay;
import DomainLayer.Employees.Role;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class ShiftsDataManager extends AbstractDataManager<ShiftsDTO> {

    public static final String tableName = "ShiftToEmployeeTable";

    public ShiftsDataManager() {
        super(tableName);
    }



    public void addEmployeeToShift(int BID, HashMap<Integer, Role> EIDs, Date date, PartOfDay partOfDay, List<Role> neededRoles) {
        this.insertDTO(new ShiftsDTO(BID, EIDs, date, partOfDay, neededRoles));
    }

    public void deleteEmployeeFromShift(int BID, HashMap<Integer, Role> EIDs, Date date, PartOfDay partOfDay, List<Role> neededRoles) {
        ShiftsDTO dto = new ShiftsDTO(BID, EIDs, date, partOfDay, neededRoles);
        this.deleteDTO(dto);
    }


    @Override
    public boolean insertDTO(ShiftsDTO dto) {
        int result = -1;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String query = "INSERT INTO " + this.tableName + " (" + ShiftsDTO.COLUMN_NAME_BID + ", " + ShiftsDTO.COLUMN_NAME_EID + ", " + ShiftsDTO.COLUMN_NAME_DATE + ", " + ShiftsDTO.COLUMN_NAME_PART_OF_DAY + ", " + ShiftsDTO.COLUMN_NAME_ROLES + ", " + ShiftsDTO.COLUMN_NAME_E_ROLE + ") " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(this.connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, dto.getBID());
            statement.setInt(2, dto.getEIDs().get(0));
            statement.setString(3, DateEncryptDecrypt.encryptDate(dto.getDate()));
            statement.setInt(4, partOfDayToInt(dto.getPartOfDay()));
            statement.setInt(5, rolesToInt(dto.getNeededRoles()));

            List<Role> role = new LinkedList<Role>();
            role.add(dto.getRole(dto.getEIDs().get(0)));
            statement.setInt(6, rolesToInt(new LinkedList<Role>(role)));


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result > 0;
    }

    @Override
    protected ShiftsDTO convertReaderToDTO(ResultSet resultSet) throws SQLException {


        Integer BID = resultSet.getInt(ShiftsDTO.COLUMN_NAME_BID);
        String date = resultSet.getString(ShiftsDTO.COLUMN_NAME_DATE);
        int partOfDay = resultSet.getInt(ShiftsDTO.COLUMN_NAME_PART_OF_DAY);
        Integer roles = resultSet.getInt(ShiftsDTO.COLUMN_NAME_ROLES);
        Integer EID = resultSet.getInt(ShiftsDTO.COLUMN_NAME_EID);
        Integer E_ROLE = resultSet.getInt(ShiftsDTO.COLUMN_NAME_E_ROLE);

        HashMap<Integer, Role> employee = new HashMap<>();
        employee.put(EID, intToRoles(E_ROLE).remove(0));



        return new ShiftsDTO(BID, employee, DateEncryptDecrypt.decryptDate(date), intToPartOfDay(partOfDay), intToRoles(roles));
    }

    protected boolean deleteDTO(ShiftsDTO dto) {
        int res = -1;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String query = "DELETE FROM " + tableName +
                " WHERE " + ShiftsDTO.COLUMN_NAME_BID + " = "+dto.getBID() + " AND " + ShiftsDTO.COLUMN_NAME_DATE + " = "+ DateEncryptDecrypt.encryptDate(dto.getDate())
                +" AND " + ShiftsDTO.COLUMN_NAME_PART_OF_DAY + " = "+ partOfDayToInt(dto.getPartOfDay());
        try (Connection connection = DriverManager.getConnection(this.connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {
            res = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res > 0;
    }


    public List<ShiftsDTO> getBranchSifts(int BID) {
        List<ShiftsDTO> shiftsDTOS = combineShifts(this.loadData());

        return shiftsDTOS.stream().filter(shift -> shift.getBID() == BID).toList();
    }

    private PartOfDay intToPartOfDay(int partOfDay) {
        return switch (partOfDay) {
            case 0 -> PartOfDay.Morning;
            case 1 -> PartOfDay.Evening;
            default -> throw new IllegalStateException("Unexpected value: " + partOfDay);
        };

    }

    private int partOfDayToInt(PartOfDay partOfDay) {
        return switch (partOfDay) {
            case Morning -> 0;
            case Evening -> 1;
        };
    }

    private List<Role> intToRoles(int roles) {
        List<Role> neededRoles = new ArrayList<>();
        while (roles > 0) {
            switch (roles % 10) {
                case 1 -> neededRoles.add(Role.ShiftManager);
                case 2 -> neededRoles.add(Role.Cashier);
                case 3 -> neededRoles.add(Role.Usher);
                case 4 -> neededRoles.add(Role.StoreKeeper);
                case 5 -> neededRoles.add(Role.Butcher);
                case 6 -> neededRoles.add(Role.CheeseMan);
                case 7 -> neededRoles.add(Role.Driver);
            }
            roles = roles / 10;
        }
        return neededRoles;
    }

    private int rolesToInt(List<Role> roles) {
        int res = 0;
        for (Role role : roles) {
            res*= 10;
            res += switch (role) {
                case ShiftManager -> 1;
                case Cashier -> 2;
                case Usher -> 3;
                case StoreKeeper -> 4;
                case Butcher -> 5;
                case CheeseMan -> 6;
                case Driver -> 7;
            };
        }
        return res;
    }

    public void addNeededRoles(int bid, Date date, PartOfDay partOfDay, List<Role> neededRoles) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        String query = "UPDATE " + this.tableName + " SET " +
                ShiftsDTO.COLUMN_NAME_ROLES + " = ? " +
                "WHERE " + ShiftsDTO.COLUMN_NAME_BID + " = ? " +
                "AND " + ShiftsDTO.COLUMN_NAME_PART_OF_DAY + " = ?" +
                "AND " + ShiftsDTO.COLUMN_NAME_DATE + " = ?";

        try (Connection connection = DriverManager.getConnection(this.connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, rolesToInt(neededRoles));
            statement.setInt(2, bid);
            statement.setInt(3, partOfDayToInt(partOfDay));
            statement.setString(4, DateEncryptDecrypt.encryptDate(date));

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static List<ShiftsDTO> combineShifts(List<ShiftsDTO> shifts) {
        // Create a map to group and combine shifts
        Map<String, ShiftsDTO> combinedShiftsMap = new HashMap<>();

        for (ShiftsDTO shift : shifts) {
            String key = generateKey(shift.getBID(), shift.getDate(), shift.getPartOfDay());
            combinedShiftsMap.merge(key, shift, ShiftsDTO::combineShifts);
        }

        // Convert the map values to a list
        return new ArrayList<>(combinedShiftsMap.values());
    }

    private static String generateKey(int BID, Date date, PartOfDay partOfDay) {
        return BID + "_" + date.toString() + "_" + partOfDay;
    }
}

