
import presentation_layer.Controller;

public class Main {
    public static void main(String[] args) {
        String path = "persisted_layer.db";
        new Controller(path, null, null ,null).run();
    }
}