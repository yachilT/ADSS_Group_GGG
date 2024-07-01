package dataAccess_layer;

import domain_layer.ProductAmount;


import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class ProductDAO {
    private final String TABLE_NAME = "Products";
    private final String URL;
    public ProductDAO(String dbPath) {
        URL = "jdbc:sqlite:" + Paths.get(dbPath).toAbsolutePath().toString().replace("\\", "/");
    }

    public List<ProductAmount> getProductsByDstId(int destDocId) throws NoSuchElementException{
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE destinationDocId = " + destDocId;
        List<ProductAmount> products = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL)) {

             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL);

            while (rs.next()) {
                products.add(new ProductAmount( rs.getString("productName"), rs.getInt("productAmount")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        if(products.isEmpty())
            throw new NoSuchElementException("Error: No products found for Destination ID: " + destDocId);
        return products;
    }

    public void create(ProductAmount productAmount, int dstId) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(URL)){

            String insertSQL = "INSERT INTO " + TABLE_NAME + "(destinationDocId, productName, productAmount) VALUES(?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(insertSQL);

            pstmt.setInt(1, dstId);
            pstmt.setString(2, productAmount.getProductName());
            pstmt.setInt(3, productAmount.getAmount());

            pstmt.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

}
