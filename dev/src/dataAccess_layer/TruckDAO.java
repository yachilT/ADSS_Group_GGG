package dataAccess_layer;

import domain_layer.Truck;


import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class TruckDAO {
    private final String TABLE_NAME = "Trucks";
    private final String URL;
    public TruckDAO(){
        URL = "jdbc:sqlite:" + Paths.get("persisted_layer.db").toAbsolutePath().toString().replace("\\", "/");
    }

    public void create(Truck truck){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try (Connection connection = DriverManager.getConnection(URL)){

            String insertSQL = "INSERT INTO " + TABLE_NAME + "(truckNumber, model, emptyWeight, maxWeight) VALUES(?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);

            pstmt.setInt(1, truck.getNumber());
            pstmt.setString(2, truck.getModel());
            pstmt.setFloat(3, truck.getEmptyWeight());
            pstmt.setFloat(4, truck.getMaxWeight());

            pstmt.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public Truck read(int truckNumber){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE truckNumber = " + truckNumber;
        Truck truck = null;
        try (Connection conn = DriverManager.getConnection(URL);

             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                truck = new Truck(rs.getInt("truckNumber"), rs.getString("model"), rs.getFloat("emptyWeight"), rs.getFloat("maxWeight"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(truck == null)
            throw new NoSuchElementException("truck with id: " + truckNumber + " is not found.");
        return truck;
    }
    public List<Truck> readAll(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        List<Truck> trucks = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL);

             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            while (rs.next()) {
                trucks.add(new Truck(rs.getInt("truckNumber"), rs.getString("model"), rs.getFloat("emptyWeight"), rs.getFloat("maxWeight")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return trucks;
    }
}
