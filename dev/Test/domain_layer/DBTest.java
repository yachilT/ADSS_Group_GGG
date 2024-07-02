package domain_layer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DBTest {
    protected String testDBPath;

    public DBTest(String dbToCopy) throws IOException {
        testDBPath = dbToCopy + " - copy";
        Files.copy(new File(dbToCopy).toPath(), new File(dbToCopy).toPath());
    }

    public void deleteDB() throws IOException {
        Files.delete(new File(testDBPath).toPath());
    }
}
