package domain_layer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class StoreDocsTest extends DBTest{
    public StoreDocsTest() throws IOException {
        super("empty.db");
    }

    @BeforeEach
    public void setUp() {
        try {
            createDB();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void SaveShipmentTest() {

        ShipmentDocument s = new ShipmentDocument(0, "origin", "Mr. origin", "+origin-phone-number", "or/ig/in", "or:ig:in", 0, "origin-driver");
        


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
