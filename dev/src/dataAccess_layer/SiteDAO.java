package dataAccess_layer;

import domain_layer.DestinationDocument;
import domain_layer.Site;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class SiteDAO {
    private final String TABLE_NAME = "Sites";
    private final String URL;
    public SiteDAO() {
        URL = "jdbc:sqlite:" + Paths.get("persisted_layer.db").toAbsolutePath().toString().replace("\\", "/");
    }
    public List<Site> getSitesByArea(String name) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<Site> sites = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL)) {
            String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE areaName = '" + name + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectSQL);

            while (rs.next()) {
                sites.add(new Site(rs.getString("address"), rs.getString("contactName"), rs.getString("contactNumber")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        if(sites.isEmpty())
            throw new NoSuchElementException("No site in area: " + name + " was found");
        return sites;
    }

    public void create(Site site, String areaName) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(URL)){

            String insertSQL = "INSERT INTO " + TABLE_NAME + "(address, areaName, contactName, contactNumber) VALUES(?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);

            pstmt.setString(1, site.getAddress());
            pstmt.setString(2, areaName);
            pstmt.setString(3, site.getContactName());
            pstmt.setString(4, site.getContactNumber());

            pstmt.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }
}
