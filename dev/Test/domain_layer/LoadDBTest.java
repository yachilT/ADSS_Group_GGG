package domain_layer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoadDBTest extends DBTest {

    AreaFacade areaFacade;
    TruckFacade truckFacade;
    DriverFacade driverFacade;
    ShipmentHistory shipmentHistory;


    public LoadDBTest(String dbToCopy) throws IOException {
        super("EmptyDB.db");
    }

    @BeforeEach
    void setup() {
        areaFacade = new AreaFacade(testDBPath);
        truckFacade = new TruckFacade(testDBPath);
        driverFacade = new DriverFacade(testDBPath);
        shipmentHistory = new ShipmentHistory(testDBPath);
    }
    @AfterEach
    void tearDown() {try{deleteDB();} catch (IOException io) {io.printStackTrace();};}
    @Test
    void loadTruck() {
        String TABLE_NAME = "Trucks";
        Truck testTruck = new Truck(100, "VOLVO", 100, 2000);
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            fail();
        }
        try (Connection connection = DriverManager.getConnection(URL)){

            String insertSQL = "INSERT INTO " + TABLE_NAME + "(truckNumber, model, emptyWeight, maxWeight) VALUES(?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);

            pstmt.setInt(1, testTruck.getNumber());
            pstmt.setString(2, testTruck.getModel());
            pstmt.setFloat(3, testTruck.getEmptyWeight());
            pstmt.setFloat(4, testTruck.getMaxWeight());

            pstmt.executeUpdate();
        }
        catch (SQLException ex){
            fail();
        }
        truckFacade.loadAll();
        assertTrue(truckFacade.getTrucks().contains(testTruck));
    }
    @Test
    void loadDriver() {
        String TABLE_NAME = "Drivers";
        Driver testDriver = new Driver(111, "Nachum Takoom", new License(222));
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            fail();
        }
        try (Connection connection = DriverManager.getConnection(URL)){

            String insertSQL = "INSERT INTO " + TABLE_NAME + "(driverId, driverName, license) VALUES(?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);

            pstmt.setInt(1, testDriver.getId());
            pstmt.setString(2, testDriver.getDriverName());
            pstmt.setFloat(3, testDriver.getLicense().allowedWeight);


            pstmt.executeUpdate();
        }
        catch (SQLException ex){
            fail();
        }
        driverFacade.loadAll();
        assertTrue(driverFacade.getDrivers().contains(testDriver));
    }
    @Test
    void loadArea(){
        Site site = new Site("Hashalom 15", "David", "050");
        Set<Site> sites = new HashSet<>();
        sites.add(site);
        Area area = new Area("Lala land", sites);

    }
}