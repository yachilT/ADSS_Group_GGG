package domain_layer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class StoreDocsTest extends DBTest{

    private ShipmentHistory sh;
    public StoreDocsTest() throws IOException {
        super("EmptyDB.db");
    }

    @BeforeEach
    public void setUp() {
        try {
            createDB();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        sh = new ShipmentHistory(true, testDBPath);
    }

    @Test
    void saveShipmentTest() {

        ShipmentDocument s = new ShipmentDocument(0, "origin", "Mr. origin", "+origin-phone-number", "or/ig/in", "or:ig:in", 0, "origin-driver");
        sh.add(s, new ArrayList<>());
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ShipmentDocs");


            if (rs.next()) {
                assertTrue(rs.getInt("shipmentId") == 0
                        && rs.getString("originAddress").equals(s.getOriginAddress())
                        && rs.getString("originContactName").equals(s.getOriginContactName())
                        && rs.getString("originContactNumber").equals(s.getOriginContactNumber())
                        && rs.getString("shipmentDate").equals(s.getShipmentDate())
                        && rs.getInt("truckNumber") == s.getTruckNumber()
                        && rs.getString("driverName").equals(s.getDriverName()));
            }
            else
                fail("Table is empty");

        } catch (SQLException ex) {
            fail(ex.getMessage());
        }

    }

    @Test
    void saveDestinationTest() {
        ShipmentDocument s = new ShipmentDocument(0, "origin", "Mr. origin", "+origin-phone-number", "or/ig/in", "or:ig:in", 0, "origin-driver");
        List<DestinationDocument> dsts = List.of(
                new DestinationDocument(new Destination(new Site("dst1", "Mr. dst1", "+dst1-phone-number"), List.of(new ProductAmount("Milk", 10))), 0, 0, 900),
                new DestinationDocument(new Destination(new Site("dst2", "Mr. dst2", "+dst2-phone-number"), List.of(new ProductAmount("bread", 100), new ProductAmount("cheese", 200))), 1, 0,800));

        sh.add(s, dsts);
        try (Connection conn = DriverManager.getConnection(URL)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM DestinationDocs");

            int count = 0;
            while (rs.next() && count < dsts.size()) {
                if (!(rs.getInt("destinationDocId") == dsts.get(count).getDestinationDocId()
                        && rs.getInt("shipmentDocId") == dsts.get(count).getShipmentDocId()
                        && rs.getString("address").equals(dsts.get(count).getAddress())
                        && rs.getString("contactName").equals(dsts.get(count).getContactName())
                        && rs.getString("contactNumber").equals(dsts.get(count).getContactNumber())
                        && rs.getFloat("weight") == dsts.get(count).getWeight()))
                    fail("saved destination doc doesn't match premade doc");

                List<ProductAmount> prods = dsts.get(count).getProducts();
                PreparedStatement productStatement = conn.prepareStatement("SELECT * FROM Products WHERE destinationDocId = ?");
                productStatement.setInt(1, rs.getInt("destinationDocId"));


                ResultSet rsProd = productStatement.executeQuery();
                int countProd = 0;
                while (rsProd.next() && countProd < prods.size()) {
                    if (!(rsProd.getString("productName").equals(prods.get(countProd).getProductName())
                            && rsProd.getInt("productAmount") == prods.get(countProd).getAmount()))
                        fail();
                    countProd++;
                }
                count++;
            }

        } catch (SQLException ex) {
            fail(ex.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            deleteDB();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
