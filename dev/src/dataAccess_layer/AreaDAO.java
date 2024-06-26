package dataAccess_layer;

import domain_layer.Area;
import domain_layer.ProductAmount;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AreaDAO {
    private String TABLE_NAME = "Areas";
    private String URL;
    public AreaDAO() {
        URL = "jdbc:sqlite:" + Paths.get("persisted_layer.db").toAbsolutePath().toString().replace("\\", "/");
    }
    public Area read(String name) {
        return null;
    }

    public List<String> readAllNames() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<String> names = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL)) {
            String selectSQL = "SELECT name FROM " + TABLE_NAME;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectSQL);

            while (rs.next()) {
                names.add(rs.getString("name"));
            }
            return names;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void create(Area area) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(URL)) {
            String insertSQL = "INSERT INTO " + TABLE_NAME + "(name) VALUES(?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, area.getAreaName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
