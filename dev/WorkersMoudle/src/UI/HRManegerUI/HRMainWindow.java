package UI.HRManegerUI;

import ServiceLayer.ServiceManager;
import UI.Window;
import presentation_layer.Controller;

public class HRMainWindow extends Window {
    private Integer id;
    private Window nextWindow;
    private Controller deliveryController;
    public HRMainWindow(ServiceManager serviceManager, Integer id, Controller deliveryController) {
        super(serviceManager);
        this.id = id;
        this.deliveryController = deliveryController;
    }

    @Override
    public void run() {
        boolean exit = true;
        while (exit) {
            chooseOptions();
            switch (scanner.nextLine()) {
                case "1" -> new AddBranchesWindow(serviceManager, deliveryController).run();
                case "2" -> {
                    serviceManager.getEmployeeService().logout(id);
                    exit = false;
                }
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
