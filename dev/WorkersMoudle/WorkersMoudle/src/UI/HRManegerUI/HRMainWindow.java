package UI.HRManegerUI;

import ServiceLayer.ServiceManager;
import UI.Window;

public class HRMainWindow extends Window {
    private Integer id;
    private Window nextWindow;
    public HRMainWindow(ServiceManager serviceManager, Integer id) {
        super(serviceManager);
        this.id = id;
    }

    @Override
    public void run() {
        boolean exit = true;
        while (exit) {
            chooseOptions();
            switch (scanner.nextLine()) {
                case "1" -> new AddBranchesWindow(serviceManager).run();
                case "2" -> exit = false;
            }
        }
        System.out.println("Goodbye!");
    }

    private void chooseOptions() {
        System.out.println("Choose an option:");
        System.out.println("1. Add Branches");
        System.out.println("2. Exit");

    }

    public Window getNextWindow() {
        return nextWindow;
    }
}
