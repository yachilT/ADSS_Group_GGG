package dataAccess_layer;

import domain_layer.ShipmentDocument;


import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ShipmentDocumentDAO {
    private final String TABLE_NAME = "ShipmentDocs";
    private final String URL;
    public ShipmentDocumentDAO() {
        URL = "jdbc:sqlite:" + Paths.get("persisted_layer.db").toAbsolutePath().toString().replace("\\", "/");
    }
    public void create(ShipmentDocument shipment) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(URL)){

            String insertSQL = "INSERT INTO " + TABLE_NAME + "(shipmentId, originAddress, originContactName, originContactNumber, shipmentDate, departureTime, truckNumber, driverName) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);

            pstmt.setInt(1, shipment.getShipmentId());
            pstmt.setString(2, shipment.getOriginAddress());
            pstmt.setString(3, shipment.getOriginContactName());
            pstmt.setString(4, shipment.getOriginContactNumber());
            pstmt.setString(5, shipment.getShipmentDate());
            pstmt.setString(6, shipment.getDepartureTime());
            pstmt.setInt(7, shipment.getTruckNumber());
            pstmt.setString(8, shipment.getDriverName());



            pstmt.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public ShipmentDocument read(int shipmentId) throws NoSuchElementException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE truckNumber = " + shipmentId;
        ShipmentDocument shipmentDoc = null;
        try (Connection conn = DriverManager.getConnection(URL)){

             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL);

            while (rs.next()) {
                shipmentDoc = new ShipmentDocument(rs.getInt("shipmentId"), rs.getString("originAddress"), rs.getString("originContactName"), rs.getString("originContactNumber"), rs.getString("shipmentDate"), rs.getString("departureTime"), rs.getInt("truckNumber"), rs.getString("driverName"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(shipmentDoc == null)
            throw new NoSuchElementException("shipment document with id: " + shipmentId + " is not found.");
        return shipmentDoc;

    }

    public List<ShipmentDocument> readAll() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        List<ShipmentDocument> shipmentDocs = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL)){

             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL);

            while (rs.next()) {
                shipmentDocs.add(new ShipmentDocument(rs.getInt("shipmentId"), rs.getString("originAddress"), rs.getString("originContactName"), rs.getString("originContactNumber"), rs.getString("shipmentDate"), rs.getString("departureTime"), rs.getInt("truckNumber"), rs.getString("driverName")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return shipmentDocs;

    }
}
