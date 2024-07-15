package domain_layer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class DBTest {
    protected String testDBPath;
    protected String dbToCopyPath;
    protected String URL;

    public DBTest(String dbToCopy) throws IOException {
        this.dbToCopyPath = dbToCopy;
        testDBPath = ("Test\\" + dbToCopy).split("\\.")[0] + " - copy.db";
        URL = "jdbc:sqlite:" + Paths.get(testDBPath).toAbsolutePath().toString().replace("\\", "/");
    }

    public void createDB() throws IOException {
        Files.copy(new File(dbToCopyPath).toPath(), new File(testDBPath).toPath());
    }
    public void deleteDB() throws IOException {
        Files.delete(new File(testDBPath).toPath());
    }
}
