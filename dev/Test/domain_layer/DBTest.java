package domain_layer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class DBTest {
    protected String testDBPath;
    protected  String dbToCopyPath;

    public DBTest(String dbToCopy) throws IOException {
        this.dbToCopyPath = dbToCopy;
        testDBPath = ("Test\\" + dbToCopy).split("\\.")[0] + " - copy.db";
    }

    public void createDB() throws IOException {
        Files.copy(new File(testDBPath).toPath(), new File(dbToCopyPath).toPath());
    }
    public void deleteDB() throws IOException {
        Files.delete(new File(testDBPath).toPath());
    }
}
