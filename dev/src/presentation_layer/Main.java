package presentation_layer;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        Window window = new MainMenuWindow();
        while (window != null)
            window = window.run(controller);
    }
}