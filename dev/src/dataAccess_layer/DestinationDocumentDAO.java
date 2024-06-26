package dataAccess_layer;
import domain_layer.DestinationDocument;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class DestinationDocumentDAO {
    private final   String TABLE_NAME = "DestinationDocs";
    private  final  String URL = "jdbc:sqlite:persisted_layer.db";

    public DestinationDocumentDAO() {}

    public void create(DestinationDocument dstDoc) {
        try (Connection connection = DriverManager.getConnection(URL)){

            String insertSQL = "INSERT INTO " + TABLE_NAME + "(destinationDocId, shipmentDocId, address, contactName, contactNumber, weight) VALUES(?,?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);

            pstmt.setInt(1, dstDoc.getDestinationDocId());
            pstmt.setInt(2, dstDoc.getShipmentDocId());
            pstmt.setString(3, dstDoc.getAddress());
            pstmt.setString(4, dstDoc.getContactName());
            pstmt.setString(5, dstDoc.getContactNumber());
            pstmt.setFloat(6, dstDoc.getWeight());

            pstmt.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }
    public DestinationDocument read(int destinationDocId) throws NoSuchElementException{
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE destinationDocId = " + destinationDocId;
        DestinationDocument resultDoc = null;
        try (Connection conn = DriverManager.getConnection(URL);

             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                resultDoc = new DestinationDocument(rs.getInt("destinationDocId"), rs.getInt("shipmentDocId"), rs.getString("address"), rs.getString("contactName"), rs.getString("contactNumber"), rs.getFloat("weight"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(resultDoc == null)
            throw new NoSuchElementException("destinationDocument with id: " + destinationDocId + " is not found.");
        return  resultDoc;
    }


    public List<Integer> readDstIdByShipmentId(int shipmentDocId) {
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE shipmentDocId = " + shipmentDocId;
        List<Integer> lst = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL);

             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                lst.add(rs.getInt("destinationDocId"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(lst.isEmpty())
            throw new NoSuchElementException("Error: No destinationDocs found for Shipment ID: " +  shipmentDocId);
        return  lst;
    }
}
