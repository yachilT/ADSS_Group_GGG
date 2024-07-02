package domain_layer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.HashSet;

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
        try{createDB();} catch (IOException io) {io.printStackTrace();}
        areaFacade = new AreaFacade(testDBPath);
        truckFacade = new TruckFacade(true, testDBPath);
        driverFacade = new DriverFacade(true, testDBPath);
        shipmentHistory = new ShipmentHistory(true, testDBPath);
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
        String TABLE_NAME = "Sites";
        Site site = new Site("Hashalom 15", "David", "050");
        Set<Site> sites = new HashSet<>();
        sites.add(site);
        Area area = new Area("Lala land", sites);
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            fail();
        }

        try (Connection connection = DriverManager.getConnection(URL)){

            String insertSQL = "INSERT INTO " + TABLE_NAME + "(address, areaName, contactName, contactNumber) VALUES(?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);

            pstmt.setString(1, site.getAddress());
            pstmt.setString(2, area.getAreaName());
            pstmt.setString(3, site.getContactName());
            pstmt.setString(4, site.getContactNumber());

            pstmt.executeUpdate();
        }
        catch (SQLException ex) {
            fail();
        }
    // ---------------------------------------------------------------------------------------------------------------------
        TABLE_NAME = "Areas";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            fail();
        }

        try (Connection conn = DriverManager.getConnection(URL)) {
            String insertSQL = "INSERT INTO " + TABLE_NAME + "(name) VALUES(?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, area.getAreaName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            fail();
        }
        areaFacade.loadAll();
        assertTrue(areaFacade.getAreas().contains(area));
    }
    @Test
    void loadShipmentDoc(){
        String TABLE_NAME = "ShipmentDocs";
        ShipmentDocument testDoc = new ShipmentDocument(1111, "Orange", "Orange the man", "+972Orange", "Or:an:ge", "Or:an:ge", 11,"Orange");
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(URL)){

            String insertSQL = "INSERT INTO " + TABLE_NAME + "(shipmentId, originAddress, originContactName, originContactNumber, shipmentDate, departureTime, truckNumber, driverName) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);

            pstmt.setInt(1, testDoc.getShipmentId());
            pstmt.setString(2, testDoc.getOriginAddress());
            pstmt.setString(3, testDoc.getOriginContactName());
            pstmt.setString(4, testDoc.getOriginContactNumber());
            pstmt.setString(5, testDoc.getShipmentDate());
            pstmt.setString(6, testDoc.getDepartureTime());
            pstmt.setInt(7, testDoc.getTruckNumber());
            pstmt.setString(8, testDoc.getDriverName());



            pstmt.executeUpdate();
        }
        catch (SQLException ex){
            fail();
        }
        shipmentHistory.loadAll();
        assertTrue(shipmentHistory.getShipmentDocs().contains(testDoc));
    }

}