package dataAccess_layer;
import domain_layer.DestinationDocument;

import java.sql.*;
public class DestinationDocumentDAO {
    private  String TABLE_NAME = "DestinationDocs";
    private Connection connection;
    public DestinationDocumentDAO() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:persisted_layer.db");
        }
        catch (SQLException ex){
            connection = null;
        }
    }

    public void create(DestinationDocument dstDoc){
        try {
            String insertSQL = "INSERT INTO " + TABLE_NAME + "(destinationDocId, shipmentDocId, address, contactName, contactNumber, weight) VALUES(?,?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);
        }
        catch (SQLException ex){

        }
        
    }



}
