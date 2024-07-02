package domain_layer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DBTest {
    protected String testDBPath;
    protected String URL;

    public DBTest(String dbToCopy) throws IOException {
        testDBPath = dbToCopy + " - copy";
        Files.copy(new File(dbToCopy).toPath(), new File(dbToCopy).toPath());
        URL = "jdbc:sqlite:" + Paths.get(testDBPath).toAbsolutePath().toString().replace("\\", "/");
    }

    public void deleteDB() throws IOException {
        Files.delete(new File(testDBPath).toPath());
    }
}
