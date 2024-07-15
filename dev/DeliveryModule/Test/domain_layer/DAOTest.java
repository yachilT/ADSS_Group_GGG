package domain_layer;

import dataAccess_layer.DriverDAO;
import dataAccess_layer.ProductDAO;
import dataAccess_layer.ShipmentDocumentDAO;
import dataAccess_layer.TruckDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DAOTest extends DBTest{
    ProductDAO productDAO;
    TruckDAO truckDAO;
    DriverDAO driverDAO;
    ShipmentDocumentDAO shipmentDocumentDAO;
    public DAOTest() throws IOException {
        super("EmptyDB.db");
    }
    @BeforeEach
    void setup() {
        try{createDB();} catch (IOException io) {io.printStackTrace();}
        productDAO = new ProductDAO(testDBPath);
        truckDAO = new TruckDAO(testDBPath);
        driverDAO = new DriverDAO(testDBPath);
        shipmentDocumentDAO = new ShipmentDocumentDAO(testDBPath);
    }

    @AfterEach
    void tearDown() {try{deleteDB();} catch (IOException io) {io.printStackTrace();};}
    @Test
    void readProductsOfDest(){
        ProductAmount prd = new ProductAmount("Milk", 1233);
        int destId = 1000;
        String TABLE_NAME = "Products";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(URL)){

            String insertSQL = "INSERT INTO " + TABLE_NAME + "(destinationDocId, productName, productAmount) VALUES(?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);

            pstmt.setInt(1, destId);
            pstmt.setString(2, prd.getProductName());
            pstmt.setInt(3, prd.getAmount());

            pstmt.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        Assertions.assertTrue(productDAO.getProductsByDstId(destId).contains(prd));
    }
    @Test
    void readTruck(){
        int truckNumber = 1111111;
        Truck truck = new Truck(truckNumber, "model", 100, 2000);

        truckDAO.create(truck);
        Assertions.assertEquals(truck, truckDAO.read(truckNumber));
    }
    @Test
    void readDriver(){
        int driverId = 1111;
        Driver driver = new Driver(driverId, "Maneskin", new License(111));
        driverDAO.create(driver);
        Assertions.assertEquals(driver, driverDAO.read(driverId));
    }
    @Test
    void readShipmentDoc(){
        int shipmentDocId = 111111;
        ShipmentDocument shipmentDoc = new ShipmentDocument(shipmentDocId, "AJR", "Alar", "05327283", "1/1/1", "1/1/1", 111, "Moshe");

        shipmentDocumentDAO.create(shipmentDoc);

        Assertions.assertEquals(shipmentDoc, shipmentDocumentDAO.read(shipmentDocId));
    }
}
