package dataAccess_layer;

import domain_layer.Driver;
import domain_layer.License;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class DriverDAO {
    private final String TABLE_NAME = "Drivers";
    private final String URL;
    public DriverDAO(){
        URL = "jdbc:sqlite:" + Paths.get("persisted_layer.db").toAbsolutePath().toString().replace("\\", "/");
    }
    public void create(Driver driver){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(URL)){

            String insertSQL = "INSERT INTO " + TABLE_NAME + "(driverId, driverName, license) VALUES(?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);

            pstmt.setInt(1, driver.getId());
            pstmt.setString(2, driver.getDriverName());
            pstmt.setFloat(3, driver.getLicense().allowedWeight);


            pstmt.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public Driver read(int driverId){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE driverId = " + driverId;
        Driver driver = null;
        try (Connection conn = DriverManager.getConnection(URL)) {

             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL);

            while (rs.next()) {
                driver = new Driver(rs.getInt("driverId"), rs.getString("driverName"), new License(rs.getFloat("license")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(driver == null)
            throw new NoSuchElementException("driver with id: " + driverId + " is not found.");
        return driver;
    }
    public List<Driver> readAll(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        List<Driver> drivers = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL)) {

             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL);

            while (rs.next()) {
                drivers.add(new Driver(rs.getInt("driverId"), rs.getString("driverName"), new License(rs.getFloat("license"))));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return drivers;
    }



}
